package ci.smile.simswaporange.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ci.smile.simswaporange.dao.entity.AtionToNotifiable;
import ci.smile.simswaporange.dao.repository.AtionToNotifiableRepository;
import com.sun.mail.smtp.SMTPSendFailedException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.UserDto;

//import ci.smile.recouvrementB2B.utils.contract.Response;

/**
 * BUSINESS factory
 *
 * @author Diomande Souleymane
 */

@Component
@RequiredArgsConstructor
public class HostingUtils {
	private final ParamsUtils paramsUtils;
	private final TemplateEngine templateEngine;
	private final ExceptionUtils exceptionUtils;
	private final AtionToNotifiableRepository ationToNotifiableRepository;
	@Autowired
	Environment environment;
	private static final Logger slf4jLogger = LoggerFactory.getLogger(HostingUtils.class);
	@Async
	public <T> Response<T> sendEmail(Map<String, String> from, List<Map<String, String>> toRecipients, String subject,
									 String body, List<String> attachmentsFilesAbsolutePaths, Context context, String templateName,
									 Locale locale, AtionToNotifiable ationToNotifiable) {
		Response<T> response = new Response<T>();
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		String smtpServer = paramsUtils.getSmtpHost();
		if (smtpServer != null) {
			Boolean auth = false;
			javaMailSender.setHost(smtpServer);
			javaMailSender.setPort(paramsUtils.getSmtpPort());
			javaMailSender.setUsername(paramsUtils.getSmtpUsername());
			javaMailSender.setPassword(paramsUtils.getSmtpPassword());
			slf4jLogger.info("host :  {}, port :  {}, username :  {}, password :  {}", smtpServer, paramsUtils.getSmtpPort(), paramsUtils.getSmtpUsername(), paramsUtils.getSmtpPassword());
			// ADD NEW CONFIG for "no object DCH for MIME type multipart/mixed" error
			MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
			auth = true;

			javaMailSender.setJavaMailProperties(getMailProperties(paramsUtils.getSmtpHost(), auth));

			MimeMessage message = javaMailSender.createMimeMessage();
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message, Boolean.TRUE, "UTF-8");

				// sender
				helper.setFrom(new InternetAddress(from.get("email"), from.get("user")));
				// sender

				// recipients
				List<InternetAddress> to = new ArrayList<InternetAddress>();
				for (Map<String, String> recipient : toRecipients) {
					to.add(new InternetAddress(recipient.get("email"), recipient.get("user")));
				}
				helper.setTo(to.toArray(new InternetAddress[0]));

				// Subject and body
				helper.setSubject(subject);
				if (context != null && templateName != null) {
					body = templateEngine.process(templateName, context);
				}
				helper.setText(body, true);

				// Attachments
				if (attachmentsFilesAbsolutePaths != null && !attachmentsFilesAbsolutePaths.isEmpty()) {
					for (String attachmentPath : attachmentsFilesAbsolutePaths) {
						File pieceJointe = new File(attachmentPath);
						FileSystemResource file = new FileSystemResource(attachmentPath);
						if (pieceJointe.exists() && pieceJointe.isFile()) {
							helper.addAttachment(file.getFilename(), file);
						}
					}
				}

				javaMailSender.send(message);
				response.setHasError(Boolean.FALSE);
				ationToNotifiable.setStatutAction("L'e-mail a été envoyé avec succès.");
				ationToNotifiable.setIsNotify(Boolean.TRUE);
				ationToNotifiable.setUpdatedAt(Utilities.getCurrentDate());
				ationToNotifiableRepository.save(ationToNotifiable);
				/// gerer les cas d'exeption de non envoi de mail
			} catch (Exception e) {
				System.out.println(e);
				slf4jLogger.info(e.getMessage());
				slf4jLogger.error("message exception lors de l'envoie du mail " + e.getMessage());
				ationToNotifiable.setStatutAction(e.getMessage());
				ationToNotifiable.setIsNotify(Boolean.FALSE);
				ationToNotifiable.setUpdatedAt(Utilities.getCurrentDate());
				ationToNotifiableRepository.save(ationToNotifiable);
				slf4jLogger.info("nouvelle objet" + ationToNotifiable);
				exceptionUtils.EXCEPTION(response, locale, e);
			}
		}
		return response;
	}


	private Properties getMailProperties(String host, Boolean auth) {
		Properties properties = new Properties();
		if (environment.getActiveProfiles() != null && environment.getActiveProfiles().length > 0) {
			String activeProfiles = "";
			for (int i = 0; i < environment.getActiveProfiles().length; i++) {
				activeProfiles += environment.getActiveProfiles()[i];
			}
			System.out.println("active profile: " + activeProfiles);
			// Check if Active profiles contains profile to ignore
			if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equals("dev"))) {
				// DEV
				properties.setProperty("mail.transport.protocol", "smtp");
				properties.setProperty("mail.smtp.auth", auth.toString());
				properties.setProperty("mail.smtp.starttls.enable", "true");
				properties.setProperty("mail.smtp.starttls.required", "true");
				properties.setProperty("mail.debug", "true");
				if (host.equals("smtp.gmail.com")) {
					properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
				}
			}
			if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equals("production"))) {
				// PROD
				System.out.println(
						" **************************** setter les properties pour l'envoi de mail ****************************");
				properties.setProperty("mail.smtp.auth", auth.toString());
				properties.setProperty("mail.transport.protocol", "smtp");
				properties.put("mail.smtp.starttls.enable", "false");
				properties.setProperty("mail.debug", "true");

			}
		}

		return properties;
	}

	public <T> Response<T> sendEmailLite(Map<String, String> from, List<Map<String, String>> toRecipients,
			String subject, String body, List<String> attachmentsFilesAbsolutePaths, Context context,
			String templateName, Locale locale) {
		Response<T> response = new Response<T>();
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		String smtpServer = paramsUtils.getSmtpHost();
		if (smtpServer != null) {
			Boolean auth = false;
			javaMailSender.setHost(paramsUtils.getSmtpHost());
			javaMailSender.setPort(paramsUtils.getSmtpPort());
			javaMailSender.setPassword(paramsUtils.getPassword());
			// ADD NEW CONFIG for "no object DCH for MIME type multipart/mixed" error
			MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
			auth = true;

			javaMailSender.setJavaMailProperties(getMailProperties(paramsUtils.getOciServeurMailAddress(), auth));

			MimeMessage message = javaMailSender.createMimeMessage();
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message, Boolean.TRUE, "UTF-8");

				// sender
				helper.setFrom(new InternetAddress(from.get("email"), from.get("user")));
				// sender

				// recipients
				List<InternetAddress> to = new ArrayList<InternetAddress>();
				for (Map<String, String> recipient : toRecipients) {
					to.add(new InternetAddress(recipient.get("email"), recipient.get("user")));
				}
				helper.setTo(to.toArray(new InternetAddress[0]));

				// Subject and body
				helper.setSubject(subject);
				if (context != null && templateName != null) {
					body = templateEngine.process(templateName, context);
				}
				helper.setText(body, true);

				// Attachments
				if (attachmentsFilesAbsolutePaths != null && !attachmentsFilesAbsolutePaths.isEmpty()) {
					for (String attachmentPath : attachmentsFilesAbsolutePaths) {
						File pieceJointe = new File(attachmentPath);
						FileSystemResource file = new FileSystemResource(attachmentPath);
						if (pieceJointe.exists() && pieceJointe.isFile()) {
							helper.addAttachment(file.getFilename(), file);
						}
					}
				}
				javaMailSender.send(message);
				response.setHasError(Boolean.FALSE);
				/// gerer les cas d'exeption de non envoi de mail
			} catch (MessagingException e) {
				e.printStackTrace();
				exceptionUtils.EXCEPTION(response, locale, e);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				exceptionUtils.EXCEPTION(response, locale, e);
			}
		}
		return response;
	}

	private Properties getMailPropertiess(String host, Boolean auth) {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", auth.toString());
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.starttls.required", "true");
		properties.setProperty("mail.debug", "true");
		if (host.equals("smtp.gmail.com")) {
			properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
		}
		return properties;
	}

//	private Properties getMailProperties(String host, Boolean auth) {
//		  Properties properties = new Properties();
//		  properties.setProperty("mail.transport.protocol", "smtp");
//		  properties.setProperty("mail.smtp.auth", auth.toString());
//		  properties.setProperty("mail.smtp.starttls.enable", "true");
//		  properties.setProperty("mail.smtp.starttls.required", "true");
//		  properties.setProperty("mail.debug", "true");
//		  if (host.equals("smtp.gmail.com")) {
//		   properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
//		  }
//		  return properties;
//		 }

	@SuppressWarnings("unused")
	private Properties getMailPropertiees(String host, Boolean auth) {
		Properties properties = new Properties();
		if (environment.getActiveProfiles() != null && environment.getActiveProfiles().length > 0) {
			String activeProfiles = "";
			for (int i = 0; i < environment.getActiveProfiles().length; i++) {
				activeProfiles += environment.getActiveProfiles()[i];
			}
			System.out.println("active profile: " + activeProfiles);
			// Check if Active profiles contains profile to ignore
			if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equals("development"))) {
				// DEV
				properties.setProperty("mail.transport.protocol", "smtp");
				properties.setProperty("mail.smtp.auth", auth.toString());
				properties.setProperty("mail.smtp.starttls.enable", "true");
				properties.setProperty("mail.smtp.starttls.required", "true");
				properties.setProperty("mail.debug", "true");
				if (host.equals("smtp.gmail.com")) {
					properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
				}
			}
			if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equals("production"))) {
				// PROD
				System.out.println(
						" **************************** setter les properties pour l'envoi de mail ****************************");
				properties.setProperty("mail.smtp.auth", auth.toString());
				properties.setProperty("mail.transport.protocol", "smtp");
				properties.put("mail.smtp.starttls.enable", "false");
				properties.setProperty("mail.debug", "true");

			}
		}

		return properties;
	}
	
	@Async 
	public void LogResponseTransaction(String RequestIdentifier,
			String RemoteAddr,
			String UserPrincipal,
			String UserAgent,
			String Protocol,
			String SessionUser,
			String KeyAES,
			String CompanyID,
			String nom,
			String prenom,
			String Uri,
			String HasError,
			String StatusCode,
			String StatusMessage,
			String RequestValue,
			String ResponseValue,
			String Action,
			String ND,
			String Customer) throws Exception {		
		slf4jLogger.info(" ------------------------  l'execution de la methode asynchrone    ----------------------------");
		String  connectedUserLogin    = "";
		String  connectedUserFullName = "";

		slf4jLogger.info("log_refonte_si-"+new SimpleDateFormat("yyyy.MM.dd").format(new Date()), "_doc", "/templates/es/template_refonte_si.json");
		slf4jLogger.info(
				"Request:: RequestIdentifier:{},RemoteAddr:{} ,UserPrincipal:{},UserAgent:{},Protocol:{},SessionUser:{},KeyAES:{},CompanyID:{},FirstName:{},LastName:{},Uri:{},HasError:{},StatusCode:{},StatusMessage:{},RequestValue:{},ResponseValue:{},Action:{}",
				RequestIdentifier, RemoteAddr, UserPrincipal, UserAgent, Protocol, SessionUser, KeyAES, CompanyID,
				nom, prenom, Uri, HasError, StatusCode, StatusMessage, RequestValue, ResponseValue, Action);
	}

}
