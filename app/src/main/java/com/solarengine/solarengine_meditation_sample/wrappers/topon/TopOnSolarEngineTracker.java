package com.solarengine.solarengine_meditation_sample.wrappers.topon;

import android.content.Context;

import com.anythink.core.api.ATAdConst;
import com.anythink.core.api.ATAdInfo;
import com.reyun.solar.engine.SolarEngineManager;
import com.reyun.solar.engine.infos.SEAdImpEventModel;

import org.json.JSONObject;

/**
 * TopOn专用的SolarEngine埋点工具类
 * 用于上报TopOn广告相关事件到SolarEngine
 * 使用Taku API since TopOn and Taku APIs are identical
 */
public class TopOnSolarEngineTracker {
    
    /**
     * 上报TopOn广告展示事件
     * @param context 上下文
     * @param atAdInfo TopOn广告信息对象
     * @param adType 广告类型
     */
    public static void trackAdImpression(Context context, com.anythink.core.api.ATAdInfo atAdInfo
            , com.reyun.solar.engine.AdType adType) {
        try {
            // 从ATAdInfo中提取广告相关信息
            String networkName = extractNetworkName(atAdInfo);
            String adNetworkAppID = "";//TopOn not provided adNetworkAppID
            String adNetworkADID = extractAdNetworkADID(atAdInfo);
            double ecpmPrecision = extractEcpmPrecision(atAdInfo);

            SEAdImpEventModel model = new SEAdImpEventModel();
            model.adNetworkPlatform = networkName;
            model.mediationPlatform = "topon";
            model.adType = adType.intValue();
            model.adNetworkAppID = adNetworkAppID;
            model.adNetworkADID = adNetworkADID;
            model.ecpm = ecpmPrecision;
            model.currencyType = ToponConstants.MY_CURRENCY;
            model.isRenderSuccess = true;
            model.customProperties = new JSONObject();


            SolarEngineManager.getInstance().trackAdImpression(model);
            LogUtils.i("TopOnSolarEngineTracker: SolarEngine广告展示埋点上报成功: network=" + networkName);
            
        } catch (Exception e) {
            LogUtils.e("TopOnSolarEngineTracker: SolarEngine广告展示埋点上报失败: " + e.getMessage());
        }
    }
    
    /**
     * 从ATAdInfo中提取广告单元ID
     */
    private static String extractAdUnitId(Object adInfo) {
        try {
            if (adInfo instanceof com.anythink.core.api.ATAdInfo) {
                com.anythink.core.api.ATAdInfo atAdInfo = (com.anythink.core.api.ATAdInfo) adInfo;
                return atAdInfo.getPlacementId();
            }
        } catch (Exception e) {
            LogUtils.e("TopOnSolarEngineTracker: 提取广告单元ID失败: " + e.getMessage());
        }
        return "unknown";
    }
    
    /**
     * 从ATAdInfo中提取网络名称
     */
    private static String extractNetworkName(Object adInfo) {
        try {
            if (adInfo instanceof ATAdInfo atAdInfo) {
                return String.valueOf(atAdInfo.getNetworkName());
            }
        } catch (Exception e) {
            LogUtils.e("TopOnSolarEngineTracker: 提取网络名称失败: " + e.getMessage());
        }
        return "unknown";
    }

    private static String extractAdNetworkADID(ATAdInfo atAdInfo) {
        try {
            return atAdInfo.getNetworkPlacementId();
        } catch (Exception e) {
            LogUtils.e("TopOnSolarEngineTracker: 提取广告network ADID 失败: " + e.getMessage());
        }
        return "unknown";
    }

    private static double extractEcpmPrecision(ATAdInfo atAdInfo) {
        try {
            if (ToponConstants.MY_CURRENCY.equals(ToponConstants.CNY)) {
                return atAdInfo.getPublisherRevenue(ATAdConst.CURRENCY.RMB);
            } else if (ToponConstants.MY_CURRENCY.equals(ToponConstants.USD)) {
                return atAdInfo.getPublisherRevenue(ATAdConst.CURRENCY.USD);
            }
            return atAdInfo.getPublisherRevenue(ATAdConst.CURRENCY.USD);
        } catch (Exception e) {
            LogUtils.e("TopOnSolarEngineTracker: 提取广告 ecpm 失败: " + e.getMessage());
        }
        return 0;

    }



}
