package com.jay.logburner.leaf.bankopened

import com.jay.logburner.Variables
import com.jay.logburner.Constants
import com.jay.logburner.LogBurner
import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Skills
import org.powbot.api.rt4.walking.model.Skill
import org.powbot.api.script.tree.Leaf
import org.powbot.mobile.script.ScriptManager

class SetupInventory(script: LogBurner) : Leaf<LogBurner>(script, "Setting Up Inventory") {
    override fun execute() {
        if (Variables.stopAfterMinutes > 0) {
            val minutes: Int = (ScriptManager.getRuntime(true) / 60000).toInt()
            if (minutes >= Variables.stopAfterMinutes) {
                script.info("Script stopping due to runtime goal reached.")
                ScriptManager.stop()
                return
            }
        }

        if (Skills.realLevel(Skill.Firemaking) >= Variables.stopAtLvl) {
            script.info("Script stopping due to firemaking level goal reached.")
            ScriptManager.stop()
            return
        }

        if (Inventory.stream().name("Tinderbox").count().toInt() == 0) {
            val bankTinderBox = Bank.stream().name("Tinderbox").first()
            if (!bankTinderBox.valid()) {
                script.severe("Could not find any tinderboxes in the bank.")
                ScriptManager.stop()
                return
            }

            if (!Bank.withdraw(bankTinderBox, 1)) {
                script.info("Failed to find withdraw a tinderbox.")
                return
            }

            if (!Condition.wait({ Inventory.stream()
                .name(bankTinderBox.name()).count().toInt() != 0 }, 50, 80)) {
                script.info("Failed to find a tinderbox in our inventory.")
                return
            }
        }

        if (Inventory.stream().name(Constants.LOG_TYPES[Variables.logsToBurn]).count().toInt() != 27) {
            val bankLogs = Bank.stream().name(Constants.LOG_TYPES[Variables.logsToBurn]).first()
            if (!bankLogs.valid()) {
                script.severe("Could not find any logs in the bank. ")
                ScriptManager.stop()
                return
            }

            if (bankLogs.stackSize() < 27) {
                script.severe("Could not find enough logs in the bank for another trip.")
                ScriptManager.stop()
                return
            }

            if (!Bank.withdraw(bankLogs, 27)) {
                script.info("Failed to withdraw logs.")
                return
            }

            if (!Condition.wait({ Inventory.stream().name(bankLogs.name()).count().toInt() == 27 }, 50, 80)) {
                script.info("Failed to find enough logs in our inventory for another trip.")
                return
            }
        }
    }
}