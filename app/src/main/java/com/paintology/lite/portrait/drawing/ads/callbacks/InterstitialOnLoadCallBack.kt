package com.paintology.lite.portrait.drawing.ads.callbacks

interface InterstitialOnLoadCallBack {
    fun onAdFailedToLoad(adError: String)
    fun onAdLoaded()
    fun onPreloaded()
}