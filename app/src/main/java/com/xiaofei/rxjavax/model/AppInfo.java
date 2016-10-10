package com.xiaofei.rxjavax.model;


/**
 * 作者：xiaofei
 * 日期：2016/10/9
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class AppInfo implements Comparable<AppInfo> {

    private long mLastUpdateTime;
    private String mName;
    private String mIcon;

    public AppInfo(long lastUpdateTime, String name, String icon) {
        mLastUpdateTime = lastUpdateTime;
        mName = name;
        mIcon = icon;
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
    public int compareTo(AppInfo other) {
        return getName().compareTo(other.getName());
    }

    @Override
    public String toString() {
        return getName();
    }
}
