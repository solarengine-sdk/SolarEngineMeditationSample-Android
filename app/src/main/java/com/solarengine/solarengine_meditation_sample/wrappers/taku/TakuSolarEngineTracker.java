package com.solarengine.solarengine_meditation_sample.wrappers.taku;

import android.content.Context;

import com.anythink.core.api.ATAdConst;
import com.anythink.core.api.ATAdInfo;
import com.reyun.solar.engine.SolarEngineManager;
import com.reyun.solar.engine.infos.SEAdImpEventModel;
import com.solarengine.solarengine_meditation_sample.wrappers.topon.ToponConstants;

import org.json.JSONObject;

/**
 * Taku专用的SolarEngine埋点工具类
 * 用于上报Taku广告相关事件到SolarEngine
 */
public class TakuSolarEngineTracker {
    
    /**
     * 上报Taku广告展示事件
     * @param context 上下文
     * @param atAdInfo Taku广告信息对象
     * @param adType 广告类型
     */
    public static void trackAdImpression(Context context, com.anythink.core.api.ATAdInfo atAdInfo
            , com.reyun.solar.engine.AdType adType) {
        try {
            // 从ATAdInfo中提取广告相关信息
            String networkName = extractNetworkName(atAdInfo);
            String adNetworkAppID = "";//Taku not provided adNetworkAppID
            String adNetworkADID = extractAdNetworkADID(atAdInfo);
            double ecpmPrecision = extractEcpmPrecision(atAdInfo);

            SEAdImpEventModel model = new SEAdImpEventModel();
            model.adNetworkPlatform = networkName;
            model.mediationPlatform = "taku";
            model.adType = adType.intValue();
            model.adNetworkAppID = adNetworkAppID;
            model.adNetworkADID = adNetworkADID;
            model.ecpm = ecpmPrecision;
            model.currencyType = TakuConstants.MY_CURRENCY;
            model.isRenderSuccess = true;
            model.customProperties = new JSONObject();


            SolarEngineManager.getInstance().trackAdImpression(model);
            LogUtils.i("TakuSolarEngineTracker: SolarEngine广告展示埋点上报成功: network=" + networkName);
            
        } catch (Exception e) {
            LogUtils.e("TakuSolarEngineTracker: SolarEngine广告展示埋点上报失败: " + e.getMessage());
        }
    }



    
    /**
     * 从ATAdInfo中提取广告单元ID
     */
    private static String extractAdUnitId(com.anythink.core.api.ATAdInfo atAdInfo) {
        try {
            return atAdInfo.getPlacementId();
        } catch (Exception e) {
            LogUtils.e("TakuSolarEngineTracker: 提取广告单元ID失败: " + e.getMessage());
        }
        return "unknown";
    }
    
    /**
     * 从ATAdInfo中提取网络名称
     */
    private static String extractNetworkName(com.anythink.core.api.ATAdInfo atAdInfo) {
        try {
            return String.valueOf(atAdInfo.getNetworkName());
        } catch (Exception e) {
            LogUtils.e("TakuSolarEngineTracker: 提取网络名称失败: " + e.getMessage());
        }
        return "unknown";
    }

    private static String extractAdNetworkADID(ATAdInfo atAdInfo) {
        try {
            return atAdInfo.getNetworkPlacementId();
        } catch (Exception e) {
            LogUtils.e("TakuSolarEngineTracker: 提取广告network ADID 失败: " + e.getMessage());
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
            return atAdInfo.getPublisherRevenue(ATAdConst.CURRENCY.RMB);
        } catch (Exception e) {
            LogUtils.e("TakuSolarEngineTracker: 提取广告 ecpm 失败: " + e.getMessage());
        }
        return 0;

    }



}
