package com.badlogic.gdx.pay.app;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import com.badlogic.gdx.pay.android.googlebilling.PurchaseManagerGoogleBilling;
import android.widget.LinearLayout;
import android.widget.Toast;


public class AndroidLauncher extends AndroidApplication implements AdsController, RewardedVideoAdListener {
    private RewardedVideoAd rewardedVideoAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        GdxPayApp game = new GdxPayApp(this);
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        initFlavor(game);

        initialize(game, config);
        loadRewardedVideoAd();
    }

    protected void initFlavor(GdxPayApp game) {

        game.purchaseManager = new PurchaseManagerGoogleBilling(this);

    }

    @Override
    public void showRewardedVideo() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(rewardedVideoAd.isLoaded()){
                    rewardedVideoAd.show();
                }
                else loadRewardedVideoAd();
            }
        });
    }

    @Override
    public void loadRewardedVideoAd() {
        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", // Test Ad
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(this, "Ad Loaded", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Toast.makeText(this, "Reward: "+rewardItem.getAmount(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}
