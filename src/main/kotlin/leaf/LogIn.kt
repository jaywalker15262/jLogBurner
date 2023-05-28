package com.jay.logburner.leaf

import com.jay.logburner.LogBurner
import org.powbot.api.script.tree.Leaf
import org.powbot.mobile.SettingsManager
import org.powbot.mobile.ToggleId

class LogIn(script: LogBurner) : Leaf<LogBurner>(script, "Logging In") {
    override fun execute() {
        if (!SettingsManager.enabled(ToggleId.AutoLogin))
            SettingsManager.set(ToggleId.AutoLogin, true)
    }
}