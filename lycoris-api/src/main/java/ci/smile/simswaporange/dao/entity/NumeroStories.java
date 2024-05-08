/*
 * Created on 2023-07-04 ( Time 13:03:13 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
// This Bean has a basic Primary Key (not composite) 

package ci.smile.simswaporange.dao.entity;

import java.io.Serializable;

import lombok.*;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.Date;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "numero_stories"
 *
 * @author Telosys Tools Generator
 *
 */
@Data
@ToString
@Entity
@Table(name="numero_stories" )
public class NumeroStories implements Serializable, Cloneable {

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
    @Column(name="numero", nullable=false, length=2147483647)
    private String     numero       ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at", nullable=false)
    private Date       createdAt    ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private Date       updatedAt    ;

    @Column(name="updated_by")
    private Integer    updatedBy    ;

    @Column(name="is_deleted")
    private Boolean    isDeleted    ;

    @Column(name="is_machine")
    private Boolean    isMachine    ;

    @Column(name="statut", length=100)
    private String     statut       ;

    @Column(name="reason", length=2147483647)
    private String     reason       ;

    @Column(name="login", length=100)
    private String     login        ;

    @Column(name="machine", length=100)
    private String     machine      ;

    @Column(name="adresse_ip", length=100)
    private String     adresseIp    ;
    
    @Column(name="serial_number", length=100)
    private String     serialNumber    ;
    
    @Column(name="port_number", length=100)
    private String     portNumber    ;
    
    @Column(name="contract_id", length=100)
    private String     contractId    ;
    
    @Column(name="offer_name", length=100)
    private String     offerName    ;

	// "idProfil" (column "id_profil") is not defined by itself because used as FK in a link 
	// "createdBy" (column "created_by") is not defined by itself because used as FK in a link 
	// "idStatut" (column "id_statut") is not defined by itself because used as FK in a link 
	// "idCategory" (column "id_category") is not defined by itself because used as FK in a link 
	// "idTypeNumero" (column "id_type_numero") is not defined by itself because used as FK in a link 

    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="id_type_numero", referencedColumnName="id")
    private TypeNumero typeNumero  ;
    @ManyToOne
    @JoinColumn(name="id_profil", referencedColumnName="id")
    private Profil profil      ;
    @ManyToOne
    @JoinColumn(name="id_category", referencedColumnName="id")
    private Category category   ;
    @ManyToOne
    @JoinColumn(name="created_by", referencedColumnName="id")
    private User user        ;
    @ManyToOne
    @JoinColumn(name="id_statut", referencedColumnName="id")
    private Status status      ;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public NumeroStories() {
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