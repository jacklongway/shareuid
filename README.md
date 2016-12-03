# shareuid
> shareUID是提供已经安装app之间的数据访问的通道，我们最常见的场景就是同一个公司不同的app安装在同一手机上，在某些时候可能需要数据，资源，代码共享，但是实现该目的也是有一定限制的，首先保证app之间的签名一致，同时shareUID值相等，才能完全无障碍访问所有资源，不然只能访问部分资源，代码，sharedPreferences等资源是没法访问到的，即使访问到的也是非安全的。

## shareuid常见数据共享的方式有
1. 用户数据共享
2. 资源共享
3. 代码共享

## 用户数据共享
```
 String filename = "share";
        String key = "share";
        String value = ShareUtils.getStringValue(this, targetPackagename, filename, key, "");
        Log.e(TAG, "value:" + value);
```

## 资源共享
```
 String app_name = ShareUtils.getResString(this, targetPackagename, "app_name");
        Log.e(TAG, "app_name:" + app_name);
```

## 代码共享
```
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
```

> 以上只是简单的提供数据访问的几种方式面，比如数据库，assets等等都是可以访问的，但是访问的时候需要考虑数据安全。

## 访问数据简单代码
```
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
```
## 注意
> shareuid的前提是app已经安装到了设备中，不然是访问不了的，如果是没安装的访问，就只能用插件化的方式去加载，然后去访问了。根据自己的需求，选择对应的技术实现就好了。