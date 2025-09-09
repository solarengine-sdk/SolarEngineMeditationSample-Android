package com.solarengine.solarengine_meditation_sample.wrappers.max;

import static com.reyun.solar.engine.AdType.Banner;
import static com.reyun.solar.engine.AdType.InterstitialVideo;
import static com.reyun.solar.engine.AdType.RewardVideo;
import static com.reyun.solar.engine.AdType.Splash;

import android.content.Context;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.reyun.solar.engine.AdType;
import com.reyun.solar.engine.SolarEngineManager;
import com.reyun.solar.engine.infos.SEAdImpEventModel;
import com.solarengine.solarengine_meditation_sample.wrappers.max.LogUtils;

import org.json.JSONObject;

/**
 * SolarEngine tracking utility for AppLovin MAX ads
 */
@SuppressWarnings("unused")
public class MaxSolarEngineTracker {

    /**
     * Track ad impression for MAX ads with MaxAd
     */
    @SuppressWarnings("unused")
    public static void trackAdImpression(Context context, MaxAd maxAd, AdType adType,boolean isRenderSuccess) {
        LogUtils.i("MaxSolarEngineTracker.trackAdImpression() called with MaxAd");
        
        try {
            SEAdImpEventModel model = new SEAdImpEventModel();
            model.adNetworkPlatform = maxAd.getNetworkName();
            model.mediationPlatform = "Max";
            model.adType = adType.intValue();
            model.adNetworkAppID = "";
            model.adNetworkADID = maxAd.getNetworkPlacement();
            model.ecpm = maxAd.getRevenue() > 0 ? maxAd.getRevenue() * 1000 : 0.0;
            model.currencyType = MaxConstants.MY_CURRENCY;
            model.isRenderSuccess = isRenderSuccess;
            model.customProperties = new JSONObject();

            SolarEngineManager.getInstance().trackAdImpression(model);
            LogUtils.i("MAX SolarEngine tracking completed with MaxAd");
            
        } catch (Exception e) {
            LogUtils.e("MAX SolarEngine tracking failed with MaxAd: " + e.getMessage());
        }
    }

}
