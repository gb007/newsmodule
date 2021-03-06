package com.hollysmart.newsmodule.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by cai on 2018/11/8.
 */

public class CaiUtils {
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }


    /**
     * bmp????????? array
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * ????????????????????????????????????????????????
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = null;
                value = field.get(obj);
                map.put(fieldName, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }


    private static final String TAG = "Utils";
    /*
     * global-phone-number = ["+"] 1*( DIGIT / written-sep ) written-sep =
     * ("-"/".")
     */
    private static final Pattern GLOBAL_PHONE_NUMBER_PATTERN = Pattern
            .compile("[\\+]?[0-9.-]+");

    /**
     * ??????????????????????????????
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";

        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    public static int diffDay(Date lastDate) {
        Date date = new Date();
        long i = date.getTime();
        long j = lastDate.getTime();
        if (j > i) {
            return 0;
        }
        if (i == j) {
            return 0;
        }
        long diff = i - j;
        int day = (int) (diff / (24 * 60 * 60 * 1000));
        return day;
    }

    /**
     * ??????????????????????????????
     *
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        String strPattern = "^[a-zA-Z0-9_-]{3,16}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * ???????????????????????????
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return true;
        }
        return false;
    }

    public static String getEmptyStr(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return "??????";
        }
        return str;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param cellPhone
     * @return
     */
    public static boolean checkPhone(String cellPhone) {
        if (TextUtils.isEmpty(cellPhone)) {
            return false;
        }

        Matcher match = GLOBAL_PHONE_NUMBER_PATTERN.matcher(cellPhone);
        return match.matches();

    }

    /**
     * ????????????????????????????????????
     *
     * @param phone
     * @return
     */
    public static boolean checkMobilePhone(String phone) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
        Matcher matcher = p.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * GBK???UTF-8
     *
     * @param str
     * @return
     */
    public static String toUtf8(String str) {
        try {
            str = URLEncoder.encode(str, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * MD5????????????
     *
     * @param data
     * @return
     */
    public static String md5Sign(String data) {
        if (CaiUtils.isEmpty(data)) {
            return "";
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(data.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.exit(-1);
            Log.e(TAG, "md5 sign error " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "md5 sign error " + e.getMessage());
        }

        byte[] byteArray = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                sb.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                sb.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return sb.toString();
    }

    /**
     * DES ??????
     *
     * @param data
     * @param secretkey
     * @return
     */
    public static String DESSign(String data, String secretkey) {
        try {
            byte[] arrBTmp = secretkey.getBytes("utf-8");
            byte[] arrB = new byte[8];
            for (int i = 0; i < arrBTmp.length && i < arrB.length; ++i) {
                arrB[i] = arrBTmp[i];
            }
            Key key = new SecretKeySpec(arrB, "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String dataRes = HexStringUtil.toHexString(cipher.doFinal(data.getBytes("utf-8")));

            return dataRes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ??????
     *
     * @param data
     * @param secretkey
     * @return
     */
    public static String DESDecrypt(String data, String secretkey) {
        try {
            byte[] arrBTmp = secretkey.getBytes("utf-8");
            byte[] arrB = new byte[8];
            for (int i = 0; i < arrBTmp.length && i < arrB.length; ++i) {
                arrB[i] = arrBTmp[i];
            }
            Key key = new SecretKeySpec(arrB, "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] d = cipher.doFinal(HexStringUtil.toByteArray(data));
            String dataRes = new String(d, "utf-8");
            return dataRes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretkey;
    }


    public static String byteArr2HexStr(byte[] digestByte) {
        String hs = "";
        String temp = "";
        for (int n = 0; n < digestByte.length; n++) {
            temp = (Integer.toHexString(digestByte[n] & 0XFF));
            if (temp.length() == 1) {
                hs = hs + "0" + temp;
            } else {
                hs = hs + temp;
            }
        }
        return hs;
    }


    /**
     * ?????????????????????
     *
     * @return
     */
    public static String formatDateTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
        return format.format(date);
    }

    /**
     * ????????????????????????????????????
     *
     * @param fontSize
     * @return
     */
    public static int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * ???textview?????????????????????
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * ?????????????????????
     *
     * @param path
     * @return
     */
    public static String getContentFromFile(String path) {
        StringBuffer sb = new StringBuffer();
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while (true) {
                    String content = reader.readLine();
                    if (content == null) {
                        break;
                    }
                    sb.append(content);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * ????????????????????????
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean diffTime(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * ?????????????????????date????????????string??????
     *
     * @param date_str
     * @return
     */
    public static String dateToString(Date date_str) {
        if (date_str == null) {
            return "";
        }
        String datestr = "";
        try {
            java.text.DateFormat df = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            datestr = df.format(date_str);
        } catch (Exception ex) {
            Log.e(TAG, "date to string error " + ex.getMessage());
        }
        return datestr;
    }

    /**
     * ?????????????????????date????????????string??????
     *
     * @param date_str
     * @return
     */
    public static String dateToString(Date date_str, String str) {
        if (date_str == null) {
            return "";
        }
        String datestr = "";
        try {
            java.text.DateFormat df = new SimpleDateFormat(str);
            datestr = df.format(date_str);
        } catch (Exception ex) {
            Log.e(TAG, "date to string error " + ex.getMessage());
        }
        return datestr;
    }

    /**
     * ?????????????????????yyyy-MM-dd????????????Date
     *
     * @return String
     */
    public static Date getDateByStr(String str) {
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(str);
        } catch (Exception e) {
            System.out.println("String to Date error" + e.getMessage());
        }
        return date;
    }

    /**
     * ?????????????????????yyyy-MM-dd????????????Date
     *
     * @return String
     */
    public static Date getDateByStr(String str, String format) {
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    format);
            date = sdf.parse(str);
        } catch (Exception e) {
            System.out.println("String to Date error" + e.getMessage());
        }
        return date;
    }


    /**
     * ???????????????????????????????????????
     *
     * @param seconds   ????????????????????????
     * @param
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param str1
     * @return
     */
    public static String getShortTime(String str1) {
        Date date = new Date();
        date = getDateByStr(str1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int mMouth = cal.get(Calendar.MONTH) + 1;
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        int mHour = cal.get(Calendar.HOUR_OF_DAY);
        int mMinuts = cal.get(Calendar.MINUTE);
        String mMouthStr = mMouth + "";
        String mDayStr = mDay + "";
        String mHourStr = mHour + "";
        String mMinutsStr = mMinuts + "";
        if (mMouth < 10) {
            mMouthStr = "0" + mMouth + "";
        }
        if (mDay < 10) {
            mDayStr = "0" + mDay + "";
        }
        if (mHour < 10) {
            mHourStr = "0" + mHour + "";
        }
        if (mMinuts < 10) {
            mMinutsStr = "0" + mMinuts + "";
        }
        return (mMouthStr + "-" + mDayStr + " " + mHourStr + ":" + mMinutsStr);
    }

    /**
     * ????????????????????????,???????????????????????????????????????????????????????????????
     *
     * @param task
     */
    public static void cancelTaskInterrupt(AsyncTask<?, ?, ?> task) {
        cancelTask(task, true);
    }

    /**
     * ????????????????????????
     *
     * @param task
     */
    public static void cancelTask(AsyncTask<?, ?, ?> task,
                                  boolean mayInterruptIfRunning) {
        if (task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
            task.cancel(mayInterruptIfRunning);
        }
    }

    public static String getEndDayStr(Integer endTime) {
        long diff = endTime * 1000l - System.currentTimeMillis();
        if (diff <= 0) {
            return "?????????";
        }
        int days = (int) (diff / (24 * 60 * 60 * 1000));
        diff = diff % (24 * 60 * 60 * 100);
        int hours = (int) (diff / (60 * 60 * 1000));
        diff = diff % (60 * 60 * 100);
        int minues = (int) (diff / (60 * 1000));
        String returnStr = "";
        if (days > 0) {
            returnStr = "" + days + "???";
        }
        hours = hours + 8;
        if (hours > 24) {
            days += 1;
            hours -= 24;
        }
        if (hours > 0) {
            returnStr += "" + hours + "??????";
        } else {
            if (!CaiUtils.isEmpty(returnStr)) {
                returnStr += "0??????";
            }
        }
        if (minues > 0) {
            returnStr += "" + minues + "???";
        } else {
            if (!CaiUtils.isEmpty(returnStr)) {
                returnStr += "0???";
            }
        }
        return returnStr;
    }

    public static String diffTime(Date firstDate) {
        long firstTime = firstDate.getTime();
        long nowTime = new Date().getTime();
        long diff = (nowTime - firstTime) / 1000; // ??????
        if (diff <= 60) {
            return "1?????????";
        } else if (diff <= (60 * 60)) {
            int i = (int) (diff / 60);
            int j = (int) (diff % 60);
            if (i == 60) {
                return "1?????????";
            }
            if (j == 0) {
                return "" + i + "?????????";
            } else {
                if (i == 59) {
                    return "1?????????";
                }
                return "" + (i + 1) + "?????????";
            }
        } else if (diff <= (60 * 60 * 24)) {
            int i = (int) (diff / (60 * 60));
            int j = (int) (diff % (60 * 60));
            if (i == 24) {
                return "1??????";
            }
            if (j == 0) {
                return "" + i + "?????????";
            } else {
                if (i == 23) {
                    return "1??????";
                }
                return "" + (i + 1) + "?????????";
            }
        } else if (diff <= (60 * 60 * 24 * 7)) {
            int i = (int) (diff / (60 * 60 * 24));
            int j = (int) (diff % (60 * 60 * 24));
            if (i == 7) {
                return "1??????";
            }
            if (j == 0) {
                return "" + i + "??????";
            } else {
                if (i == 6) {
                    return "1??????";
                }
                return "" + (i + 1) + "??????";
            }
        } else {
            return dateToString(firstDate, "yyyy-MM-dd");
        }
    }

    /**
     * MD5??????
     *
     * @param secret_key
     * @return
     */
    public static String createSign(String secret_key) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(secret_key.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }


    /**
     * ??????2????????????????????????
     *
     * @param str
     * @param str1
     * @return
     */
    public static boolean isStringEquals(String str, String str1) {
        if (str.equals(str1) || (str == str1)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ???????????????????????????
     *
     * @param str
     * @return
     */
    public static int countContentLength(String str) {
        int length = 0;
        str = filterHtml(str);
        String target = "http://";
        int targetLen = target.length();
        int begin = str.indexOf(target, 0);
        if (begin != -1) {
            while (begin != -1) {
                length += begin;
                if (begin + targetLen == str.length()) {
                    str = str.substring(begin);
                    break;
                }
                int i = begin + targetLen;
                char c = str.charAt(i);
                while (((c <= 'Z') && (c >= 'A')) || ((c <= 'z') && (c >= 'a'))
                        || ((c <= '9') && (c >= '0')) || (c == '_')
                        || (c == '.') || (c == '?') || (c == '/') || (c == '%')
                        || (c == '&') || (c == ':') || (c == '=') || (c == '-')) {
                    i++;
                    if (i < str.length()) {
                        c = str.charAt(i);
                    } else {
                        i--;
                        length--;
                        break;
                    }
                }

                length += 10;

                str = str.substring(i);
                begin = str.indexOf(target, 0);
            }

            length += str.length();
        } else {
            length = str.length();
        }

        return length;
    }

    private static String filterHtml(String str) {
        str = str.replaceAll("<(?!br|img)[^>]+>", "").trim();
        str = unicodeToGBK(str);
        str = parseHtml(str);
        str = str.trim();

        return str;
    }

    private static String parseHtml(String newStatus) {
        String temp = "";
        String target = "<img src=";
        int begin = newStatus.indexOf(target, 0);
        if (begin != -1) {
            while (begin != -1) {
                temp = temp + newStatus.substring(0, begin);
                int end = newStatus.indexOf(">", begin + target.length());
                // String t = newStatus.substring(begin + 10, end - 1);

                // temp = temp + (String)ImageAdapter.hashmap.get(t);

                newStatus = newStatus.substring(end + 1);
                begin = newStatus.indexOf(target);
            }
            temp = temp + newStatus;
        } else {
            temp = newStatus;
        }

        return temp;
    }

    private static String unicodeToGBK(String s) {
        String[] k = s.split(";");
        String rs = "";
        for (int i = 0; i < k.length; i++) {
            int strIndex = k[i].indexOf("&#");
            String newstr = k[i];
            if (strIndex > -1) {
                String kstr = "";
                if (strIndex > 0) {
                    kstr = newstr.substring(0, strIndex);
                    rs = rs + kstr;
                    newstr = newstr.substring(strIndex);
                }

                int m = Integer.parseInt(newstr.replace("&#", ""));
                char c = (char) m;
                rs = rs + c;
            } else {
                rs = rs + k[i];
            }
        }
        return rs;
    }

    /**
     * ???????????????
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * ???????????????
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, int message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * ??????????????????????????????
     *
     * @param days
     * @return
     */
    public static String getBeforeDays(int days) {
        Date d = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - days);
        Date beforeDate = now.getTime();
        String s = CaiUtils.dateToString(beforeDate, "yyyy-MM-dd");
        return s;
    }

    /**
     * ??????????????????????????? ????????????????????????
     *
     * @param wd1
     * @param jd1
     * @param wd2
     * @param jd2
     * @return
     */
    public static double D_jw(double wd1, double jd1, double wd2, double jd2) {
        double x, y, out;
        double PI = 3.14159265;
        double R = 6.371229 * 1e6;
        x = (jd2 - jd1) * PI * R * Math.cos(((wd1 + wd2) / 2) * PI / 180) / 180;
        y = (wd2 - wd1) * PI * R / 180;
        out = Math.hypot(x, y);
        return out / 1000;
    }

    /**
     * double???str????????????
     *
     * @param d
     * @return
     */
    public static String doubleToStr(double d) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String str = df.format(d);
        return str;
    }

    /**
     * ????????????
     *
     * @param context
     * @param phoneNum
     */
    public static void makeCall(Context context, String phoneNum) {
        if (CaiUtils.isEmpty(phoneNum)) {
            CaiUtils.showToast(context, "????????????");
            return;
        }
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNum));
        context.startActivity(intent);
    }

    public static String getTxtContent(Context context, String fileName) {
        try {
            StringBuilder builder = new StringBuilder();
            InputStream input = context.getResources().getAssets()
                    .open(fileName);
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader mReader = new BufferedReader(reader);
            String content = null;
            while ((content = mReader.readLine()) != null) {
                builder.append(content);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//	/**
//	 * ??????????????????????????????
//	 *
//	 * @param pTime
//	 *            ?????????????????????
//	 * @return dayForWeek ????????????
//	 * @Exception ????????????
//	 */
//	public static int dayForWeek(String pTime) {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar c = Calendar.getInstance();
//		try {
//			c.setTime(format.parse(pTime));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		int dayForWeek = 0;
//		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
//			dayForWeek = 7;
//		} else {
//			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
//		}
//		Log.i(TAG, "DAY OF WEEK:" + dayForWeek);
//		return dayForWeek;
//	}

    public static void browser(Context context, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(CaiUtils.class.getName(), "browser url is error");
        }
    }


    public static String unitType(String unitType) {
        String str = null;
        if (unitType.equals("1")) {
            str = "??????";
        } else if (unitType.equals("2")) {
            str = "??????";
        } else if (unitType.equals("3")) {
            str = "??????";
        } else if (unitType.equals("4")) {
            str = "??????";
        } else if (unitType.equals("5")) {
            str = "??????";
        }
        return str;
    }


    public static String dianZan(int saygood) {
        String num = null;

        if (saygood > 9999) {
            num = saygood / 10000 + "???+";
        } else
            num = saygood + "";
        return num;
    }


    public static String dizi(String fileInfo) {
        if (fileInfo == null || fileInfo.equals("")) {
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(fileInfo);
            String street = jsonObject.getJSONObject("address").getString("street");
            return street;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * b ????????? mb
     *
     * @param b
     * @return
     */
    public static String bToKbToMb(long b) {
        if (b >= 1024 * 1024) {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(b / 1024f / 1024f) + "MB";
        } else if (b >= 1024) {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(b / 1024f) + "KB";
        } else {
            return b + "B";
        }
    }

    public static boolean saveFile(String AbsolutePath, Serializable info) {
        File file = new File(AbsolutePath);
        ObjectOutputStream oout;
        try {
            oout = new ObjectOutputStream(new FileOutputStream(file));
            oout.writeObject(info);
            oout.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Serializable readFile(String AbsolutePath) {
        List<Serializable> infos = new ArrayList<>();
        File file = new File(AbsolutePath);
        String test[];
        test = file.list();
        if (test != null) {
            for (int i = 0; i < test.length; i++) {
                try {
                    FileInputStream fis = new FileInputStream(AbsolutePath + test[i]);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    Serializable info = (Serializable) ois.readObject();
                    ois.close();
                    infos.add(info);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return (Serializable) infos;
    }


    public static int readFileNum(String AbsolutePath) {
        File file = new File(AbsolutePath);
        return file.list().length;
    }

    public static Serializable readFile2(String AbsolutePath) {
        try {
            FileInputStream fis = new FileInputStream(AbsolutePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Serializable info = (Serializable) ois.readObject();
            ois.close();
            return info;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * ???????????????????????????????????????
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time);
        return t.substring(0, t.length() - 3);
    }

    /**
     * ???????????????????????????
     *
     * @param phone
     */
    public static String phoneHide(String phone) {
        String phoneHide = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phoneHide;
    }

    /**
     * ???????????????8?????????
     * ??????????????????
     *
     * @param idCard
     */
    public static String idCardHide(String idCard) {
        String idCardHide = idCard.replaceAll("(\\d{6})\\d{8}(\\w{4})", "$1*****$2");
        return idCardHide;
    }


    /**
     * ??????????????????bitmap???????????????
     *
     * @param srcPath ????????????
     * @param width   ???????????????????????????????????????????????????????????????????????????????????????????????????
     * @param height  ???????????????????????????????????????????????????????????????????????????????????????????????????
     * @param size    ?????????????????????kb
     * @return ??????????????????bitmap
     */
    public static Bitmap getCompressBitmap(String srcPath, float width, float height, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // ??????????????????????????????options.inJustDecodeBounds ??????true???
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int scaleW = (int) (w / width);
        int scaleH = (int) (h / height);
        int scale = scaleW < scaleH ? scaleH : scaleW;
        if (scale <= 1) {
            scale = 1;
        }
        newOpts.inSampleSize = scale;// ??????????????????
        // ??????????????????????????????????????????options.inJustDecodeBounds ??????false???
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        // ?????????????????????????????????????????????
        return compressImage(bitmap, size);
    }

    /**
     * ??????????????????
     *
     * @param image ?????????bitmap
     * @param size  ????????????????????????kb
     * @return ??????????????????bitmap
     */
    public static Bitmap compressImage(Bitmap image, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // ???????????????????????????100????????????????????????????????????????????????baos???
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        // ?????????????????????????????????????????????size,??????????????????
        while (baos.toByteArray().length / 1024 > size) {
            // ??????baos?????????baos
            baos.reset();
            // ???????????????10
            options -= 10;
            // ????????????options%?????????????????????????????????baos???
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        // ?????????????????????baos?????????ByteArrayInputStream???
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // ???ByteArrayInputStream??????????????????
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * bitmap???????????????
     *
     * @param bm       bitmap
     * @param filePath ????????????
     * @return ?????????????????? true????????????false?????????
     */
    public static boolean saveBitmapToFile(Bitmap bm, String filePath) {
        try {
            File file = new File(filePath);
            file.deleteOnExit();
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            boolean b = false;
            if (filePath.toLowerCase().endsWith(".png")) {
                b = bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            } else {
                b = bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            }
            bos.flush();
            bos.close();
            return b;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return false;
    }


    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    /**
     * 2      * ??????URL??????????????????????????????Map??????
     * 3      * @param url
     * 4      * @return
     * 5
     */
    public static Map<String, String> getUrlPramNameAndValue(String url) {
        String regEx = "(\\?|&+)(.+?)=([^&]*)";//?????????????????????????????????????????????
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);
        // LinkedHashMap????????????Map??????????????????????????????????????????????????????
        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        while (m.find()) {
            String paramName = m.group(2);//???????????????
            String paramVal = m.group(3);//???????????????
            paramMap.put(paramName, paramVal);
        }
        return paramMap;
    }


    public static void showDialog(Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("??????", null);
        builder.create().show();
    }


    /***
     * ????????????????????????
     */


    //????????????
    public static final int NETWORK_NONE=1;
    //????????????
    public static final int NETWORK_MOBILE=0;
    //????????????
    public static final int NETWORW_WIFI=2;
    //??????????????????
    public static int getNetWorkStart(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                //???????????? CONNECTIVITY_SERVICE
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //???????????? NetworkInfo
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo!=null&&activeNetworkInfo.isConnected()){
            //???????????????wifi
            if (activeNetworkInfo.getType()==(ConnectivityManager.TYPE_WIFI)){
                //??????????????????
                return NETWORW_WIFI;
                //????????????????????????
            }else if (activeNetworkInfo.getType()==(ConnectivityManager.TYPE_MOBILE)){
                //??????????????????
                return NETWORK_MOBILE;
            }
        }else {
            //????????????
            return NETWORK_NONE;
        }
        //????????????  ????????????
        return NETWORK_NONE;
    }


    public static String formatCurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static Map<String, String> splitStr(String url) {
//        Mlog.d("url=====" + url);
        Map<String, String> map = new HashMap<String, String>();
        int i1 = url.indexOf("?");
        String substring = url.substring(i1 + 1);
        try {
            if (substring != null) {
                String[] valuse = substring.split("&");
                if (valuse.length > 0) {
                    for (int i = 0; i < valuse.length; i++) {
                        String s = valuse[i];
                        String[] split = s.split("=");
                        map.put(split[0], URLDecoder.decode(split[1], "UTF-8"));

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}
