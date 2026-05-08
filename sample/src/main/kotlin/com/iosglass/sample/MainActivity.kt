package com.iosglass.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // 默认展示 Apple 设置 App 仿品
            // 切换为组件展示: IOSGlassDemoApp()
            AppleSettingsApp()
        }
    }
}
