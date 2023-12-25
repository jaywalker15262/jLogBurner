package com.jay.logburner.branch

import com.jay.logburner.Constants
import com.jay.logburner.LogBurner
import com.jay.logburner.Variables
import com.jay.logburner.leaf.TravelToBurningTile
import com.jay.logburner.leaf.TravelToBurningTileTwo
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent

class AtBurningArea(script: LogBurner) : Branch<LogBurner>(script, "On the burning start tile?") {
    override val successComponent: TreeComponent<LogBurner> = BurningCheck(script)
    override val failedComponent: TreeComponent<LogBurner> = BurningCheckTwo(script)

    override fun validate(): Boolean {
        return Inventory.stream().name(Constants.LOG_TYPES[Variables.logsToBurn]).isNotEmpty()
                && Constants.AREA_BURNING.contains(Players.local()) && !Variables.traveling
    }
}

class ChooseBurningTile(script: LogBurner) : Branch<LogBurner>(script, "Burning tile 1?") {
    override val successComponent: TreeComponent<LogBurner> = TravelToBurningTile(script)
    override val failedComponent: TreeComponent<LogBurner> = TravelToBurningTileTwo(script)

    override fun validate(): Boolean {
        return Variables.secondLine
    }
}