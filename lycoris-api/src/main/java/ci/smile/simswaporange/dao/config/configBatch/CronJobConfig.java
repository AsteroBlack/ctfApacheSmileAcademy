package ci.smile.simswaporange.dao.config.configBatch;

import ci.smile.simswaporange.utils.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Slf4j
@Configuration
@EnableScheduling
@EnableBatchProcessing
public class CronJobConfig{

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("job") // job pour la lecture automatique
    private Job runJob;

    @Autowired
    @Qualifier("jobRefreshNumber") // job pour raffraichir tous les numéros
    private Job jobRefreshNumber;

    @Autowired
    @Qualifier("jobLockUnlockNumber") // job pour bloquer tous les numéros debloqué après 24h
    private Job jobLockUnlockNumber;


    @Value("${cron.expression}")
    private String cronExpression;
    @Scheduled(cron = "0 0 20 * * *")
    public void runCronJob() {
        log.info("debut de runCronJob");
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("identifiant", UUID.randomUUID().toString())
                    .addString("date", Utilities.getCurrentDateTime())
                    .addLong("JobId",System.currentTimeMillis())
                    .addLong("time",System.currentTimeMillis()).toJobParameters();
            JobExecution execution = jobLauncher.run(runJob, jobParameters);
            System.out.println("STATUS :: " + execution.getStatus());
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                 | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void jobRefreshNumber() {
        log.info("debut de jobRefreshNumber");
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("identifiant", UUID.randomUUID().toString())
                    .addString("date", Utilities.getCurrentDateTime())
                    .addLong("JobId",System.currentTimeMillis())
                    .addLong("time",System.currentTimeMillis()).toJobParameters();
            JobExecution execution = jobLauncher.run(jobRefreshNumber, jobParameters);
            System.out.println("STATUS :: " + execution.getStatus());
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                 | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 20 21 * * *")
    public void jobLockUnlockNumber() {
        log.info("debut de jobLockUnlockNumber");
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("identifiant", UUID.randomUUID().toString())
                    .addString("date", Utilities.getCurrentDateTime())
                    .addLong("JobId",System.currentTimeMillis())
                    .addLong("time",System.currentTimeMillis()).toJobParameters();
            JobExecution execution = jobLauncher.run(jobLockUnlockNumber, jobParameters);
            System.out.println("STATUS :: " + execution.getStatus());
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                 | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

}
