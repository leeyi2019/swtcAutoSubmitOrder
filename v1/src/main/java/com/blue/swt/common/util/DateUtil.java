package com.blue.swt.common.util;

import java.util.Date;
import java.text.SimpleDateFormat;
//日期工具类
public class DateUtil {
	private DateUtil(){}
	public static String getStrTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
}
