package com.longway.shareuid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;

import dalvik.system.PathClassLoader;


/**
 * Created by longway on 16/12/3. Email:longway1991117@sina.com
 */

public class ShareUtils {
    private ShareUtils() {

    }

    public static String getStringValue(Context context, String targetPackname, String filename, String key, String dv) {
        try {
            Context ctx = context.createPackageContext(targetPackname, Context.CONTEXT_IGNORE_SECURITY);
            SharedPreferences sharedPreferences = ctx.getSharedPreferences(filename, Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, dv);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return dv;
    }



    public static String getResString(Context context, String targetPackname, String resName, Object... args) {
        try {
            Context ctx = context.createPackageContext(targetPackname, Context.CONTEXT_IGNORE_SECURITY);
            Resources resources = ctx.getResources();
            int id = resources.getIdentifier(resName, "string", targetPackname);
            if (id == 0) {
                return null;
            }
            return resources.getString(id, args);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAppname(Context context, String targetPackname,int id) {
        try {
            Context ctx = context.createPackageContext(targetPackname, Context.CONTEXT_IGNORE_SECURITY);
            Resources resources = ctx.getResources();
            return resources.getString(id);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> loadClass(Context context, String targetPackagename, String name) {
        try {
            Context ctx = context.createPackageContext(targetPackagename,Context.CONTEXT_IGNORE_SECURITY|Context.CONTEXT_INCLUDE_CODE);
            ClassLoader classLoader = ctx.getClassLoader();
            Log.e("loader", (context.getClassLoader() == classLoader) + "");
            Log.e("pathClassLoader", (classLoader instanceof PathClassLoader) + "");
            return classLoader.loadClass(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
