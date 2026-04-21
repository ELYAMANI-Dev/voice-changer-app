package com.voicechanger.calleffects

import android.app.Application
import com.voicechanger.calleffects.analytics.MixpanelAnalytics
import com.google.android.gms.ads.MobileAds

class VoiceChangerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        MixpanelAnalytics.init(this)
        MixpanelAnalytics.trackAppOpen()
    }
}