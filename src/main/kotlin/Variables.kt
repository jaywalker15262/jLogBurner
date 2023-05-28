package com.jay.logburner

object Variables {
    var stopAtLvl = 99
    var stopAfterMinutes = 0
    var logsToBurn = 0
    var lastKnownFiremakingXp = 0
    var timeSinceLastFiremakingXp: Long = 0
    var secondLine = false
    var skipTile = false
    var buyLogs = false
    var burningTileMatrix = Constants.BURNING_START_TILE.matrix()
    var burningTileMatrixTwo = Constants.BURNING_START_TILE_TWO.matrix()
}