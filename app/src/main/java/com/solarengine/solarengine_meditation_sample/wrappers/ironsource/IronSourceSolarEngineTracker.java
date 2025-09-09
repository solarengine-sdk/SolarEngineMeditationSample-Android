package com.solarengine.solarengine_meditation_sample.wrappers.ironsource;

import static com.reyun.solar.engine.AdType.Banner;
import static com.reyun.solar.engine.AdType.Interstitial;
import static com.reyun.solar.engine.AdType.Native;
import static com.reyun.solar.engine.AdType.RewardVideo;
import static com.reyun.solar.engine.AdType.Splash;

import android.content.Context;

import androidx.annotation.NonNull;
import com.reyun.solar.engine.SolarEngineManager;
import com.reyun.solar.engine.infos.SEAdImpEventModel;
import com.unity3d.mediation.impression.LevelPlayImpressionData;
import com.reyun.solar.engine.AdType;

import org.json.JSONObject;

import java.util.Locale;

/**
 * IronSource专用的SolarEngine埋点工具类
 * 用于上报IronSource广告相关事件到SolarEngine
 */
public class IronSourceSolarEngineTracker {

    /**
     * 上报IronSource广告展示事件 (使用LevelPlayAdInfo对象)
     * @param context 上下文
     * @param adInfo IronSource广告信息对象
     */
    public static void trackAdImpression(Context context, LevelPlayImpressionData adInfo) {
        try {

            LogUtils.i("IronSourceWrapper: Ad Format: " + adInfo.getAdFormat());

            String networkName = adInfo.getAdNetwork();
            String adNetworkADID = adInfo.getInstanceId();

            SEAdImpEventModel model = new SEAdImpEventModel();
            model.adNetworkPlatform = networkName;
            model.mediationPlatform = "IronSource";

            AdType adType = getAdType(adInfo);

            model.adType = adType.intValue();

            model.adNetworkAppID = "";
            model.adNetworkADID = adNetworkADID;

            Double revenueObj = adInfo.getRevenue();
            double revenue = (revenueObj != null) ? revenueObj : 0.0;
            model.ecpm = revenue * 1000;
            model.currencyType = IronSourceConstants.MY_CURRENCY;
            model.isRenderSuccess = true;
            model.customProperties = new JSONObject();

            SolarEngineManager.getInstance().trackAdImpression(model);
            LogUtils.i("IronSourceSolarEngineTracker: SolarEngine广告展示埋点上报成功: network=" + networkName);
            
        } catch (Exception e) {
            LogUtils.e("IronSourceSolarEngineTracker: SolarEngine广告展示埋点上报失败: " + e.getMessage());
        }
    }

    @NonNull
    private static AdType getAdType(LevelPlayImpressionData adInfo) {
        AdType adType = AdType.OTHER;
        if (adInfo.getAdFormat() != null) {
            String format = adInfo.getAdFormat().toLowerCase(Locale.ROOT);
            if (format.contains("interstitial")) {
                adType = Interstitial;
            } else if (format.contains("rewarded") || format.contains("reward")) {
                adType = RewardVideo;
            } else if (format.contains("banner")) {
                adType = Banner;
            } else if (format.contains("native")) {
                adType = Native;
            } else if (format.contains("splash") || format.contains("appopen")) {
                adType = Splash;
            }
        }
        return adType;
    }

}
