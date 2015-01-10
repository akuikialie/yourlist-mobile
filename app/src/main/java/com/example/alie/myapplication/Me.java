package com.example.alie.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ALIE on 10/01/2015.
 */
public class Me {
    private static String username = null;
    private static String key = null;
    private static Integer level = 3;

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("xyz", Context.MODE_PRIVATE);
    }

    private static void commit(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static void commitInt(Context context, String key, Integer value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private static String getValue(Context context, String key) {
        return getSharedPreferences(context).getString(key, null);
    }
    private static int getInt(Context context, String key) {
        return getSharedPreferences(context).getInt(key, Integer.parseInt(null));
    }

    public static String getUsername(Context context) {
        if(username == null) {
            Me.username = getValue(context, "username");
        }
        return username;
    }

    public static void setUsername(Context context, String username) {
        commit(context, "username", username);
        Me.username = username;
    }

    public static String getKey(Context context) {
        if(key == null) {
            Me.key = getValue(context, "key");
        }
        return key;
    }

    public static void setKey(Context context, String key) {
        commit(context, "key", key);
        Me.key = key;
    }

    public static Integer getLevel(Context context) {
        if(level != 3) {
            Me.level = getInt(context, "level");
        }
        return level;
    }

    public static void setLevel(Context context,Integer level) {
        commitInt(context, "key", level);
        Me.level = level;
    }

}
