package com.jay.logburner.leaf.bankopened

import com.jay.logburner.LogBurner
import com.jay.logburner.Variables
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.Bank
import org.powbot.api.script.tree.Leaf

class CloseBank(script: LogBurner) : Leaf<LogBurner>(script, "Closing Bank") {
    override fun execute() {
        if (Bank.close()) {
            Variables.secondLine = !Variables.secondLine
            Variables.lastKnownFiremakingXp = 0
            Condition.wait({ !Bank.opened() }, Random.nextGaussian(170, 250, 200, 20.0), 13)
        }
    }
}