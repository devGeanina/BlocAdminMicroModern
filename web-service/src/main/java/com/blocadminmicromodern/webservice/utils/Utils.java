package com.blocadminmicromodern.webservice.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utils {

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd";

	public static String convertDateToString(Date date) {
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		cal.setTime(date);
		return sdf.format(date);
	}
}
