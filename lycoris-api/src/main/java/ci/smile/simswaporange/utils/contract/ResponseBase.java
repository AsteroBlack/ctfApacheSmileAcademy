
/*
 * Created on 2022-06-09 ( Time 18:20:00 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils.contract;

import lombok.*;
import ci.smile.simswaporange.utils.Status;

/**
 * Response Base
 * 
 * @author Geo
 *
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseBase {

	protected Status	status = new Status();
	protected boolean	hasError;
	protected String	sessionUser;
	protected Long		count;
	protected String	actionEffectue;
	protected String    state;
	protected Long		sessionUserExpire;
	protected String	exponent;
	protected String	modulus;

}
