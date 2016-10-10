package com.xiaofei.rxjavax.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaofei.rxjavax.R;
import com.xiaofei.rxjavax.model.AppInfo;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者：xiaofei
 * 日期：2016/10/10
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {


    private List<AppInfo> apps;
    private int rowLayout;

    public AppAdapter(List<AppInfo> apps, int rowLayout) {
        this.apps = apps;
        this.rowLayout = rowLayout;
    }

    public void addApps(List<AppInfo> apps) {
        this.apps.clear();
        this.apps.addAll(apps);
        notifyDataSetChanged();
    }

    public void addApp(int position, AppInfo app) {
        if (position < 0) {
            position = 0;
        }
        this.apps.add(position, app);
        notifyItemInserted(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AppInfo appInfo = apps.get(position);
        holder.mTextView.setText(appInfo.getName());
        getBitmap(appInfo.getIcon())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(holder.mImageView::setImageBitmap);
                /*
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        holder.mImageView.setImageBitmap(bitmap);
                    }
                });*/
    }

    @Override
    public int getItemCount() {
        return apps == null ? 0 : apps.size();
    }

    private Observable<Bitmap> getBitmap(String icon) {
        return Observable.create(subscriber -> {
            subscriber.onNext(BitmapFactory.decodeFile(icon));
            subscriber.onCompleted();
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        ImageView mImageView;

        ViewHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.name);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
