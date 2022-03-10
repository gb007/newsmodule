package com.hollysmart.newsmodule.value;

public class Value {

    //TODO
    public static String BASE_URL = "";
    public static String FILE_URL = "";
    public static boolean IS_LOGIN = false;
    public static String USER_ID = "";
    public static String UUID = "";

    public  static int PAGE_SIZE = 20; //页容量，不传默认20，非必填
    public final static String ANIM_TYPE = "animType";
    public final static int ANIM_TYPE_SHANG = 1; //上进 上出
    public final static int ANIM_TYPE_XIA = 2;   //下进 下出
    public final static int ANIM_TYPE_LEFT = 3;  //左进 左出
    public final static int ANIM_TYPE_RIGHT = 4; //右进 右出
    public final static int ANIM_TYPE_SUOFANG = 5; //放大进 缩小出
    public final static int ANIM_TYPE_LONG_LEFT = 6; //左进 左出 350毫秒
    public final static int ANIM_TYPE_LONG_RIGHT = 7; //右进 右出 350毫秒
    public final static int ANIM_TYPE_ALPHA = 8; //右进 右出 350毫秒

}
