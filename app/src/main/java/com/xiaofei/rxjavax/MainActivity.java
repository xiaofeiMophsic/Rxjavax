package com.xiaofei.rxjavax;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.xiaofei.rxjavax.adapter.AppAdapter;
import com.xiaofei.rxjavax.model.AppInfo;
import com.xiaofei.rxjavax.model.AppInfoRich;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.sl_container)
    SwipeRefreshLayout mAppContainer;
    @BindView(R.id.rv_app_list)
    RecyclerView mAppsView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private AppAdapter mAdapter;
    private File mFileDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        mToolbar.setTitle(R.string.app_title);
        setSupportActionBar(mToolbar);

        mAppsView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AppAdapter(new ArrayList<>(), R.layout.item_app_list);
        mAdapter.setOnItemClickListener(this::launchApp);

        mAppsView.setAdapter(mAdapter);
        mAppsView.setItemAnimator(new DefaultItemAnimator());
        mAppContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_purple,
                android.R.color.holo_red_light
        );
        mAppContainer.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
//        mAppContainer.setEnabled(false);
        mAppContainer.post(() -> mAppContainer.setRefreshing(true));
        mAppsView.setVisibility(View.GONE);

        getFileDir()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    mFileDir = file;
                    refreshList();
                });
        mAppContainer.setOnRefreshListener(this::refreshList);
    }

    @DebugLog
    private Observable<File> getFileDir() {
        return Observable.create(subscriber -> {
            subscriber.onNext(App.instance.getFilesDir());
            subscriber.onCompleted();
        });
    }

    @DebugLog
    private Observable<AppInfo> getApps() {
        return Observable.create(subscriber -> {
            Logger.d("some message");
            List<AppInfoRich> apps = new ArrayList<>();
            final Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> ris = getPackageManager().queryIntentActivities(intent, 0);

            ris.forEach(resolveInfo ->
                    apps.add(new AppInfoRich(MainActivity.this, resolveInfo))
            );

            apps.forEach(appInfoRich -> {
                Bitmap icon = Utils.drawableToBitmap(appInfoRich.getIcon());
                String name = appInfoRich.getName();

                String iconPath = mFileDir + "/" + name;
                Utils.storeBitmap(App.instance, icon, name);

                if (subscriber != null) {
                    subscriber.onNext(new AppInfo(appInfoRich.getLastInstallTime(), name, iconPath, appInfoRich.getResolveInfo()));
                }
            });

            if (subscriber != null) {
                subscriber.onCompleted();
            }
        });
    }

    private void launchApp(View view, AppInfo appInfo) {
        ResolveInfo resolveInfo = appInfo.getResolveInfo();
        if (null != resolveInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName(packageName, className));
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            startActivity(intent);
        }
    }

    @DebugLog
    private void refreshList() {
        getApps()
                .subscribeOn(Schedulers.io())
                .toSortedList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "loading complete", Toast.LENGTH_SHORT).show();
                        mAppContainer.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "loading error", Toast.LENGTH_SHORT).show();
                        mAppContainer.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        mAppsView.setVisibility(View.VISIBLE);
                        mAdapter.addApps(appInfos);
                        Logger.d(appInfos);
                    }
                });
    }


}
