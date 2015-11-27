package org.myapp.common.utils;

import java.math.BigDecimal;

public class MathUtils {

	public static double scale2(Double d) {
		BigDecimal bd = new BigDecimal(d);
		return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	

	public static double scale4(Double d) {
		BigDecimal bd = new BigDecimal(d);
		return bd.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
