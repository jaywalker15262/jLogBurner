package com.jay.logburner.leaf.bankopened

import com.jay.logburner.LogBurner
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Leaf

class DepositInventory(script: LogBurner) : Leaf<LogBurner>(script, "Depositing Inventory") {
    override fun execute() {
        if (Bank.depositInventory())
            Condition.wait({ Inventory.isEmpty() }, Condition.sleep(Random.nextGaussian(170, 250, 200, 20.0)), 13)
    }
}