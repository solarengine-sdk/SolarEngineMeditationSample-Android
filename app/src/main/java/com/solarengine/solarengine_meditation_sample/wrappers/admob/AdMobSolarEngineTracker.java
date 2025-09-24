package com.solarengine.solarengine_meditation_sample.wrappers.admob;

import static com.reyun.solar.engine.AdType.Banner;
import static com.reyun.solar.engine.AdType.InterstitialVideo;
import static com.reyun.solar.engine.AdType.RewardVideo;
import static com.reyun.solar.engine.AdType.Splash;

import android.content.Context;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.AdapterResponseInfo;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.reyun.solar.engine.AdType;
import com.reyun.solar.engine.SolarEngineManager;
import com.reyun.solar.engine.infos.SEAdImpEventModel;
import com.solarengine.solarengine_meditation_sample.wrappers.admob.LogUtils;

import org.json.JSONObject;

/**
 * SolarEngine tracking utility for AdMob ads
 */

public class AdMobSolarEngineTracker {

    /**
     * Track ad impression for AdMob ads with AdValue
     */
    @SuppressWarnings("unused")
    public static void trackAdImpression(Context context,
                                         String adUnitId,
                                         AdapterResponseInfo adInfo,
                                         AdValue adValue,
                                         AdType adType) {
        LogUtils.i("AdMobSolarEngineTracker.trackAdImpression() called ");
        
        try {
            String adSourceName = adInfo.getAdSourceName();
            String adSourceId = adInfo.getAdSourceId();

            SEAdImpEventModel model = new SEAdImpEventModel();
            model.adNetworkPlatform = adSourceName;
            model.mediationPlatform = "Admob";
            model.adType = adType.intValue();
            model.adNetworkAppID = adSourceId;
            model.adNetworkADID = adUnitId;
            model.ecpm = adValue.getValueMicros() / 1000.0; // Convert micros to dollars
            model.currencyType = adValue.getCurrencyCode();;
            model.isRenderSuccess = true;
            model.customProperties = new JSONObject();
            
            SolarEngineManager.getInstance().trackAdImpression(model);
            LogUtils.i("AdMob SolarEngine tracking completed with AdValue");
            
        } catch (Exception e) {
            LogUtils.e("AdMob SolarEngine tracking failed with AdValue: " + e.getMessage());
        }
    }


}
