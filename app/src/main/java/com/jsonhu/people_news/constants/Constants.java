package com.jsonhu.people_news.constants;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.util.UUID;

public class Constants {

    //访问服务端的地址
//    http://www.inglian.cn/  http://ytzhixuan20.52yingzheng.com:80/
    public static String BASE_URL = "http://ytzhixuan20pc.52yingzheng.com/";
//    public static String BASE_URL = "http://www.inglian.cn/";

    public static int MAIN_PAGE_TAB = 1;

    public static String CATLOG = "CATLOG";
    public static String TAB = "TAB";
    private static final boolean LOG_TAG = true;

    public static final String IS_LOGIN_STATUS = "is_login_status";

    public static final String WXAPPID = "wx91c3c71e376ebde1";
    public static final String WXSECRET = "4cb12d3550d11a65cc3e67ee2a6bf607";
    public static void log_i(String className, String tag, String text) {
        if (LOG_TAG) {
            Log.i(className, tag + "----->>" + text);
        }
    }

    public static void print(String className, String tag, String text) {
        if (LOG_TAG) {
            Log.i(className, tag + "------>>" + text);
        }
    }

    public static File getImageCacheFilePath() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File f = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + "//com.lingsi.zhixuan//imageCache");
            if (!f.isDirectory()) {
                f.mkdirs();
            }
            return f;
        } else {
            return null;
        }
    }

    /**
     * 获取uuid
     *
     * @param context
     * @return
     */
    public static String getUUID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }


}
