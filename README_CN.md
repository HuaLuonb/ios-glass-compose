# iOS Glass Compose

<p align="center">
  <strong>基于 Jetpack Compose 的 iOS 26 Liquid Glass UI 组件库</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-2.1.0-blue.svg" />
  <img src="https://img.shields.io/badge/Compose_BOM-2024.12-green.svg" />
  <img src="https://img.shields.io/badge/minSdk-26-orange.svg" />
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" />
</p>

---

## 📖 项目简介

**iOS Glass Compose** 是一个高仿 Apple iOS 26 设计语言的 Jetpack Compose UI 组件库。

它完整复刻了 iOS 26 的 **Liquid Glass（液态玻璃）** 材质系统，包括：

- 🪟 **毛玻璃模糊** — GPU 加速的 AGSL 着色器实现
- ✨ **折射高光** — 模拟光线穿过玻璃的折射效果
- 🎭 **弹簧动画** — 精确匹配 UIKit 的 CoreAnimation 弹簧曲线
- 🌗 **深色模式** — 完整的 Light / Dark 主题适配
- 📱 **40+ 组件** — 覆盖 iOS 几乎所有常用 UI 控件

> 适用于：想要在 Android 应用中实现 iOS 风格 UI 的开发者。

---

## ✨ 特性一览

### 🪟 Liquid Glass 材质引擎

```
┌─────────────────────────────────────────┐
│  GlassMaterialSpec                      │
│  ├─ tint          半透明色调            │
│  ├─ blurRadius    模糊半径 (GPU加速)    │
│  ├─ refraction    折射率                │
│  ├─ specular      高光强度/角度         │
│  ├─ saturation    饱和度增强            │
│  └─ borderOpacity 边框透明度            │
└─────────────────────────────────────────┘
```

6 种预设材质：
| 材质 | 用途 | 模糊 | 透明度 |
|------|------|------|--------|
| `regular` | 卡片、通用容器 | 30dp | 12% |
| `thick` | 导航栏、Tab栏 | 50dp | 22% |
| `thin` | 搜索栏、标签 | 15dp | 6% |
| `dark` | 暗色模式 | 30dp | 30% |
| `elevated` | 弹窗、模态框 | 60dp | 65% |
| `ultraThin` | 微妙叠加层 | 10dp | 4% |

### 🎭 动画系统

精确匹配 Apple UIKit 的弹簧曲线：

| 动画 | 阻尼比 | 刚度 | 场景 |
|------|--------|------|------|
| `iosSpring` | 0.82 | 300 | 默认交互 |
| `bouncySpring` | 0.60 | 400 | 按钮、开关 |
| `snappySpring` | 0.85 | 600 | 菜单、弹出 |
| `smoothSpring` | 1.00 | 200 | 页面过渡 |
| `gentleSpring` | 0.90 | 150 | 大卡片、模态框 |
| `responsiveSpring` | 0.75 | 500 | 手势跟踪 |

---

## 📦 组件列表

### 🧭 导航 Navigation

| 组件 | 说明 |
|------|------|
| `GlassNavigationBar` | iOS 顶部导航栏，支持返回按钮、大标题、毛玻璃背景 |
| `GlassTabBar` | 底部标签栏，支持徽章、选中动画、毛玻璃背景 |
| `IOSToolbar` | 轻量工具栏 |

### 📄 视图 Views

| 组件 | 说明 |
|------|------|
| `GlassCard` | 毛玻璃卡片，支持按压缩放反馈 |
| `Card` | 普通圆角卡片 |
| `SectionHeader` | 分组列表标题 |
| `Separator` | 分割线 |

### 🎛️ 控件 Controls

| 组件 | 说明 |
|------|------|
| `IOSToggle` | iOS 开关（带弹簧动画） |
| `IOSSlider` | 滑块（支持拖拽） |
| `IOSStepper` | 步进器 (+/-) |
| `IOSButton` | 按钮 (5种风格：Filled/Gray/Plain/Borderless/Glass) |
| `IOSSegmentedControl` | 分段选择器 |
| `IOSSearchBar` | 搜索栏（含取消按钮） |
| `IOSChip` | 标签胶囊 |
| `IOSBadge` | 通知徽章 |
| `IOSLabel` | 图标+文字标签 |
| `IOSLink` | 可点击链接 |

### 🎰 选择器 Pickers

| 组件 | 说明 |
|------|------|
| `IOSPicker` | 滚轮选择器 |
| `IOSDatePicker` | 日期时间滚轮选择器 |
| `IOSColorPicker` | 颜色网格选择器 |

### 💬 反馈 Feedback

| 组件 | 说明 |
|------|------|
| `IOSAlert` | iOS 风格弹窗（毛玻璃材质） |
| `IOSActionSheet` | 底部操作面板 |
| `IOSSheet` | 半屏/全屏 Sheet（支持拖拽关闭） |
| `IOSToast` | 成功/错误/信息提示 |
| `IOSContextMenu` | 长按上下文菜单 |
| `IOSProgressView` | 线性 + 圆形进度条 |
| `DynamicIsland` | 灵动岛（紧凑/展开两种状态） |

### 📐 布局 Layout

| 组件 | 说明 |
|------|------|
| `InsetGroupedList` | iOS Settings 风格分组列表 |
| `DisclosureGroup` | 可展开/折叠的分组 |
| `IOSGrid` | 响应式网格布局 |
| `IOSFeatureGrid` | App Library 风格图标网格 |
| `IOSEmptyState` | 空状态占位图 |
| `IOSHeroCard` | 大型渐变特色卡片 |

### 🎬 媒体 Media

| 组件 | 说明 |
|------|------|
| `IOSPageDots` | 页面指示器小圆点 |
| `IOSPageView` | 水平翻页器 |
| `IOSCardCarousel` | 水平滚动卡片轮播 |

### 👆 手势 Gestures

| 组件 | 说明 |
|------|------|
| `IOSSwipeActionRow` | 滑动操作行 |
| `IOSPullToRefresh` | 下拉刷新 |
| `GlassShimmer` | 加载闪烁效果 |
| `IOSHaptics` | 触觉反馈 |

---

## 🚀 快速开始

### 1. 添加依赖

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

// app/build.gradle.kts
dependencies {
    implementation("com.iosglass:library:1.0.0")
}
```

### 2. 包裹主题

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IOSTheme {
                YourApp()
            }
        }
    }
}
```

### 3. 使用组件

```kotlin
@Composable
fun SettingsScreen() {
    Column {
        // 导航栏
        GlassNavigationBar(
            config = NavigationBarConfig(
                title = "设置",
                largeTitle = true,
            ),
        )

        // 搜索栏
        IOSSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            placeholder = "搜索",
        )

        // 分组列表
        InsetGroupedList(
            sections = listOf(
                ListSection(
                    items = listOf(
                        ListItem(
                            title = "Wi-Fi",
                            value = "Home-5G",
                            icon = "📶",
                            iconTint = IOSTheme.colors.blue,
                            showChevron = true,
                        ),
                        ListItem(
                            title = "蓝牙",
                            value = "开",
                            icon = "🔷",
                            iconTint = IOSTheme.colors.blue,
                            showChevron = true,
                        ),
                    ),
                ),
            ),
        )

        // 底部标签栏
        GlassTabBar(
            items = listOf(
                TabItem("主页", "🏠"),
                TabItem("搜索", "🔍"),
                TabItem("我的", "👤"),
            ),
            selectedIndex = 0,
            onSelected = { },
        )
    }
}
```

---

## 🏗️ 项目结构

```
ios-glass-compose/
├── library/src/main/kotlin/com/iosglass/
│   ├── core/                    # 核心引擎
│   │   ├── GlassMaterial.kt     #   AGSL GPU 毛玻璃着色器
│   │   └── IOSBackgrounds.kt    #   渐变背景系统
│   ├── theme/
│   │   └── IOSTheme.kt          # 完整主题 (40+ 色彩, 11 级字体)
│   ├── animations/
│   │   └── IOSAnimations.kt     # UIKit 弹簧动画曲线
│   └── components/
│       ├── views/               # 卡片、分割线
│       ├── controls/            # 开关、滑块、按钮、选择器、搜索
│       ├── navigation/          # 导航栏、标签栏
│       ├── feedback/            # 弹窗、Sheet、Toast、进度条
│       ├── layout/              # 列表、网格、空状态
│       ├── media/               # 翻页、轮播
│       └── gestures/            # 滑动操作、下拉刷新、闪烁
├── sample/                      # 完整演示 App
├── .github/workflows/
│   ├── build.yml               # CI: 编译 + 测试 + Lint
│   └── release.yml             # CD: 自动构建 APK + 发布 Release
└── docs/                        # 文档
```

---

## ⚡ 性能优化

| 优化项 | 实现方式 |
|--------|----------|
| GPU 渲染 | AGSL `RenderEffect` 着色器，不占主线程 |
| 懒加载 | `LazyColumn` / `LazyRow` 仅渲染可见项 |
| 减少重组 | 大量使用 `remember` / `derivedStateOf` |
| 离屏动画 | `graphicsLayer` 在 GPU 线程执行 (scale/alpha/rotation) |
| Snap Fling | 原生手感的滚动回弹 |

---

## 🎨 设计规范

所有色彩、字体、间距均严格遵循 Apple Human Interface Guidelines：

### 系统色彩

| 颜色 | Light | Dark |
|------|-------|------|
| Blue | `#007AFF` | `#0A84FF` |
| Green | `#34C759` | `#30D158` |
| Red | `#FF3B30` | `#FF453A` |
| Orange | `#FF9500` | `#FF9F0A` |
| Purple | `#AF52DE` | `#BF5AF2` |
| Pink | `#FF2D55` | `#FF375F` |

### 字体层级

| 样式 | 字号 | 字重 | 行高 |
|------|------|------|------|
| Large Title | 34sp | Bold | 41sp |
| Title 1 | 28sp | Bold | 34sp |
| Title 2 | 22sp | Bold | 28sp |
| Headline | 17sp | Semibold | 22sp |
| Body | 17sp | Regular | 22sp |
| Callout | 16sp | Regular | 21sp |
| Subheadline | 15sp | Regular | 20sp |
| Footnote | 13sp | Regular | 18sp |
| Caption | 12sp | Regular | 16sp |

---

## 📥 下载

- 📱 [示例 APK 下载](https://github.com/HuaLuonb/ios-glass-compose/releases) (GitHub Releases)
- 📦 [源码](https://github.com/HuaLuonb/ios-glass-compose/archive/refs/heads/main.zip)

---

## 🤝 参与贡献

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/awesome`)
3. 提交更改 (`git commit -m '添加超棒功能'`)
4. 推送 (`git push origin feature/awesome`)
5. 提交 Pull Request

---

## 📄 开源协议

MIT License — 可自由用于个人和商业项目。

---

## ⭐ 支持项目

如果这个项目对你有帮助，请点个 Star ⭐ 让更多人看到！
