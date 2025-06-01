package com.paintology.lite.portrait.drawing.ads.consent

interface ConsentCallback {
    fun onAdsLoad(canRequestAd: Boolean) {}
    fun onConsentFormShow() {}
    fun onConsentFormDismissed() {}
    fun onPolicyStatus(required: Boolean) {}
}