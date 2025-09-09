## SolarEngine Meditation Sample – Android

### Why this sample exists

- Demonstrate end‑to‑end integration of multiple ad mediation SDKs (MAX, AdMob, Gromore, IronSource, Taku, TopOn) in a single Android app.
- Show a consistent “wrapper” pattern for isolating SDK specifics and reporting ad revenue/impressions to SolarEngine with one API.
- Provide minimal, runnable examples for common ad types (Rewarded, Interstitial, Banner, Native, Splash/App Open) and where to hook revenue callbacks.

### What the wrappers are for

- Encapsulate each network’s SDK calls and map their revenue/impression callbacks to SolarEngine’s `trackAdImpression`.
- Normalize parameters (ad type, placement/ad unit info, currency, eCPM) across networks before sending to SolarEngine.
- Keep activities/managers clean: activities trigger load/show; wrappers handle SDK listeners and tracking.

### Key points by network

- AdMob: use onPaidEvent (AdValue + ResponseInfo) and forward via `AdMobSolarEngineTracker`.
- MAX: use `AdRevenuePaid` per ad type; forward via `MaxSolarEngineTracker` with a `MaxAdType` enum.
- Gromore: use `getShowEcpm()` (MediationAdEcpmInfo); forward via `GromoreSolarEngineTracker` with `GromoreAdType`.
- IronSource: use `LevelPlayImpressionData`; forward via `IronSourceSolarEngineTracker`.
- Taku/TopOn: use callback info objects; forward via their respective trackers with enums.

### Project layout (high‑level)

- `app/src/main/java/com/solarengine/solarengine_meditation_sample/`
  - `activity/` Simple demo screens per mediation
  - `ads/` Lightweight managers for loading/showing ads
  - `wrappers/` Wrapper + tracker classes per mediation
  - `config/` App and mediation config utilities

### How to use in your app

1) Integrate the mediation SDK you choose (MAX, AdMob, Gromore, IronSource, Taku, or TopOn) following that SDK’s official guide.
2) Use this sample as a reference for wiring that SDK’s listeners/callbacks (You can download the wrapper file from [here](https://github.com/solarengine-sdk/SolarEngineMeditationSample-Android/blob/main/wrappers.zip), delete the mediation wrapper file you don`t need).
3) When the SDK provides revenue/impression callbacks, call the corresponding wrapper to forward data to SolarEngine:
   - AdMob: onPaidEvent ➜ `AdMobAdWrapper.*` which calls `AdMobSolarEngineTracker`
     
     Example (Interstitial):
     
     ```kotlin
     interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {}
     interstitialAd.onPaidEventListener = AdMobAdWrapper.buildInterstitialOnPaidEventListener()
     ```
   - MAX: AdRevenuePaid ➜ `Max*AdWrapper` which calls `MaxSolarEngineTracker`
     
     Example (Rewarded,onAdRevenuePaid):
     
     ```kotlin
     MaxRewardedAd sharedRewarded = MaxRewardedAd.getInstance("YOUR_UNIT_ID", this)
     sharedRewarded.setListener(object : MaxRewardedAdListener {
         override fun onAdRevenuePaid(ad: MaxAd) {
             MaxRewardedAdWrapper.onAdRevenuePaid(ad)
         }
     })
     ```
   - Gromore: `onAdShow()` ➜ `GromoreAdWrapper` which calls `GromoreSolarEngineTracker`
     
     Example (Interstitial, onAdShow):
     
     ```kotlin

    override fun onFullScreenVideoAdLoad(ttFullScreenVideoAd: TTFullScreenVideoAd) {
        LogUtils.i("Gromore Interstitial ad loaded successfully")
        interstitialAd = ttFullScreenVideoAd
        ttFullScreenVideoAd.setFullScreenVideoAdInteractionListener(object
            : TTFullScreenVideoAd.FullScreenVideoAdInteractionListener {
            override fun onAdShow() {
                GromoreAdWrapper.trackInterstitialAdImpression(this@GromoreAdActivity, interstitialAd)
            }
            ...

        })
    }
     ```
   - IronSource: add a `LevelPlayImpressionDataListener` via `IronSourceWrapper.addImpressionDataListener(context, yourListener)` (do NOT call `LevelPlay.addImpressionDataListener(...)` directly); the wrapper tracks to `IronSourceSolarEngineTracker` and then forwards to your listener
     
     Example:
     
     ```kotlin
     IronSourceWrapper.addImpressionDataListener(this) { data ->
         // Your own logic here (wrapper already forwarded to SolarEngine)
     }
     ```
   - Taku/TopOn: callback info ➜ `TakuAdWrapper` / `TopOnAdWrapper` which call their trackers
     
     Example (Taku, Rewarded shown):
     
     ```java
     // In the Topon/Taku rewarded show callback
     public void onRewardedVideoAdPlayStart(String placementId, ATCallbackInfo info) {
         TakuAdWrapper.TrackRewardedAdRevenue(TakuAdType.RewardVideo, info);
     }
     ``` 
4) Keep your app code focused on load/show logic; let wrappers normalize and report to SolarEngine.

 

### Out of scope

- Production error handling, consent/UMP flows, and complete monetization strategy. This sample focuses on wiring and tracking.

### Notes

- appID/appKey and unit IDs are placeholders.
- Replace test IDs with your real IDs before release.


