package com.jay.logburner.helpers

import com.jay.logburner.Constants
import com.jay.logburner.LoggingService
import com.jay.logburner.Variables
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.GrandExchange
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Skills
import org.powbot.api.rt4.walking.model.Skill
import org.powbot.mobile.script.ScriptManager

object GrandExchangeHelper {
    fun buyLogs(): Boolean {
        if (GrandExchange.opened() && (!GrandExchange.close() || !Condition.wait({ !GrandExchange.opened() }, 50, 80))) {
            LoggingService.info("We failed to close the GE to open the bank to buy logs at the GE.")
            return false
        }

        if (!Bank.opened() && (!Bank.open() || !Condition.wait({ Bank.opened() }, 50, 80))) {
            LoggingService.info("We failed to open the bank to buy logs at the GE.")
            return false
        }

        if (Inventory.isNotEmpty() && (!Bank.depositInventory()
                    || !Condition.wait({ Inventory.isEmpty() }, 50, 100))) {
            LoggingService.info("We failed to deposit our inventory to buy logs at the GE.")
            return false
        }

        var calcCost = 0
        val currFiremakingLevel = Skills.realLevel(Skill.Firemaking)
        for (n in Constants.LOG_COSTS.indices) {
            if (Variables.stopAtLvl > Constants.LOG_LEVELS[n] && currFiremakingLevel < Constants.FIREMAKING_LEVELS[n])
                calcCost += Constants.LOG_COSTS[n] * Constants.LOG_AMOUNTS[n]
        }

        val bankMoney = Bank.stream().name("Coins").first()
        if (!bankMoney.valid() || bankMoney.stackSize() < calcCost) {
            LoggingService.info("We failed to find enough money in our bank.")
            return false
        }

        if (!Bank.withdraw(bankMoney, calcCost)) {
            LoggingService.info("We failed to attempt to withdraw money from the bank.")
            return false
        }

        var money = Inventory.stream().name("Coins").first()
        for (n in 1..80) {
            if (money.valid())
                break

            Condition.sleep(50)
            money = Inventory.stream().name("Coins").first()
        }

        if (!money.valid() ||money.stackSize() < calcCost) {
            LoggingService.info("We failed to find that we have withdrawn enough money from our bank.")
            return false
        }

        if (!Bank.close() || !Condition.wait({ !Bank.opened() }, 50, 80)) {
            LoggingService.info("We failed to close the bank to buy logs at the GE.")
            return false
        }

        if (!GrandExchange.opened() && (!GrandExchange.open()
                    || !Condition.wait({ GrandExchange.opened() }, 50, 80))) {
            LoggingService.info("We failed to open the GE window to buy logs.")
            return false
        }

        for (n in 0..8) {
            if (Variables.stopAtLvl > Constants.LOG_LEVELS[n] && currFiremakingLevel < Constants.FIREMAKING_LEVELS[n]) {
                var geSlot = GrandExchange.createOffer(Constants.LOG_IDS[n], Constants.LOG_AMOUNTS[n],
                    Constants.LOG_COSTS[n], true)
                for (i in 1..4) {
                    if (Condition.wait({ geSlot.isFinished() }, 50, 800))
                        break

                    if (!GrandExchange.pressBackButton() || !GrandExchange.abortOffer(geSlot)
                        || !Condition.wait({ geSlot.isAborted() }, 50, 150)
                        || !GrandExchange.collectOffer(geSlot)
                        || !Condition.wait({ geSlot.isAvailable() }, 50, 150)) {
                        LoggingService.info("Failed to abort GE offer.")
                        ScriptManager.stop()
                        return false
                    }

                    if (i != 4) {
                        geSlot = GrandExchange.createOffer(Constants.LOG_IDS[n], Constants.LOG_AMOUNTS[n],
                            Constants.LOG_COSTS[n], true)
                    }
                }

                for (i in 1..4) {
                    if (GrandExchange.pressBackButton() &&
                        Condition.wait({ !GrandExchange.isCurrentSlot(geSlot) }, 50, 120))
                        break

                    Condition.sleep(Random.nextGaussian(170, 250, 200, 20.0))
                }

                if (GrandExchange.isCurrentSlot(geSlot)) {
                    LoggingService.info("Failed to get back to the main GE window.")
                    ScriptManager.stop()
                    return false
                }
            }
        }

        for (geSlot in GrandExchange.allSlots()) {
            if (!geSlot.isAvailable()) {
                if (!geSlot.isFinished()) {
                    LoggingService.info("Failed to finish the GE offer.")
                    ScriptManager.stop()
                    return false
                }

                if (!GrandExchange.collectOffer(geSlot, GrandExchange.CollectMode.Noted)
                    || !Condition.wait({ geSlot.isAvailable() }, 50, 180)) {
                    LoggingService.info("Failed to collect the GE offer.")
                    ScriptManager.stop()
                    return false
                }
            }
        }

        Variables.buyLogs = false
        return true
    }
}