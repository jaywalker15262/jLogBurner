package com.jay.logburner.leaf

import com.jay.logburner.Constants
import com.jay.logburner.LogBurner
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Leaf
import org.powbot.dax.api.DaxWalker
import org.powbot.dax.api.models.RunescapeBank

class OpenBank(script: LogBurner) : Leaf<LogBurner>(script, "Opening Bank") {
    override fun execute() {
        if (Bank.inViewport()) {
            if (Bank.open())
                Condition.wait({ Bank.opened() }, Condition.sleep(Random.nextGaussian(170, 250, 200, 20.0)), 13)
        }
        else {
            DaxWalker.walkToBank(RunescapeBank.GRAND_EXCHANGE)
            Camera.turnTo(Bank.nearest().tile())
            Condition.wait({ Bank.inViewport() }, 50, 80)
        }
    }
}