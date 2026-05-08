package com.iosglass.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import com.iosglass.components.gestures.*
import com.iosglass.components.layout.*
import com.iosglass.components.media.*
import com.iosglass.components.navigation.*
import com.iosglass.components.views.*
import com.iosglass.core.*
import com.iosglass.theme.*

/**
 * iOS Glass Compose — Full Demo App
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IOSGlassDemoApp() {
    IOSTheme {
        val colors = IOSTheme.colors
        val typography = IOSTheme.typography

        // State
        var selectedTab by remember { mutableIntStateOf(0) }
        var darkMode by remember { mutableStateOf(false) }
        var toggle1 by remember { mutableStateOf(true) }
        var toggle2 by remember { mutableStateOf(false) }
        var sliderValue by remember { mutableFloatStateOf(0.6f) }
        var stepperValue by remember { mutableIntStateOf(3) }
        var searchQuery by remember { mutableStateOf("") }
        var showAlert by remember { mutableStateOf(false) }
        var showSheet by remember { mutableStateOf(false) }
        var showActionSheet by remember { mutableStateOf(false) }
        var segmentedIndex by remember { mutableIntStateOf(0) }
        var islandExpanded by remember { mutableStateOf(false) }

        val tabs = listOf(
            TabItem("Home", "🏠", "🏡", badge = 5),
            TabItem("Search", "🔍", "🔎"),
            TabItem("Create", "➕", "✚"),
            TabItem("Activity", "🔔", "🔕", badgeText = "NEW"),
            TabItem("Profile", "👤", "👤"),
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.bgPrimary),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // ── Navigation Bar ──
                GlassNavigationBar(
                    config = NavigationBarConfig(
                        title = "iOS Glass",
                        largeTitle = true,
                    ),
                    trailing = {
                        Text(
                            "⋯",
                            fontSize = 24.sp,
                            color = colors.blue,
                            modifier = Modifier.clickable { },
                        )
                    },
                )

                // ── Scrollable Content ──
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 0.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    Spacer(Modifier.height(4.dp))

                    // Search
                    IOSSearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        placeholder = "Search components...",
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    // Dynamic Island
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        DynamicIsland(
                            content = IslandContent(
                                title = "Now Playing",
                                subtitle = "Chill Vibes · Lofi Mix",
                                leading = "🎵",
                                trailing = "▶️",
                                accentColor = colors.pink,
                                progress = 0.45f,
                            ),
                            expanded = islandExpanded,
                            onClick = { islandExpanded = !islandExpanded },
                        )
                    }

                    // Segmented Control
                    IOSSegmentedControl(
                        items = listOf("Overview", "Components", "Settings"),
                        selectedIndex = segmentedIndex,
                        onSelected = { segmentedIndex = it },
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    // ── Card Carousel ──
                    Text(
                        text = "Featured",
                        style = typography.title2,
                        color = colors.label,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    IOSCardCarousel(
                        items = listOf(
                            CarouselItem("Glass Effects", "Liquid Glass material", "🪟", colors.blue),
                            CarouselItem("Animations", "iOS spring curves", "🎭", colors.purple),
                            CarouselItem("Controls", "Toggles, sliders, more", "🎛️", colors.orange),
                            CarouselItem("Navigation", "Tab bars, nav bars", "🧭", colors.green),
                        ),
                    )

                    // ── Glass Cards ──
                    Text(
                        text = "Glass Cards",
                        style = typography.title2,
                        color = colors.label,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    GlassCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                    ) {
                        Text("Liquid Glass Material", style = typography.headline, color = colors.label)
                        Spacer(Modifier.height(4.dp))
                        Text("Translucent, blurred, with specular highlights", style = typography.subheadline, color = colors.labelSecondary)
                    }

                    GlassCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        pressable = true,
                        onClick = { },
                    ) {
                        Text("Tap Me! 🫧", style = typography.headline, color = colors.label)
                        Text("Press scale animation", style = typography.subheadline, color = colors.labelSecondary)
                    }

                    GlassCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        spec = GlassMaterials.elevated,
                    ) {
                        Text("Elevated Glass", style = typography.headline, color = colors.label)
                        Text("Higher blur, more tint, shadow", style = typography.subheadline, color = colors.labelSecondary)
                    }

                    // ── Controls ──
                    Text(
                        text = "Controls",
                        style = typography.title2,
                        color = colors.label,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    // Toggle row
                    GlassCard(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text("Wi-Fi", style = typography.body, color = colors.label)
                                IOSToggle(checked = toggle1, onCheckedChange = { toggle1 = it })
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text("Bluetooth", style = typography.body, color = colors.label)
                                IOSToggle(checked = toggle2, onCheckedChange = { toggle2 = it })
                            }
                        }
                    }

                    // Slider
                    GlassCard(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text("Brightness", style = typography.subheadline, color = colors.labelSecondary)
                        Spacer(Modifier.height(8.dp))
                        IOSSlider(
                            value = sliderValue,
                            onValueChange = { sliderValue = it },
                        )
                    }

                    // Stepper
                    GlassCard(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("Quantity", style = typography.body, color = colors.label)
                            IOSStepper(
                                value = stepperValue,
                                onValueChange = { stepperValue = it },
                                range = 0..20,
                            )
                        }
                    }

                    // Buttons
                    FlowRow(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        IOSButton(label = "Filled", onClick = { }, style = IOSButtonStyle.Filled)
                        IOSButton(label = "Gray", onClick = { }, style = IOSButtonStyle.Gray)
                        IOSButton(label = "Plain", onClick = { }, style = IOSButtonStyle.Plain)
                        IOSButton(label = "Glass", onClick = { }, style = IOSButtonStyle.Glass, icon = "🪟")
                    }

                    // Alert/Sheet buttons
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        IOSButton(
                            label = "Show Alert",
                            onClick = { showAlert = true },
                            style = IOSButtonStyle.Filled,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        IOSButton(
                            label = "Show Sheet",
                            onClick = { showSheet = true },
                            style = IOSButtonStyle.Gray,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        IOSButton(
                            label = "Show Action Sheet",
                            onClick = { showActionSheet = true },
                            style = IOSButtonStyle.Plain,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    // Chips
                    FlowRow(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        IOSChip(label = "All", selected = true, icon = "🔥")
                        IOSChip(label = "Favorites", icon = "⭐")
                        IOSChip(label = "Recent")
                        IOSChip(label = "Nearby", icon = "📍")
                    }

                    // ── Settings List ──
                    Text(
                        text = "Settings",
                        style = typography.title2,
                        color = colors.label,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    InsetGroupedList(
                        sections = listOf(
                            ListSection(
                                items = listOf(
                                    ListItem(
                                        title = "Airplane Mode",
                                        icon = "✈️",
                                        iconTint = colors.orange,
                                        trailing = { IOSToggle(checked = false, onCheckedChange = {}) },
                                    ),
                                    ListItem(
                                        title = "Wi-Fi",
                                        value = "Home-5G",
                                        icon = "📶",
                                        iconTint = colors.blue,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "Bluetooth",
                                        value = "On",
                                        icon = "🔷",
                                        iconTint = colors.blue,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "Cellular",
                                        icon = "📱",
                                        iconTint = colors.green,
                                        showChevron = true,
                                    ),
                                ),
                            ),
                            ListSection(
                                title = "Notifications",
                                items = listOf(
                                    ListItem(
                                        title = "Notifications",
                                        icon = "🔔",
                                        iconTint = colors.red,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "Sounds & Haptics",
                                        icon = "🔊",
                                        iconTint = colors.pink,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "Focus",
                                        icon = "🌙",
                                        iconTint = colors.indigo,
                                        showChevron = true,
                                    ),
                                ),
                            ),
                            ListSection(
                                title = "General",
                                items = listOf(
                                    ListItem(
                                        title = "About",
                                        icon = "ℹ️",
                                        iconTint = colors.labelSecondary,
                                        showChevron = true,
                                    ),
                                    ListItem(
                                        title = "Software Update",
                                        icon = "📲",
                                        iconTint = colors.red,
                                        showChevron = true,
                                        trailing = {
                                            IOSBadge(count = 1)
                                        },
                                    ),
                                    ListItem(
                                        title = "Reset",
                                        icon = "🔄",
                                        iconTint = colors.labelSecondary,
                                        showChevron = true,
                                    ),
                                ),
                                footer = "Some settings may require administrator access.",
                            ),
                        ),
                    )

                    // ── Hero Card ──
                    IOSHeroCard(
                        title = "Welcome to iOS Glass",
                        subtitle = "The most authentic iOS 26 UI library for Compose",
                        icon = "🪟",
                        gradient = listOf(colors.indigo, colors.purple, colors.pink),
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    // ── Feature Grid ──
                    Text(
                        text = "Quick Access",
                        style = typography.title2,
                        color = colors.label,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    IOSFeatureGrid(
                        items = listOf(
                            FeatureItem("Photos", "🖼️", colors.green),
                            FeatureItem("Camera", "📷", colors.labelSecondary),
                            FeatureItem("Safari", "🧭", colors.blue),
                            FeatureItem("Messages", "💬", colors.green),
                            FeatureItem("Mail", "✉️", colors.blue),
                            FeatureItem("Notes", "📝", colors.yellow),
                            FeatureItem("Maps", "🗺️", colors.green),
                            FeatureItem("Music", "🎵", colors.pink),
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    // ── Empty State ──
                    IOSEmptyState(
                        icon = "📭",
                        title = "No Messages",
                        message = "When you receive messages, they'll appear here.",
                        actionLabel = "Compose",
                        onAction = { },
                    )

                    Spacer(Modifier.height(100.dp))
                }

                // ── Tab Bar ──
                GlassTabBar(
                    items = tabs,
                    selectedIndex = selectedTab,
                    onSelected = { selectedTab = it },
                )
            }

            // ── Overlays ──
            IOSAlert(
                visible = showAlert,
                title = "Liquid Glass",
                message = "This is an iOS 26 style alert with glass material effect.",
                actions = listOf(
                    AlertAction("Cancel", AlertActionStyle.Cancel) { showAlert = false },
                    AlertAction("Delete", AlertActionStyle.Destructive) { showAlert = false },
                    AlertAction("Confirm", AlertActionStyle.Default) { showAlert = false },
                ),
                onDismiss = { showAlert = false },
            )

            IOSActionSheet(
                visible = showActionSheet,
                title = "Choose an action",
                actions = listOf(
                    AlertAction("Share", AlertActionStyle.Default) { showActionSheet = false },
                    AlertAction("Duplicate", AlertActionStyle.Default) { showActionSheet = false },
                    AlertAction("Delete", AlertActionStyle.Destructive) { showActionSheet = false },
                ),
                onCancel = { showActionSheet = false },
            )

            IOSSheet(
                visible = showSheet,
                config = SheetConfig(presentation = SheetPresentation.Half),
                onDismiss = { showSheet = false },
                title = "Details",
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text("This is a half-sheet with glass material.", style = IOSTheme.typography.body)
                    Text("Drag down to dismiss.", style = IOSTheme.typography.subheadline, color = IOSTheme.colors.labelSecondary)
                    Spacer(Modifier.height(200.dp))
                }
            }
        }
    }
}
