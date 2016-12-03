package com.longway.shareuid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // INSTALL_FAILED_SHARED_USER_INCOMPATIBLE
        String targetPackagename = "com.longway.share";
        // 获取资源
        String app_name = ShareUtils.getResString(this, targetPackagename, "app_name");
        Log.e(TAG, "app_name:" + app_name);
        // 获取数据
        String filename = "share";
        String key = "share";
        String value = ShareUtils.getStringValue(this, targetPackagename, filename, key, "");
        Log.e(TAG, "value:" + value);
        // 获取代码
        String cName = "com.longway.share.Print";
        Class<?> clz = ShareUtils.loadClass(this, targetPackagename, cName);
        if (clz != null) {
            try {
                Object o = clz.newInstance();
                Method method = clz.getDeclaredMethod("print", String.class);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                method.invoke(o, TAG);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        // 获取id
        String cNameId = "com.longway.share.R$string";
        clz = ShareUtils.loadClass(this, targetPackagename, cNameId);
        if (clz != null) {
            try {
                Field field = clz.getDeclaredField("app_name");
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                int id = field.getInt(null);
                Log.e(TAG, ShareUtils.getAppname(this, targetPackagename, id));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
