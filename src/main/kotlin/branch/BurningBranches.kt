package com.jay.logburner.branch

import com.jay.logburner.LogBurner
import com.jay.logburner.Variables
import com.jay.logburner.leaf.BurnLogs
import com.jay.logburner.leaf.Chill
import org.powbot.api.rt4.Skills
import org.powbot.api.rt4.walking.model.Skill
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent
import org.powbot.mobile.script.ScriptManager

class BurningCheck(script: LogBurner) : Branch<LogBurner>(script, "Already burning?") {
    override val successComponent: TreeComponent<LogBurner> = Chill(script)
    override val failedComponent: TreeComponent<LogBurner> = BurnLogs(script)

    override fun validate(): Boolean {
        return !Variables.skipTile && Variables.lastKnownFiremakingXp == Skills.experience(Skill.Firemaking)
                && Variables.timeSinceLastFiremakingXp > ScriptManager.getRuntime(true)
    }
}