package com.elmohandes.storeegypt.utils

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.FrameLayout
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class AdMobAdsManager(private val context: Context) {

    //sample banner unitId = ca-app-pub-3940256099942544/6300978111

    companion object{
        private lateinit var adView: AdView
        fun setupBannerAd(context: Context, unitId: String, mAdView: FrameLayout){
            MobileAds.initialize(context) {}
            adView = AdView(context)
            val adSize: AdSize = getAdSize(context)
            adView.setAdSize(adSize)
            adView.adUnitId = unitId
            //adView.setAdUnitId("ca-app-pub-5401550443462421/3341187473");
            //my adUnitId ca-app-pub-5401550443462421/3341187473
            // TODO: Add adView to your view hierarchy.
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
            mAdView.addView(adView)

            adView.adListener = object: AdListener() {
                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }

                override fun onAdFailedToLoad(adError : LoadAdError) {
                    // Code to be executed when an ad request fails.
                }

                override fun onAdImpression() {
                    // Code to be executed when an impression is recorded
                    // for an ad.
                }

                override fun onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }
            }


        }



        private fun getAdSize(context: Context): AdSize {
            val displayMetrics = context.resources.displayMetrics
            val widthInPixels = displayMetrics.widthPixels

            // Determine the screen density bucket
            val density = displayMetrics.density

            // Calculate the ad width and height based on screen density
            val adWidth = (widthInPixels / density).toInt()
           // val adHeight = (AdSize.BANNER.getHeightInPixels(context) * density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
        }

        //sample InterstitialAdsId ca-app-pub-3940256099942544/1033173712
        private var mInterstitialAd: InterstitialAd? = null
        fun setupInterstitialAd(context: Context,activity: Activity,unitId:String,viewId:Int){
            MobileAds.initialize(context) {}
            var adRequest = AdRequest.Builder().build()
            InterstitialAd.load(context,unitId, adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("errorTAG", adError.toString())
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    Log.i("loadTAG", "onAdLoaded")

                    Handler().postDelayed({
                        if (mInterstitialAd != null) {
                            mInterstitialAd!!.show(activity)
                        } else {
                            Log.d(
                                "nullTAG", "The interstitial ad wasn't" +
                                        " ready yet."
                            )
                        }
                    }, 500)
                }
            })

            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                    Log.d("interstitial", "Ad was clicked.")
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    Log.d("interstitial", "Ad dismissed fullscreen content.")
                    mInterstitialAd = null
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    // Called when ad fails to show.
                    Log.e("interstitial", "Ad failed to show fullscreen content.")
                    mInterstitialAd = null
                }

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d("interstitial", "Ad recorded an impression.")
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d("interstitial", "Ad showed fullscreen content.")
                }
            }
        }

    }

}