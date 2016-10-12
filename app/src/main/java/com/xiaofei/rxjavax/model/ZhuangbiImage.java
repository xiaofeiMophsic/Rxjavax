package com.xiaofei.rxjavax.model;

/**
 * 作者：xiaofei
 * 日期：2016/10/12
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class ZhuangbiImage {
    public String id;
    public String description;
    public String image_url;
    public String file_size;

    @Override
    public String toString() {
        return id + "," + description + "," + image_url + "," + file_size;
    }
}
