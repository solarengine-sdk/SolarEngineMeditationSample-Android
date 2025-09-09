package com.solarengine.solarengine_meditation_sample.wrappers.gromore;
import android.content.Context;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.reyun.solar.engine.AdType;
import com.reyun.solar.engine.SolarEngineManager;
import com.reyun.solar.engine.infos.SEAdImpEventModel;

import org.json.JSONObject;

/**
 * Gromore专用的SolarEngine埋点工具类
 * 用于上报Gromore广告相关事件到SolarEngine
 */
public class GromoreSolarEngineTracker {

    public static void trackAdImpression(Context context, MediationAdEcpmInfo item, AdType adType) {
        try {
            // 从ATAdInfo中提取广告相关信息
            String networkName = item.getSdkName();
            String adNetworkAppID = "";//Gromore not provided adNetworkAppID
            String adNetworkADID = item.getSlotId();

            double ecpmPrecision = 0;
            if (item.getEcpm() != null){
                ecpmPrecision = Float.parseFloat(item.getEcpm()); //分
                ecpmPrecision = ecpmPrecision * 0.01;//元
            }

            SEAdImpEventModel model = new SEAdImpEventModel();
            model.adNetworkPlatform = networkName;
            model.mediationPlatform = "gromore";
            model.adType = adType.intValue();
            model.adNetworkAppID = adNetworkAppID;
            model.adNetworkADID = adNetworkADID;
            model.ecpm = ecpmPrecision;
            model.currencyType = GromoreConstants.MY_CURRENCY;
            model.isRenderSuccess = true;
            model.customProperties = new JSONObject();


            SolarEngineManager.getInstance().trackAdImpression(model);
            com.solarengine.solarengine_meditation_sample.wrappers.taku.LogUtils.i("TakuSolarEngineTracker: SolarEngine广告展示埋点上报成功: network=" + networkName);

        } catch (Exception e) {
            com.solarengine.solarengine_meditation_sample.wrappers.taku.LogUtils.e("TakuSolarEngineTracker: SolarEngine广告展示埋点上报失败: " + e.getMessage());
        }
    }

}
