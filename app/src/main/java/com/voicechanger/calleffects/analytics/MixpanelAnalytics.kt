package com.voicechanger.calleffects.analytics

import android.content.Context
import com.voicechanger.calleffects.BuildConfig
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject

object MixpanelAnalytics {
    private var mixpanel: MixpanelAPI? = null
    private var optedOut: Boolean = false

    fun init(context: Context) {
        val token = BuildConfig.MIXPANEL_TOKEN.takeIf { it.isNotBlank() } ?: return
        try {
            mixpanel = MixpanelAPI.getInstance(context.applicationContext, token, false)
            val props = JSONObject().apply {
                put("app_version", BuildConfig.VERSION_NAME)
                put("app_version_code", BuildConfig.VERSION_CODE)
            }
            mixpanel?.registerSuperProperties(props)
        } catch (_: Exception) { mixpanel = null }
    }

    fun optOutTracking() { optedOut = true; mixpanel?.optOutTracking() }
    fun optInTracking() { optedOut = false; mixpanel?.optInTracking() }
    fun isOptedOut(): Boolean = optedOut

    fun track(eventName: String, properties: Map<String, Any?> = emptyMap()) {
        if (optedOut || mixpanel == null) return
        try {
            val props = JSONObject()
            properties.forEach { (k, v) ->
                when (v) {
                    null -> props.put(k, JSONObject.NULL)
                    is String -> props.put(k, v)
                    is Number -> props.put(k, v)
                    is Boolean -> props.put(k, v)
                    else -> props.put(k, v.toString())
                }
            }
            mixpanel?.track(eventName, props)
        } catch (_: Exception) { }
    }

    fun trackScreenView(screenName: String, extra: Map<String, Any?> = emptyMap()) {
        track("screen_view", mutableMapOf<String, Any?>("screen_name" to screenName).apply { putAll(extra) })
    }

    fun trackAppOpen() { track("app_open") }
    fun trackContentViewed(contentId: String, contentType: String) {
        track("content_viewed", mapOf("content_id" to contentId, "content_type" to contentType))
    }
    fun trackSearchPerformed(resultCount: Int, queryLength: Int) {
        track("search_performed", mapOf("result_count" to resultCount, "query_length" to queryLength))
    }
    fun trackAddToFavorites(contentId: String) { track("add_to_favorites", mapOf("content_id" to contentId)) }
    fun trackRemoveFromFavorites(contentId: String) { track("remove_from_favorites", mapOf("content_id" to contentId)) }
    fun flush() { mixpanel?.flush() }
}