package com.xiaofei.rxjavax;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.schedulers.Schedulers;

/**
 * 作者：xiaofei
 * 日期：2016/10/9
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class Utils {

    private Utils() {
        throw new UnsupportedOperationException("不要实例化此类！");
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static void storeBitmap(Context context, Bitmap bitmap, String fileName) {
        Schedulers.io().createWorker().schedule(() -> {
            blockStoreBitmap(context, bitmap, fileName);
        });
    }

    private static void blockStoreBitmap(Context context, Bitmap bitmap, String fileName) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
