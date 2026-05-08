package com.iosglass.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iosglass.components.controls.*
import com.iosglass.components.feedback.*
import com.iosglass.components.layout.*
import com.iosglass.components.media.*
import com.iosglass.components.navigation.*
import com.iosglass.components.views.*
import com.iosglass.core.*
import com.iosglass.theme.*

/**
 * Apple Settings App — iOS 26 Style
 *
 * 仿照 Apple 设置 App 的完整页面
 */
@Composable
fun AppleSettingsApp() {
    IOSTheme {
        val colors = IOSTheme.colors
        val typography = IOSTheme.typography

        var airplaneMode by remember { mutableStateOf(false) }
        var wifiName by remember { mutableStateOf("Home-5G") }
        var bluetoothOn by remember { mutableStateOf(true) }
        var notificationsOn by remember { mutableStateOf(true) }
        var doNotDisturb by remember { mutableStateOf(false) }
        var darkMode by remember { mutableStateOf(false) }
        var searchText by remember { mutableStateOf("") }
        var showAlert by remember { mutableStateOf(false) }
        var selectedTab by remember { mutableStateOf(4) } // Profile tab

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.bgPrimary),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // ── Navigation Bar ──
                GlassNavigationBar(
                    config = NavigationBarConfig(
                        title = "设置",
                        largeTitle = true,
                    ),
                )

                // ── Content ──
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                ) {
                    // Search
                    IOSSearchBar(
                        query = searchText,
                        onQueryChange = { searchText = it },
                        placeholder = "搜索",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    )

                    // Profile Card
                    GlassCard(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        spec = GlassMaterials.regular,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(
                                        listOf(
                                            colors.indigo,
                                            colors.purple,
                                            colors.pink,
                                        ).let {
                                            androidx.compose.ui.graphics.Brush.linearGradient(it)
                                        }
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text("H", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text("HuaLuonb", style = typography.title3, color = colors.label)
                                Text("Apple ID、iCloud、媒体与购买项目", style = typography.subheadline, color = colors.labelSecondary)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // ── Section: Connectivity ──
                    InsetGroupedList(
                        sections = listOf(
                            ListSection(
                                items = listOf(
                                    ListItem(
                                        title = "飞行模式",
                                        icon = "✈️",
                                        iconTint = colors.orange,
                                        trailing = {
                                            IOSToggle(checked = airplaneMode, onCheckedChange = { airplaneMode = it })
                                        },
                                    ),
                                    ListItem(
                                        title = "Wi-Fi",
                                        icon = "📶",
                                        iconTint = colors.blue,
                                        value = wifiName,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "蓝牙",
                                        icon = "🔷",
                                        iconTint = colors.blue,
                                        value = if (bluetoothOn) "开" else "关",
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "蜂窝网络",
                                        icon = "📱",
                                        iconTint = colors.green,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "个人热点",
                                        icon = "🔗",
                                        iconTint = colors.green,
                                        value = "关",
                                        showChevron = true,
                                    ),
                                ),
                            ),
                        ),
                    )

                    Spacer(Modifier.height(24.dp))

                    // ── Section: Notifications ──
                    InsetGroupedList(
                        sections = listOf(
                            ListSection(
                                items = listOf(
                                    ListItem(
                                        title = "通知",
                                        icon = "🔔",
                                        iconTint = colors.red,
                                        trailing = {
                                            IOSToggle(checked = notificationsOn, onCheckedChange = { notificationsOn = it })
                                        },
                                    ),
                                    ListItem(
                                        title = "声音与触感",
                                        icon = "🔊",
                                        iconTint = colors.pink,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "专注模式",
                                        icon = "🌙",
                                        iconTint = colors.indigo,
                                        trailing = {
                                            IOSToggle(checked = doNotDisturb, onCheckedChange = { doNotDisturb = it })
                                        },
                                    ),
                                    ListItem(
                                        title = "屏幕使用时间",
                                        icon = "⏳",
                                        iconTint = colors.indigo,
                                        showChevron = true,
                                    ),
                                ),
                            ),
                        ),
                    )

                    Spacer(Modifier.height(24.dp))

                    // ── Section: General ──
                    InsetGroupedList(
                        sections = listOf(
                            ListSection(
                                items = listOf(
                                    ListItem(
                                        title = "通用",
                                        icon = "⚙️",
                                        iconTint = colors.labelSecondary,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "控制中心",
                                        icon = "🎛️",
                                        iconTint = colors.labelSecondary,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "操作按钮",
                                        icon = "🔘",
                                        iconTint = colors.labelSecondary,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "显示与亮度",
                                        icon = "☀️",
                                        iconTint = colors.blue,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "壁纸",
                                        icon = "🖼️",
                                        iconTint = colors.cyan,
                                        showChevron = true,
                                    ),
                                ),
                            ),
                        ),
                    )

                    Spacer(Modifier.height(24.dp))

                    // ── Section: Accessibility ──
                    InsetGroupedList(
                        sections = listOf(
                            ListSection(
                                items = listOf(
                                    ListItem(
                                        title = "辅助功能",
                                        icon = "♿",
                                        iconTint = colors.blue,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "隐私与安全性",
                                        icon = "🔒",
                                        iconTint = colors.blue,
                                        showChevron = true,
                                    ),
                                ),
                            ),
                        ),
                    )

                    Spacer(Modifier.height(24.dp))

                    // ── Section: Developer ──
                    InsetGroupedList(
                        sections = listOf(
                            ListSection(
                                title = "开发者",
                                items = listOf(
                                    ListItem(
                                        title = "Glass Material",
                                        icon = "🪟",
                                        iconTint = colors.purple,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "Animation Test",
                                        icon = "🎭",
                                        iconTint = colors.pink,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "Component Gallery",
                                        icon = "🧩",
                                        iconTint = colors.orange,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "关于本机",
                                        icon = "ℹ️",
                                        iconTint = colors.labelSecondary,
                                        showChevron = true,
                                        trailing = {
                                            IOSBadge(count = 1)
                                        },
                                    ),
                                ),
                                footer = "iOS Glass Compose v1.0.0 · 基于 Jetpack Compose",
                            ),
                        ),
                    )

                    Spacer(Modifier.height(100.dp))
                }

                // ── Tab Bar ──
                GlassTabBar(
                    items = listOf(
                        TabItem("收藏", "⭐", badge = 3),
                        TabItem("最近", "🕐"),
                        TabItem("联系人", "👤"),
                        TabItem("键盘", "⌨️"),
                        TabItem("设置", "⚙️", selectedIcon = "⚙️"),
                    ),
                    selectedIndex = selectedTab,
                    onSelected = { selectedTab = it },
                )
            }

            // Alert overlay
            IOSAlert(
                visible = showAlert,
                title = "iOS Glass Compose",
                message = "这是一个使用 Liquid Glass 材质的弹窗示例。",
                actions = listOf(
                    AlertAction("取消", AlertActionStyle.Cancel) { showAlert = false },
                    AlertAction("确定", AlertActionStyle.Default) { showAlert = false },
                ),
                onDismiss = { showAlert = false },
            )
        }
    }
}
