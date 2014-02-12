package ca.sariarra.util;

public class ParamUtil {

	public static void ensureNotNull(String pName, Object param) {
		if (param == null) {
			throw new IllegalArgumentException(pName + " must not be null.");
		}
	}
}
