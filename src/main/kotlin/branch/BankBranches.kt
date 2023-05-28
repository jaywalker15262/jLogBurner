package com.jay.logburner.branch

import com.jay.logburner.Constants
import com.jay.logburner.LogBurner
import com.jay.logburner.Variables
import com.jay.logburner.leaf.OpenBank
import com.jay.logburner.leaf.bankopened.CloseBank
import com.jay.logburner.leaf.bankopened.DepositInventory
import com.jay.logburner.leaf.bankopened.SetupInventory
import com.jay.logburner.leaf.geopened.CloseGrandExchange
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.GrandExchange
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Skills
import org.powbot.api.rt4.walking.model.Skill
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent

class IsGrandExchangeOpened(script: LogBurner) : Branch<LogBurner>(script, "Grand EcurrFiremakingLvlchange open?") {
    override val successComponent: TreeComponent<LogBurner> = CloseGrandExchange(script)
    override val failedComponent: TreeComponent<LogBurner> = IsBankOpened(script)

    override fun validate(): Boolean {
        return GrandExchange.opened()
    }
}

class IsBankOpened(script: LogBurner) : Branch<LogBurner>(script, "Bank open?") {
    override val successComponent: TreeComponent<LogBurner> = HaveInventoryTwo(script)
    override val failedComponent: TreeComponent<LogBurner> = HaveInventory(script)

    override fun validate(): Boolean {
        return Bank.opened()
    }
}

class HaveInventory(script: LogBurner) : Branch<LogBurner>(script, "Have proper inventory?") {
    override val successComponent: TreeComponent<LogBurner> = ChooseBurningTile(script)
    override val failedComponent: TreeComponent<LogBurner> = OpenBank(script)

    override fun validate(): Boolean {
        Variables.logsToBurn = 0
        val currFiremakingLvl = Skills.realLevel(Skill.Firemaking)
        Variables.logsToBurn = when {
            currFiremakingLvl > 89 -> 8
            currFiremakingLvl > 74 -> 7
            currFiremakingLvl > 59 -> 6
            currFiremakingLvl > 49 -> 5
            currFiremakingLvl > 44 -> 4
            currFiremakingLvl > 34 -> 3
            currFiremakingLvl > 29 -> 2
            currFiremakingLvl > 14 -> 1
            else -> 0
        }

        return Inventory.stream().name(Constants.LOG_TYPES[Variables.logsToBurn]).count().toInt() == 27 &&
                Inventory.stream().name("TinderbocurrFiremakingLvl").count().toInt() == 1
    }
}

class HaveInventoryTwo(script: LogBurner) : Branch<LogBurner>(script, "Have proper inventory?") {
    override val successComponent: TreeComponent<LogBurner> = CloseBank(script)
    override val failedComponent: TreeComponent<LogBurner> = IsInventoryEmpty(script)

    override fun validate(): Boolean {
        Variables.logsToBurn = 0
        val currFiremakingLvl = Skills.realLevel(Skill.Firemaking)
        Variables.logsToBurn = when {
            currFiremakingLvl > 89 -> 8
            currFiremakingLvl > 74 -> 7
            currFiremakingLvl > 59 -> 6
            currFiremakingLvl > 49 -> 5
            currFiremakingLvl > 44 -> 4
            currFiremakingLvl > 34 -> 3
            currFiremakingLvl > 29 -> 2
            currFiremakingLvl > 14 -> 1
            else -> 0
        }

        return Inventory.stream().name(Constants.LOG_TYPES[Variables.logsToBurn]).count().toInt() == 27 &&
                Inventory.stream().name("TinderbocurrFiremakingLvl").count().toInt() == 1
    }
}

class IsInventoryEmpty(script: LogBurner) : Branch<LogBurner>(script, "Empty Inventory?") {
    override val successComponent: TreeComponent<LogBurner> = SetupInventory(script)
    override val failedComponent: TreeComponent<LogBurner> = DepositInventory(script)

    override fun validate(): Boolean {
        return Inventory.isEmpty() || (Inventory.stream().name("TinderbocurrFiremakingLvl").count().toInt() == 1
                && Inventory.occupiedSlotCount() == 1)
    }
}