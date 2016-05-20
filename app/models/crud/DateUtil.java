package models.crud;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss.SSS"; 
	private static final String DATEFORMAT_API = "yyyy-MM-dd HH:mm:ss"; 
	
	public static Date getCurrentDate() {
		Date nowDate = new Date(System.currentTimeMillis());
		return nowDate;
	}

	public static String getCurrent() {
		return new SimpleDateFormat(DATEFORMAT).format(new Date(System.currentTimeMillis()));
	}

    public static String getCurrentForAPI() {
        return new SimpleDateFormat(DATEFORMAT_API).format(new Date(System.currentTimeMillis()));
    }

	public static long getCurrentAsLong() {
		return System.currentTimeMillis();
	}

	public static String format(long millis) {
		return new SimpleDateFormat(DATEFORMAT).format(new Date(millis));
	}

    public static String formatForAPI(long millis) {
        return new SimpleDateFormat(DATEFORMAT_API).format(new Date(millis));
    }

    public static String formatForAPIWithDate(Date date) {
        if(date != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        } else {
            return "";
        }
    }

}
