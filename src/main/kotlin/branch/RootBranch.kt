package com.jay.logburner.branch

import com.jay.logburner.LogBurner
import com.jay.logburner.leaf.LogIn
import org.powbot.api.rt4.Game
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent

/**
 *  The root node which is executed by the script
 */
class IsLoggedIn(script: LogBurner) : Branch<LogBurner>(script, "Logged in?") {
    override val successComponent: TreeComponent<LogBurner> = AtBurningArea(script)
    override val failedComponent: TreeComponent<LogBurner> = LogIn(script)

    override fun validate(): Boolean {
        return Game.loggedIn()
    }
}