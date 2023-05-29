package com.jay.logburner.leaf

import com.jay.logburner.Constants
import com.jay.logburner.LogBurner
import com.jay.logburner.Variables
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.api.rt4.walking.model.Skill
import org.powbot.api.script.tree.Leaf
import org.powbot.mobile.script.ScriptManager

class BurnLogs(script: LogBurner) : Leaf<LogBurner>(script, "Burning Logs") {
    override fun execute() {
        if (Variables.skipTile) {
            Variables.skipTile = false  // We need to set it to false up here in case of infinite loop.
            val oldX = Players.local().x()
            val relocationTile = Tile(oldX - 1, Players.local().y(), 0)
            if (!relocationTile.valid()) {
                script.info("Failed to get a valid relocation tile.")
                return
            }

            if (!Movement.step(relocationTile)
                || !Condition.wait({ oldX != Players.local().x() }, 50, 120)) {
                script.info("Failed to relocate to the tile that is to the west of us.")
                return
            }

            if (!Constants.BURNING_AREA.contains(Players.local()))
                return
        }

        val tinderBox = Inventory.stream().name("Tinderbox").first()
        if (!tinderBox.valid()) {
            script.info("Failed to find the tinderbox in our inventory.")
            return
        }

        val fireLog = Inventory.stream().name(Constants.LOG_TYPES[Variables.logsToBurn]).first()
        if (!fireLog.valid()) {
            script.info("Failed to find any logs to burn in our inventory.")
            return
        }

        if (!tinderBox.interact("Use", true)) {
            script.info("Failed to select the tinderbox.")
            return
        }

        // Short sleep after interaction.
        Condition.sleep(Random.nextGaussian(170, 250, 200, 20.0))
        if (!Condition.wait({ Inventory.selectedItem().valid() }, 50, 25)) {
            script.info("Failed to find that the tinderbox was selected.")
            return
        }

        if (!fireLog.interact("Use")) {
            script.info("Failed to use the tinderbox on the logs.")
            return
        }

        Variables.lastKnownFiremakingXp = Skills.experience(Skill.Firemaking)
        Variables.timeSinceLastFiremakingXp = ScriptManager.getRuntime(true) + 3000     // Failsafe
    }
}