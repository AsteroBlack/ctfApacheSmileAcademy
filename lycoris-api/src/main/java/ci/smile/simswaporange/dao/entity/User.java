/*
 * Created on 2023-07-05 ( Time 13:47:03 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
// This Bean has a basic Primary Key (not composite) 

package ci.smile.simswaporange.dao.entity;

import java.io.Serializable;

import lombok.*;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "user"
 *
 * @author Telosys Tools Generator
 *
 */
@Data
@ToString
@Entity
@Table(name="user" )
public class User implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", nullable=false)
    private Integer    id           ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="nom", length=255)
    private String     nom          ;

    @Column(name="prenom", length=255)
    private String     prenom       ;

    @Column(name="hostname", length=255)
    private String     machine       ;

    @Column(name="ip_address", length=255)
    private String     ipadress       ;

    @Column(name="status", length=255)
    private String     status       ;

    @Column(name="is_validated")
    private Boolean    isValidated  ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date       createdAt    ;

    @Column(name="created_by")
    private Integer    createdBy    ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private Date       updatedAt    ;

    @Column(name="updated_by")
    private Integer    updatedBy    ;

    @Column(name="is_deleted")
    private Boolean    isDeleted    ;

    @Column(name="is_locked")
    private Boolean    isLocked     ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="locked_at")
    private Date       lockedAt     ;

    @Column(name="locked_by")
    private Integer    lockedBy     ;

    @Column(name="login", length=255)
    private String     login        ;

    @Column(name="is_super_admin")
    private Boolean    isSuperAdmin ;

    @Column(name="locked")
    private Boolean    locked       ;

    @Column(name="contact", length=100)
    private String     contact      ;

    @Column(name="is_connected")
    private Boolean    isConnected  ;

    @Column(name="email_adresse", length=100)
    private String     emailAdresse ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="first_connection")
    private Date       firstConnection ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_connection")
    private Date       lastConnection ;

	// "idProfil" (column "id_profil") is not defined by itself because used as FK in a link 
	// "idCivilite" (column "id_civilite") is not defined by itself because used as FK in a link 
	// "idCategory" (column "id_category") is not defined by itself because used as FK in a link 

    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="id_profil", referencedColumnName="id")
    private Profil profil      ;
    @ManyToOne
    @JoinColumn(name="id_civilite", referencedColumnName="id")
    private Civilite civilite    ;
    @ManyToOne
    @JoinColumn(name="id_category", referencedColumnName="id")
    private Category category    ;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public User() {
		super();
    }
    
	//----------------------------------------------------------------------
    // clone METHOD
    //----------------------------------------------------------------------
	@Override
	public java.lang.Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}