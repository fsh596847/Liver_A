package com.android.doctor.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.android.doctor.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * 
 * @author Yong
 */
public class StringHelper {
	
	private final static Pattern VERIFY_CODE = Pattern.compile("[0-9]{4}");
	
	private final static Pattern PHONE_NUMBER = Pattern.compile("^1[358]\\d{9}$|^147\\d{8}$");
	
    private final static Pattern EMAIL_PATTERN = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private final static Pattern IMG_URL = Pattern
            .compile(".*?(gif|jpeg|png|jpg|bmp)");

    private final static Pattern URL = Pattern
            .compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @SuppressLint("SimpleDateFormat")
		@Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @SuppressLint("SimpleDateFormat")
		@Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 
     * 
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        return toDate(sdate, dateFormater.get());
    }

    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDateString(Date date) {
        return dateFormater.get().format(date);
    }


   

    /**
     * 
     * 
     * @param dt
     * @return 
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }

    /**
     * 
     * 
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 
     * 
     * @return
     */
    public static long getToday() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater2.get().format(cal.getTime());
        curDate = curDate.replace("-", "");
        return Long.parseLong(curDate);
    }

    public static String getCurTimeStr() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater.get().format(cal.getTime());
        return curDate;
    }

    /***
     * 
     * 
     * @author 
     * 
     * @return long
     * @param dete1
     * @param date2
     * @return
     */
    public static long calDateDifferent(String dete1, String date2) {

        long diff = 0;

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = dateFormater.get().parse(dete1);
            d2 = dateFormater.get().parse(date2);

            diff = d2.getTime() - d1.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diff / 1000;
    }

    
    public static String replace(String str, char oldChar, char newChar) {
    	if (str == null) {
    		return str;
    	}
    	return str.replace(oldChar, newChar);
    }
    
    public static String replace(String str, int start, int num, char ch) {
    	if (str == null) {
    		return str;
    	}
    	
    	int rLen = start + num;
    	if (start < 0 || str.length() < rLen) {
    		return str;
    	}
    	
    	char charArr[] = str.toCharArray();
    	
    	for (int i = start; i < rLen ; ++i) {
    		charArr[i] = ch;
    	}
    	return String.valueOf(charArr);
    }
    
    /**
     * 
     * 
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * 
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 
     * 
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url) {
        if (url == null || url.trim().length() == 0)
            return false;
        return IMG_URL.matcher(url).matches();
    }

    /**
     * 
     * 
     * @param str
     * @return
     */
    public static boolean isUrl(String str) {
        if (str == null || str.trim().length() == 0)
            return false;
        return URL.matcher(str).matches();
    }
    
    public static boolean isPhoneNumber(String text) {
    	return PHONE_NUMBER.matcher(text).matches();
    }
    
    
    public static boolean isVerifyCode(String text) {
    	return VERIFY_CODE.matcher(text).matches();
    }
    
    /**
     * 
     * 
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     *
     * 
     * @param obj
     * @return 
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     *
     * 
     * @param 
     * @return
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 
     * 
     * @param b
     * @return 
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    public static String getString(String s) {
        return s == null ? "" : s;
    }

    /**
     * 
     * 
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line + "<br>");
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    /***
     *
     * 
     * @param start
     *           
     * @param num
     *         
     * @param str
     * @return
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int leng = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > leng) {
            start = leng;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > leng) {
            end = leng;
        }
        return str.substring(start, end);
    }

    /**
     * 
     * 
     * @return
     */
    public static int getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 
     * 
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        week = week == 0 ? 52 : week;
        return week > 0 ? week : 1;
    }

    public static int[] getCurrentDate() {
        int[] dateBundle = new int[3];
        String[] temp = getDataTime("yyyy-MM-dd").split("-");

        for (int i = 0; i < 3; i++) {
            try {
                dateBundle[i] = Integer.parseInt(temp[i]);
            } catch (Exception e) {
                dateBundle[i] = 0;
            }
        }
        return dateBundle;
    }

    /**
     * 
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    public static boolean isValidPhoneNumber(Context context, String number) {
        if (StringHelper.isEmpty(number)) {
            UIHelper.showToast(context, R.string.error_phone_is_empty, 3000);
            return false;
        }

        if (!StringHelper.isPhoneNumber(number)) {
            UIHelper.showToast(context, R.string.error_phone_number, 3000);
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(Context context, String passwd) {
        if (StringHelper.isEmpty(passwd)) {
            UIHelper.showToast(context, R.string.error_password_is_empty, 3000);
            return false;
        }

        if (passwd.length() < 6) {
            UIHelper.showToast(context, R.string.error_password_too_less, 3000);
            return false;
        }
        return true;
    }
}
