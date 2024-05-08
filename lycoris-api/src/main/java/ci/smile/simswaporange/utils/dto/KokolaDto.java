package ci.smile.simswaporange.utils.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ci.smile.simswaporange.utils.Status;
import lombok.Data;
import lombok.ToString;
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class KokolaDto {
	private String codePrestataire;
	private String login;
	private String workcaseID;
 	private String  prestataire;
	private String  nom;
	private String  prenom;
	private String  email;
	private Integer prestataireId;
	private String  prestataireCode;
	private String  prestataireFullName;
	
	private String recipient;
	private String sender;
	private String content;
	
	private String actorLogin;
	private String dateCreateTask;
	private String dateCreateWorkcase;
	private String dateEndTask;
	private String stepLibelle;
	private String taskId;
	private String taskState;
	private String workcaseId;
	private String workcaseState;
	
	private Status info;
	
	private Map<String, Object> sendSmsResponse;
	
	
	public String getWorkcaseId() {
		return workcaseId;
	}
	public void setWorkcaseId(String workcaseId) {
		this.workcaseId = workcaseId;
	}
	public String getWorkcaseID() {
		return workcaseID;
	}
	public void setWorkcaseID(String workcaseID) {
		this.workcaseID = workcaseID;
	}

}
