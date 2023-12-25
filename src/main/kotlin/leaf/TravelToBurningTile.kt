package com.jay.logburner.leaf

import com.jay.logburner.Constants
import com.jay.logburner.Variables
import com.jay.logburner.LogBurner
import org.powbot.api.Condition
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Leaf

class TravelToBurningTile(script: LogBurner) : Leaf<LogBurner>(script, "Traveling To Burning Tile") {
    override fun execute() {
        Constants.BURNING_START_PATH.traverse()
        if (Players.local().distanceTo(Constants.BURNING_START_TILE).toInt() > 8 ||
            !Condition.wait({ !Players.local().inMotion()
                    || Players.local().distanceTo(Constants.BURNING_START_TILE).toInt() < 4 }, 50, 50))
            return

        if (!Variables.burningTileMatrix.valid()) {
            Variables.burningTileMatrix = Constants.BURNING_START_TILE.matrix()
            return
        }

        if (!Variables.burningTileMatrix.inViewport()) {
            Camera.turnTo(Variables.burningTileMatrix)
            Condition.wait({ Variables.burningTileMatrix.inViewport() }, 50 ,50)
        }

        if (Players.local().distanceTo(Constants.BURNING_START_TILE).toInt() != 0) {
            if (!Movement.step(Constants.BURNING_START_TILE)) {
                script.info("Failed to step towards the burning start tile.")
                return
            }

            if (!Condition.wait({ Players.local()
                .distanceTo(Constants.BURNING_START_TILE).toInt() == 0 }, 50, 120))
                script.info("Failed to find that we are on the start burning tile.")
        }
    }
}

class TravelToBurningTileTwo(script: LogBurner) : Leaf<LogBurner>(script, "Traveling To Burning Tile 2") {
    override fun execute() {
        Constants.BURNING_START_PATH_TWO.traverse()
        if (Players.local().distanceTo(Constants.BURNING_START_TILE_TWO).toInt() > 8 ||
            !Condition.wait({ !Players.local().inMotion()
                    || Players.local().distanceTo(Constants.BURNING_START_TILE_TWO).toInt() < 4 }, 50, 50))
            return

        if (!Variables.burningTileMatrixTwo.valid()) {
            Variables.burningTileMatrixTwo = Constants.BURNING_START_TILE_TWO.matrix()
            return
        }

        if (!Variables.burningTileMatrixTwo.inViewport()) {
            Camera.turnTo(Variables.burningTileMatrixTwo)
            Condition.wait({ Variables.burningTileMatrixTwo.inViewport() }, 50 ,50)
        }

        if (Players.local().distanceTo(Constants.BURNING_START_TILE_TWO).toInt() != 0) {
            if (!Movement.step(Constants.BURNING_START_TILE_TWO)) {
                script.info("Failed to step towards the burning start tile 2.")
                return
            }

            if (!Condition.wait({ Players.local()
                    .distanceTo(Constants.BURNING_START_TILE_TWO).toInt() == 0 }, 50, 120))
                script.info("Failed to find that we are on the start burning tile 2.")
        }
    }
}