package com.google.android.exoplayer2.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.VideoRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.VideoInfo;
import ui.ExoPlayerRecyclerView;
import utils.DividerItemDecoration;

import static android.widget.LinearLayout.VERTICAL;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewFeed)
    ExoPlayerRecyclerView recyclerViewFeed;

    private List<VideoInfo> videoInfoList = new ArrayList<>();
    private VideoRecyclerViewAdapter mAdapter;
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        prepareVideoList();
        recyclerViewFeed.setVideoInfoList(videoInfoList);
        mAdapter = new VideoRecyclerViewAdapter(videoInfoList);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_drawable);
        recyclerViewFeed.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        recyclerViewFeed.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFeed.setAdapter(mAdapter);

        if (firstTime) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    recyclerViewFeed.playVideo();
                }
            });
            firstTime = false;
        }
        recyclerViewFeed.scrollToPosition(0);
    }

    private void prepareVideoList(){
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setmId(1);
        videoInfo.setmTitle("Apple 4x3 basic stream");
        videoInfo.setmUrl("https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8");

        VideoInfo videoInfo2 = new VideoInfo();
        videoInfo2.setmId(2);
        videoInfo2.setmTitle("Apple 16x9 basic stream");
        videoInfo2.setmUrl("https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_16x9/bipbop_16x9_variant.m3u8");

        VideoInfo videoInfo3 = new VideoInfo();
        videoInfo3.setmId(3);
        videoInfo3.setmTitle("Apple master playlist advanced (TS)");
        videoInfo3.setmUrl("https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_ts/master.m3u8");

        VideoInfo videoInfo4 = new VideoInfo();
        videoInfo4.setmId(4);
        videoInfo4.setmTitle("Magic Pin Video");
        videoInfo4.setmUrl("https://player.vimeo.com/external/286837767.m3u8?s=42570e8c4a91b98cdec7e7bfdf0eccf54e700b69");

        VideoInfo videoInfo5 = new VideoInfo();
        videoInfo5.setmId(5);
        videoInfo5.setmTitle("Magic Pin 2nd Video");
        videoInfo5.setmUrl("https://player.vimeo.com/external/286837723.m3u8?s=3df60d3c1c6c7a11df4047af99c5e05cc2e7ae96");

        videoInfoList.add(videoInfo);
        videoInfoList.add(videoInfo2);
        videoInfoList.add(videoInfo3);
        videoInfoList.add(videoInfo4);
        videoInfoList.add(videoInfo5);
    }

    @Override
    protected void onDestroy() {
        if(recyclerViewFeed!=null)
            recyclerViewFeed.onRelease();
        super.onDestroy();
    }
}
