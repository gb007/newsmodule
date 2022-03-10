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
     * bmp转化成 array
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
     * 获取利用反射获取类里面的值和名称
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
     * 验证邮箱输入是否合法
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
     * 判断密码格式是否正确
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
     * 字符串是否为空判断
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
            return "暂无";
        }
        return str;
    }

    /**
     * 简单判断电话号码格式是否正确
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
     * 判断手机号码格式是否正确
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
     * GBK转UTF-8
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
     * MD5加密算法
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
     * DES 加密
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
     * 解析
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
     * 格式化当前时间
     *
     * @return
     */
    public static String formatDateTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
        return format.format(date);
    }

    /**
     * 根据字体大小获取字体高度
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
     * 将textview中的字符全角化
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
     * 从文件读取信息
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
     * 比较两个时间大小
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
     * 日期格式化，将date类型转成string类型
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
     * 日期格式化，将date类型转成string类型
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
     * 把一个字符串（yyyy-MM-dd）转化成Date
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
     * 把一个字符串（yyyy-MM-dd）转化成Date
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
     * 时间戳转换成日期格式字符串
     *
     * @param seconds   精确到秒的字符串
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
     * 获取月，天，小时和分钟组成的时间
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
     * 退出一个异步任务,如果这个任务正在运行中，则这个任务会被中断
     *
     * @param task
     */
    public static void cancelTaskInterrupt(AsyncTask<?, ?, ?> task) {
        cancelTask(task, true);
    }

    /**
     * 退出一个异步任务
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
            return "已结束";
        }
        int days = (int) (diff / (24 * 60 * 60 * 1000));
        diff = diff % (24 * 60 * 60 * 100);
        int hours = (int) (diff / (60 * 60 * 1000));
        diff = diff % (60 * 60 * 100);
        int minues = (int) (diff / (60 * 1000));
        String returnStr = "";
        if (days > 0) {
            returnStr = "" + days + "天";
        }
        hours = hours + 8;
        if (hours > 24) {
            days += 1;
            hours -= 24;
        }
        if (hours > 0) {
            returnStr += "" + hours + "小时";
        } else {
            if (!CaiUtils.isEmpty(returnStr)) {
                returnStr += "0小时";
            }
        }
        if (minues > 0) {
            returnStr += "" + minues + "分";
        } else {
            if (!CaiUtils.isEmpty(returnStr)) {
                returnStr += "0分";
            }
        }
        return returnStr;
    }

    public static String diffTime(Date firstDate) {
        long firstTime = firstDate.getTime();
        long nowTime = new Date().getTime();
        long diff = (nowTime - firstTime) / 1000; // 秒数
        if (diff <= 60) {
            return "1分钟前";
        } else if (diff <= (60 * 60)) {
            int i = (int) (diff / 60);
            int j = (int) (diff % 60);
            if (i == 60) {
                return "1小时前";
            }
            if (j == 0) {
                return "" + i + "分钟前";
            } else {
                if (i == 59) {
                    return "1小时前";
                }
                return "" + (i + 1) + "分钟前";
            }
        } else if (diff <= (60 * 60 * 24)) {
            int i = (int) (diff / (60 * 60));
            int j = (int) (diff % (60 * 60));
            if (i == 24) {
                return "1天前";
            }
            if (j == 0) {
                return "" + i + "小时前";
            } else {
                if (i == 23) {
                    return "1天前";
                }
                return "" + (i + 1) + "小时前";
            }
        } else if (diff <= (60 * 60 * 24 * 7)) {
            int i = (int) (diff / (60 * 60 * 24));
            int j = (int) (diff % (60 * 60 * 24));
            if (i == 7) {
                return "1周前";
            }
            if (j == 0) {
                return "" + i + "天前";
            } else {
                if (i == 6) {
                    return "1周前";
                }
                return "" + (i + 1) + "天前";
            }
        } else {
            return dateToString(firstDate, "yyyy-MM-dd");
        }
    }

    /**
     * MD5加密
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
     * 判断2个字符串是否相等
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
     * 计算字符串的字符数
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
     * 悬浮框提示
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 悬浮框提示
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, int message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获得当前日期前多少天
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
     * 两个地理位置的间距 返回单位暂未处理
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
     * double转str并格式化
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
     * 拨打电话
     *
     * @param context
     * @param phoneNum
     */
    public static void makeCall(Context context, String phoneNum) {
        if (CaiUtils.isEmpty(phoneNum)) {
            CaiUtils.showToast(context, "号码为空");
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
//	 * 判断当前日期是星期几
//	 *
//	 * @param pTime
//	 *            修要判断的时间
//	 * @return dayForWeek 判断结果
//	 * @Exception 发生异常
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
            str = "美景";
        } else if (unitType.equals("2")) {
            str = "美食";
        } else if (unitType.equals("3")) {
            str = "美宿";
        } else if (unitType.equals("4")) {
            str = "休闲";
        } else if (unitType.equals("5")) {
            str = "购物";
        }
        return str;
    }


    public static String dianZan(int saygood) {
        String num = null;

        if (saygood > 9999) {
            num = saygood / 10000 + "万+";
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
     * b 转换成 mb
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
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time);
        return t.substring(0, t.length() - 3);
    }

    /**
     * 手机号中间四位隐藏
     *
     * @param phone
     */
    public static String phoneHide(String phone) {
        String phoneHide = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phoneHide;
    }

    /**
     * 身份证中间8位隐藏
     * 隐藏出生年月
     *
     * @param idCard
     */
    public static String idCardHide(String idCard) {
        String idCardHide = idCard.replaceAll("(\\d{6})\\d{8}(\\w{4})", "$1*****$2");
        return idCardHide;
    }


    /**
     * 根据路径获取bitmap（压缩后）
     *
     * @param srcPath 图片路径
     * @param width   最大宽（压缩完可能会大于这个，这边只是作为大概限制，避免内存溢出）
     * @param height  最大高（压缩完可能会大于这个，这边只是作为大概限制，避免内存溢出）
     * @param size    图片大小，单位kb
     * @return 返回压缩后的bitmap
     */
    public static Bitmap getCompressBitmap(String srcPath, float width, float height, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
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
        newOpts.inSampleSize = scale;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
        return compressImage(bitmap, size);
    }

    /**
     * 图片质量压缩
     *
     * @param image 传入的bitmap
     * @param size  压缩到多大，单位kb
     * @return 返回压缩完的bitmap
     */
    public static Bitmap compressImage(Bitmap image, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        // 循环判断如果压缩后图片是否大于size,大于继续压缩
        while (baos.toByteArray().length / 1024 > size) {
            // 重置baos即清空baos
            baos.reset();
            // 每次都减少10
            options -= 10;
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * bitmap保存为文件
     *
     * @param bm       bitmap
     * @param filePath 文件路径
     * @return 返回保存结果 true：成功，false：失败
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
     * 2      * 获取URL中的参数名和参数值的Map集合
     * 3      * @param url
     * 4      * @return
     * 5
     */
    public static Map<String, String> getUrlPramNameAndValue(String url) {
        String regEx = "(\\?|&+)(.+?)=([^&]*)";//匹配参数名和参数值的正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);
        // LinkedHashMap是有序的Map集合，遍历时会按照加入的顺序遍历输出
        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        while (m.find()) {
            String paramName = m.group(2);//获取参数名
            String paramVal = m.group(3);//获取参数值
            paramMap.put(paramName, paramVal);
        }
        return paramMap;
    }


    public static void showDialog(Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", null);
        builder.create().show();
    }


    /***
     * 判断网络是否连通
     */


    //没有网络
    public static final int NETWORK_NONE=1;
    //移动网络
    public static final int NETWORK_MOBILE=0;
    //无线网络
    public static final int NETWORW_WIFI=2;
    //获取网络启动
    public static int getNetWorkStart(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                //连接服务 CONNECTIVITY_SERVICE
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //网络信息 NetworkInfo
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo!=null&&activeNetworkInfo.isConnected()){
            //判断是否是wifi
            if (activeNetworkInfo.getType()==(ConnectivityManager.TYPE_WIFI)){
                //返回无线网络
                return NETWORW_WIFI;
                //判断是否移动网络
            }else if (activeNetworkInfo.getType()==(ConnectivityManager.TYPE_MOBILE)){
                //返回移动网络
                return NETWORK_MOBILE;
            }
        }else {
            //没有网络
            return NETWORK_NONE;
        }
        //默认返回  没有网络
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
