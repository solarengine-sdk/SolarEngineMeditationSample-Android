## SolarEngine 冥想示例 – Android

### 为什么有这个示例

- 在一个 Android 应用中演示多种聚合平台（MAX、AdMob、Gromore、IronSource、Taku、TopOn）的端到端集成。
- 展示统一的 Wrapper（封装）模式，用一个 API 将各平台的广告收益/曝光数据上报到 SolarEngine。
- 提供常见广告类型（激励、插屏、横幅、原生、开屏/App Open）的最小可运行示例，并标注收益回调接入点。

### Wrapper 的作用

- 封装各平台 SDK 的调用与回调，将其收益/曝光回调映射为 SolarEngine 的 `trackAdImpression` 上报。
- 统一参数：广告类型、广告位/单元、货币、eCPM 等，避免业务层兼容差异。
- 使 Activity/Manager 更干净：页面只负责加载/展示，Wrapper 负责监听与上报。

### 各平台要点

- AdMob：使用 onPaidEvent（AdValue + ResponseInfo）→ `AdMobAdWrapper.*` → `AdMobSolarEngineTracker`。
- MAX：每种广告类型使用 AdRevenuePaid → `Max*AdWrapper` → `MaxSolarEngineTracker`（含 `MaxAdType` 枚举）。
- Gromore：使用 `getShowEcpm()`（MediationAdEcpmInfo）→ `GromoreAdWrapper` → `GromoreSolarEngineTracker`（含 `GromoreAdType`）。
- IronSource：通过 `IronSourceWrapper.addImpressionDataListener(context, yourListener)` 添加 LevelPlay 监听（不要直接调用 `LevelPlay.addImpressionDataListener(...)`），Wrapper 先上报 `IronSourceSolarEngineTracker`，再转发给你的监听。
- Taku/TopOn：使用各自的回调信息对象 → `TakuAdWrapper` / `TopOnAdWrapper` → 各自的 Tracker。

### 目录结构（概览）

- `app/src/main/java/com/solarengine/solarengine_meditation_sample/`
  - `activity/` 各聚合平台演示页
  - `ads/` 轻量管理类（加载/展示）
  - `wrappers/` 各平台 Wrapper 与 Tracker
  - `config/` 应用与聚合平台配置

### 如何在你的应用中使用

1) 按官方文档集成你选定的聚合 SDK（MAX、AdMob、Gromore、IronSource、Taku、TopOn）。
2) 参考本示例接入各 SDK 的监听/回调 (您可以从[这里](https://github.com/solarengine-sdk/SolarEngineMeditationSample-Android/blob/main/wrappers.zip)下载各聚合平台的wrapper,从中移除您不需要的文件,仅保留您要使用的聚合对应的wrapper文件即可)。
3) 当触发收益/曝光回调时，调用相应 Wrapper 将数据上报到 SolarEngine：
   - AdMob：onPaidEvent → `AdMobAdWrapper.*` → `AdMobSolarEngineTracker`
   - MAX：AdRevenuePaid → `Max*AdWrapper` → `MaxSolarEngineTracker`
   - Gromore：`getShowEcpm()` → `GromoreAdWrapper` → `GromoreSolarEngineTracker`
   - IronSource：`IronSourceWrapper.addImpressionDataListener(...)`（不要直接用 `LevelPlay.addImpressionDataListener(...)`）→ `IronSourceSolarEngineTracker`
   - Taku/TopOn：回调信息 → `TakuAdWrapper` / `TopOnAdWrapper` → 各自 Tracker
4) 业务层只关注加载/展示，Wrapper 负责参数归一与 SolarEngine 上报。

### 范围之外

- 生产级错误处理、隐私合规（如 UMP）与完整变现策略不在此示例范围内，示例聚焦“接线与上报”。

### 说明

- appID/appKey 与广告单元 ID 仅为占位符。
- 发布前请替换为你的真实 ID。


