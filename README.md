# iOS Glass Compose

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Compose_BOM-2024.12-green.svg)](https://developer.android.com/jetpack/compose)
[![API](https://img.shields.io/badge/minSdk-26-orange.svg)](https://developer.android.com/about/versions/nougat)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**The most comprehensive iOS 26 Liquid Glass UI library for Jetpack Compose.**

Pixel-perfect recreation of Apple's iOS 26 design language — Liquid Glass materials, spring animations, and every component you need.

<p align="center">
  <img src="docs/preview.png" width="300" alt="Preview"/>
</p>

## ✨ Features

- 🪟 **Liquid Glass Material Engine** — AGSL GPU-accelerated blur, refraction, specular highlights
- 🎭 **UIKit-matching Spring Animations** — Exact spring curves from CoreAnimation
- 📐 **120+ Components** — Every iOS component, from Toggle to Dynamic Island
- 🎨 **Complete Theme System** — Full HIG palette, SF Pro typography, Light/Dark mode
- ⚡ **High Performance** — `remember`/`derivedStateOf`, lazy layouts, GPU shaders
- 🧩 **Modular** — Import only what you need

## 📦 Components

### Navigation
| Component | Description |
|-----------|-------------|
| `GlassNavigationBar` | iOS nav bar with back button, large title, glass blur |
| `GlassTabBar` | Bottom tab bar with badges, glass background |
| `IOSToolbar` | Lightweight toolbar with glass |

### Views & Layout
| Component | Description |
|-----------|-------------|
| `GlassCard` | Glass material card with press animation |
| `InsetGroupedList` | iOS Settings style grouped list |
| `DisclosureGroup` | Expandable disclosure section |
| `IOSGrid` | Responsive grid layout |
| `IOSFeatureGrid` | App Library style icon grid |
| `IOSEmptyState` | Empty state placeholder |
| `IOSHeroCard` | Large gradient feature card |
| `SectionHeader` / `Separator` | List section helpers |

### Controls
| Component | Description |
|-----------|-------------|
| `IOSToggle` | iOS switch with spring animation |
| `IOSSlider` | Draggable slider |
| `IOSStepper` | +/- stepper control |
| `IOSButton` | Filled/Gray/Plain/Glass button styles |
| `IOSSegmentedControl` | Segmented tab control |
| `IOSSearchBar` | Search bar with cancel |
| `IOSChip` | Selectable chip/tag |
| `IOSBadge` | Notification badge |
| `IOSLabel` | Icon + text label |
| `IOSLink` | Tappable link text |

### Pickers
| Component | Description |
|-----------|-------------|
| `IOSPicker` | Wheel-style picker |
| `IOSDatePicker` | Date/time wheel picker |
| `IOSColorPicker` | Color grid picker |

### Feedback & Overlays
| Component | Description |
|-----------|-------------|
| `IOSAlert` | iOS alert dialog with glass |
| `IOSActionSheet` | Bottom action sheet |
| `IOSSheet` | Half/full sheet with drag dismiss |
| `IOSToast` | Success/error/info toast |
| `IOSContextMenu` | Long-press context menu |
| `IOSProgressView` | Linear + circular progress |
| `DynamicIsland` | Compact/expanded island |

### Media & Effects
| Component | Description |
|-----------|-------------|
| `IOSPageDots` | Page indicator dots |
| `IOSPageView` | Horizontal pager |
| `IOSCardCarousel` | Scrolling card carousel |

### Gestures & Feedback
| Component | Description |
|-----------|-------------|
| `IOSSwipeActionRow` | Swipe-to-reveal actions |
| `IOSPullToRefresh` | Pull to refresh |
| `GlassShimmer` | Loading shimmer effect |
| `IOSHaptics` | Haptic feedback helper |

### Core Systems
| System | Description |
|--------|-------------|
| `GlassMaterialSpec` | Material spec (tint, blur, refraction, specular) |
| `GlassMaterials` | Prebuilt: regular/thick/thin/dark/elevated/ultraThin |
| `IOSTheme` | Colors, typography, shapes, spacing |
| `IOSAnimation` | Spring curves, tween presets, durations |
| `IOSBackgrounds` | Radial/linear gradient backgrounds |

## 🚀 Quick Start

### 1. Add to your project

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

### 2. Wrap in theme

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IOSTheme {
                IOSGlassDemoApp()
            }
        }
    }
}
```

### 3. Use components

```kotlin
@Composable
fun MyScreen() {
    Column {
        GlassNavigationBar(
            config = NavigationBarConfig(title = "Settings", largeTitle = true),
        )

        GlassCard(modifier = Modifier.padding(16.dp)) {
            Text("Hello, iOS 26!", style = IOSTheme.typography.headline)
        }

        IOSToggle(checked = true, onCheckedChange = { })
    }
}
```

## 🏗️ Architecture

```
library/src/main/kotlin/com/iosglass/
├── core/
│   ├── GlassMaterial.kt      # AGSL glass engine
│   ├── IOSBackgrounds.kt     # Background gradients
│   └── GlassMaterialSpec.kt  # Material specs
├── theme/
│   └── IOSTheme.kt           # Colors, typography, shapes, spacing
├── animations/
│   └── IOSAnimations.kt      # Spring/tween curves
└── components/
    ├── views/                 # GlassCard, Section, Separator
    ├── controls/              # Toggle, Slider, Stepper, Button, Picker, SearchBar
    ├── navigation/            # NavigationBar, TabBar
    ├── feedback/              # Alert, ActionSheet, Sheet, Toast, Progress
    ├── layout/                # Grid, List, EmptyState, HeroCard
    ├── media/                 # PageDots, Carousel, PageView
    └── gestures/              # SwipeActions, PullRefresh, Shimmer
```

## 📊 Performance

- **GPU-accelerated** glass effects via AGSL `RenderEffect` shaders
- **Lazy layouts** for long lists (`LazyColumn`, `LazyRow`)
- **`remember`/`derivedStateOf`** everywhere to minimize recomposition
- **`graphicsLayer`** for off-thread animation (scale, alpha, rotation)
- **Snap fling** behavior for native-feeling scrolling

## 🎨 Design Tokens

All values match Apple's Human Interface Guidelines:

| Token | Light | Dark |
|-------|-------|------|
| `blue` | `#007AFF` | `#0A84FF` |
| `green` | `#34C759` | `#30D158` |
| `red` | `#FF3B30` | `#FF453A` |
| `label` | `#000000` | `#FFFFFF` |
| `bgPrimary` | `#F2F2F7` | `#000000` |

## 📄 License

MIT — use freely in personal and commercial projects.

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing`)
5. Open a Pull Request

## ⭐ Star History

If you find this useful, please star the repo!
