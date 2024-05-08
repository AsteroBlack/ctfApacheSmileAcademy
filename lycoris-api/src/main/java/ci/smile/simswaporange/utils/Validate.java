
/*
 * Created on 2022-06-09 ( Time 18:20:00 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import lombok.*;

import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;

/**
 * Validate
 * 
 * @author Geo
 *
 */
@Data
@ToString
@NoArgsConstructor
public class Validate {

	private String field;
	private boolean good;
	private static Validate validate = new Validate();
	private static Logger slf4jLogger = LoggerFactory.getLogger(Validate.class);

	public static Validate getValidate() {
		return validate;
	}

	public static void setValidate(Validate validate) {
		Validate.validate = validate;
	}

	public static Validate Value(Object item, Object value, boolean strict) {
		validate.setGood(true);
		try {
			if (value == null) {
				validate.setGood(false);
				validate.setField(item.toString());
				return validate;
			}
			if (value instanceof String) {
				if (value.toString().isEmpty()) {
					validate.setGood(false);
					validate.setField(item.toString());
				}
			} else if (value instanceof Integer) {
				if (strict) {
					if (value.toString().isEmpty() || Integer.parseInt(value.toString()) < 0) {
						validate.setGood(false);
						validate.setField(item.toString());
					}
				} else {
					if (value == null || value.toString().isEmpty() || Integer.parseInt(value.toString()) <= 0) {
						validate.setGood(false);
						validate.setField(item.toString());
					}
				}
			} else {
				if (value == null || value.toString().isEmpty()) {
					validate.setGood(false);
					validate.setField(item.toString());
				}
			}
		} catch (Exception e) {
			slf4jLogger.warn(e.getMessage());
		}
		return validate;
	}

	public static Validate Value(Object item, Object value) {
		validate.setGood(true);
		try {
			if (value == null) {
				validate.setGood(false);
				validate.setField(item.toString());
				return validate;
			}
			if (value instanceof String) {
				if (value.toString().isEmpty()) {
					validate.setGood(false);
					validate.setField(item.toString());
				}
			} else if (value instanceof Integer) {
				if (value.toString().isEmpty() || Integer.parseInt(value.toString()) <= 0) {
					validate.setGood(false);
					System.out.println("object4" + value);
					validate.setField(item.toString());
				}
			} else {
				if (value == null || value.toString().isEmpty()) {
					validate.setGood(false);
					validate.setField(item.toString());
				}
			}

		} catch (Exception e) {
			slf4jLogger.warn(e.getMessage());
		}
		return validate;
	}

	public static Validate TypeValue(Object item, Object value, boolean strict) {

		validate.setGood(true);
		try {
			if (value == null) {
				validate.setGood(false);
				validate.setField(item.toString());
				return validate;
			}
			if (value instanceof String) {
				if (Utilities.isInteger(value.toString())) {
					validate.setGood(false);
					validate.setField(item.toString());
				}
			} else if (value instanceof Integer) {
				if (Utilities.isString(((Integer) value))) {
					validate.setGood(false);
					validate.setField(item.toString());
				}
			}
		} catch (Exception e) {
			slf4jLogger.warn(e.getMessage());
		}
		return validate;
	}

	public static Validate RequiredValue(Map<String, Object> listAverifier) {
		for (Map.Entry<String, Object> item : listAverifier.entrySet()) {
			validate.setGood(item.getValue() == null ? false : !item.getValue().toString().isEmpty());
			if (!validate.isGood()) {
				validate.setField(item.getKey());
				break;
			}

		}

		return validate;
	}

	public static Validate CorrectTypedValue(Map<String, Object> listAverifier) {
		for (Map.Entry<String, Object> item : listAverifier.entrySet()) {
			if (item.getValue() instanceof String) {
				if (Utilities.isInteger(item.getValue().toString())) {
					validate.setGood(false);
					validate.setField(item.toString());
					break;
				}
			} else if (item.getValue() instanceof Integer) {
				if (Utilities.isString(((Integer) item.getValue()))) {
					validate.setGood(false);
					validate.setField(item.toString());
					break;
				}
			}
		}
		return validate;
	}

	public static <T> Response<T> validateObject(Request<T> request, Response<T> response,
			FunctionalError functionalError, Locale locale) throws Exception {
		boolean ostate = true;
		response.setStatus(functionalError.REQUEST_FAIL("", locale));
		if (request == null) {
			response.setHasError(ostate);
			response.setStatus(functionalError.REQUEST_FAIL("la requete est vide", locale));
			return response;
		}
		if (request.getData() == null) {
			response.setHasError(ostate);
			response.setStatus(functionalError.REQUEST_FAIL("", locale));
			return response;
		}

		ostate = false;
		response.setStatus(null);
		response.setHasError(ostate);
		return response;
	}

	public static <T> Response<HashMap<String, String>> validateObjectHashMap(Request<T> request,
			Response<HashMap<String, String>> response, FunctionalError functionalError, Locale locale)
			throws Exception {
		boolean ostate = true;
		response.setStatus(functionalError.REQUEST_FAIL("", locale));
		if (request == null) {
			response.setHasError(ostate);
			response.setStatus(functionalError.REQUEST_FAIL("la requete est vide", locale));
			return response;
		}
		if (request.getData() == null) {
			response.setHasError(ostate);
			response.setStatus(functionalError.REQUEST_FAIL("", locale));
			return response;
		}

		ostate = false;
		response.setStatus(null);
		response.setHasError(ostate);
		return response;
	}

	public static <T> Response<T> validateList(Request<T> request, Response<T> response,
			FunctionalError functionalError, Locale locale) throws Exception {
		boolean ostate = true;
		response.setStatus(functionalError.REQUEST_FAIL("", locale));
		if (request == null) {
			response.setHasError(ostate);
			response.setStatus(functionalError.REQUEST_FAIL("la requete est vide", locale));
			return response;
		}
		if (request.getDatas() == null) {
			response.setHasError(ostate);
			response.setStatus(functionalError.REQUEST_FAIL("", locale));
			return response;
		}

		if (request.getDatas().isEmpty()) {
			response.setHasError(ostate);
			response.setStatus(functionalError.REQUEST_FAIL("la liste fournie est vide", locale));
			return response;
		}

		ostate = false;
		response.setStatus(null);
		response.setHasError(ostate);
		return response;
	}

	public static void EntityNotExiste(Object object) {
		if (ObjectUtils.isEmpty(object)) {
			throw new CustomEntityNotFoundException("902","Donnee inexistante", Boolean.TRUE);
		}
	}
	public static void validateObject(Object object, String message) {
		if (ObjectUtils.isEmpty(object)) {
			throw new CustomEntityNotFoundException("402",message, Boolean.TRUE);
		}
	}

	public static void validateList(List<Object> list){
		if ((list == null && list.isEmpty()) || ObjectUtils.isEmpty(list)){
			throw new CustomEntityNotFoundException("902","La liste est vide", Boolean.TRUE);
		}
	}

	
	public static void EntityExiste(Object object) {
		if (object != null) {
			throw new CustomEntityNotFoundException("902","Donnee déja existante", Boolean.TRUE);
		}
	}

	/**
	 * 
	 * @param datas
	 * @param groupExp
	 * @param response
	 * @param functionalError
	 * @param locale
	 * @return
	 */
	public static <T> boolean hasDuplicateData(List<T> datas, Function<? super T, ? extends Object> groupExp,
			Response response, FunctionalError functionalError, Locale locale) {
		if (datas == null) {
			throw new NullArgumentException("datas");
		}
		Function<Map<Object, Long>, Map<Object, Long>> havingCount = (m) -> {
			m.values().removeIf(v -> v <= 1L);
			return m;
		};
		Map<Object, Long> groupItems = datas.stream().collect(
				Collectors.collectingAndThen(Collectors.groupingBy(groupExp, Collectors.counting()), havingCount));
		boolean isTrue = groupItems != null && !groupItems.isEmpty();
		if (isTrue) {
			response.setStatus(functionalError.DATA_DUPLICATE(groupItems.toString().replaceAll("=", " *"), locale));
			response.setHasError(true);
		}
		return isTrue;
	}

	public static <T> Map<Object, Long> getDuplicateData(List<T> datas,
			Function<? super T, ? extends Object> groupExp) {
		if (datas == null) {
			throw new NullArgumentException("datas");
		}
		Function<Map<Object, Long>, Map<Object, Long>> havingCount = (m) -> {
			m.values().removeIf(v -> v <= 1L);
			return m;
		};
		Map<Object, Long> groupItems = datas.stream().collect(
				Collectors.collectingAndThen(Collectors.groupingBy(groupExp, Collectors.counting()), havingCount));

		return groupItems;
	}
}
