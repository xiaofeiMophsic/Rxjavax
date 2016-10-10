package com.xiaofei.rxjavax.model;


import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;

/**
 * 作者：xiaofei
 * 日期：2016/10/9
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class AppInfo implements Comparable<AppInfo> {

    private long mLastUpdateTime;
    private String mName;
    private String mIcon;
    private ResolveInfo mResolveInfo;

    public AppInfo(long lastUpdateTime, String name, String icon, ResolveInfo resolveInfo) {
        mLastUpdateTime = lastUpdateTime;
        mName = name;
        mIcon = icon;
        mResolveInfo = resolveInfo;
    }

    public ResolveInfo getResolveInfo() {
        return mResolveInfo;
    }

    public void setResolveInfo(ResolveInfo resolveInfo) {
        mResolveInfo = resolveInfo;
    }

    public long getLastUpdateTime() {
        return mLastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        mLastUpdateTime = lastUpdateTime;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    @Override
    public int compareTo(@NonNull AppInfo other) {
        return getName().compareTo(other.getName());
    }

    @Override
    public String toString() {
        return getName();
    }
}
