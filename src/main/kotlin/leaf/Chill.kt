package com.jay.logburner.leaf

import com.jay.logburner.LogBurner
import org.powbot.api.script.tree.Leaf

class Chill(script: LogBurner) : Leaf<LogBurner>(script, "Chillin") {
    override fun execute() {
        // No need to sleep here, poll() is on 50ms delay loop.
    }
}