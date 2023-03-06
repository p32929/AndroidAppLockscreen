package p32929.easypasscodelock.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by p32929 on 7/17/2018.
 */

public class EasylockSP {
    public static SharedPreferences sharedPreferences;

    //
    public static void init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("Lockscreen", Context.MODE_PRIVATE);
        }
    }

    //
    @SuppressLint("ApplySharedPref")
    public static void put(String title, boolean value) {
        sharedPreferences.edit().putBoolean(title, value).commit();
    }

    @SuppressLint("ApplySharedPref")
    public static void put(String title, float value) {
        sharedPreferences.edit().putFloat(title, value).commit();
    }

    @SuppressLint("ApplySharedPref")
    public static void put(String title, int value) {
        sharedPreferences.edit().putInt(title, value).commit();
    }

    @SuppressLint("ApplySharedPref")
    public static void put(String title, long value) {
        sharedPreferences.edit().putLong(title, value).commit();
    }

    @SuppressLint("ApplySharedPref")
    public static void put(String title, String value) {
        sharedPreferences.edit().putString(title, value).commit();
    }

    //
    public static boolean getBoolean(String title, boolean defaultValue) {
        return sharedPreferences.getBoolean(title, defaultValue);
    }

    public static float getFloat(String title, float defaultValue) {
        return sharedPreferences.getFloat(title, defaultValue);
    }

    public static int getInt(String title, int defaultValue) {
        return sharedPreferences.getInt(title, defaultValue);
    }

    public static long getLong(String title, long defaultValue) {
        return sharedPreferences.getLong(title, defaultValue);
    }

    public static String getString(String title, String defaultValue) {
        return sharedPreferences.getString(title, defaultValue);
    }

    //
    public static void clearAll() {
        sharedPreferences.edit().clear().commit();
    }

}
