/*
 * Created on 2022-06-09 ( Time 18:20:00 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils.contract;

import lombok.*;

/**
 * Request Base
 * 
 * @author Geo
 *
 */
@Data
@ToString
@NoArgsConstructor
public class RequestBase {
	protected String		sessionUser;
	protected Integer		size;
	protected Integer		index;
	protected String		lang;
	protected String		businessLineCode;
	protected String		caseEngine;
	protected Boolean		isAnd;
	protected Integer		user;
	protected Boolean 		isSimpleLoading;
	protected Boolean       hierarchyFormat;
	protected String 		identifiantRequest;
	protected String 		key;
	
	protected String 		hostName;
	protected String 		ipAddress;
	
	protected String 		login;
	protected String 		password;

	protected Boolean		isSearchNumbers;
	
}