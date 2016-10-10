package com.xiaofei.rxjavax.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import java.util.Locale;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 作者：xiaofei
 * 日期：2016/10/9
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class AppInfoRich implements Comparable<AppInfoRich> {

    private String mName;
    private Context mContext;
    private ResolveInfo mResolveInfo;
    private ComponentName mComponentName;
    private PackageInfo mPackageInfo;
    private Drawable icon;

    public AppInfoRich(Context context, ResolveInfo resolveInfo) {
        mContext = context;
        mResolveInfo = resolveInfo;

        mComponentName = new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName,
                resolveInfo.activityInfo.name);
        try {
            mPackageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        if (!TextUtils.isEmpty(mName)) {
            return mName;
        } else {
            try {
                return getNameFromResoveInfo(mResolveInfo);
            } catch (PackageManager.NameNotFoundException e) {
                return getPackageName();
            }
        }
    }

    public void setName(String name) {
        mName = name;
    }

    public String getActivityName() {
        return mResolveInfo.activityInfo.name;
    }

    public String getPackageName() {
        return mResolveInfo.activityInfo.packageName;
    }

    public ComponentName getComponentName() {
        return mComponentName;
    }

    public String getComponentInfo() {
        return mComponentName == null ? "" : mComponentName.toString();
    }

    public ResolveInfo getResolveInfo() {
        return mResolveInfo;
    }

    public PackageInfo getPackageInfo() {
        return mPackageInfo;
    }

    public String getVersionName() {
        return mPackageInfo == null ? "" : mPackageInfo.versionName;
    }

    public int getVersionCode() {
        return mPackageInfo == null ? 0 : mPackageInfo.versionCode;
    }

    public Drawable getIcon() {
        return icon == null ? mResolveInfo.loadIcon(mContext.getPackageManager()) : icon;
    }

    public long getFirstInstallTime() {
        PackageInfo pi = getPackageInfo();
        if (pi != null && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
            return pi.firstInstallTime;
        } else {
            return 0;
        }
    }

    public long getLastInstallTime() {
        PackageInfo pi = getPackageInfo();
        if (pi != null && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
            return pi.lastUpdateTime;
        } else {
            return 0;
        }
    }

    @Override
    public int compareTo(AppInfoRich o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        return mName;
    }

    public String getNameFromResoveInfo(ResolveInfo ri) throws PackageManager.NameNotFoundException {
        String name = ri.resolvePackageName;
        if (null != ri.activityInfo) {
            Resources res = mContext.getPackageManager().getResourcesForApplication(ri.activityInfo.applicationInfo);
            Resources engRes = getEnglishResource(res);

            if (ri.activityInfo.labelRes != 0) {
                name = engRes.getString(ri.activityInfo.labelRes);

                if (TextUtils.isEmpty(name)) {
                    name = res.getString(ri.activityInfo.labelRes);
                }
            } else {
                name = ri.activityInfo.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
            }
        }
        return name;
    }

    public Resources getEnglishResource(Resources resources) {
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.setLocale(Locale.US);
        return new Resources(resources.getAssets(), resources.getDisplayMetrics(), configuration);
    }
}
