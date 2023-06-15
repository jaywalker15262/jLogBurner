package com.jay.logburner

import org.powbot.api.Area
import org.powbot.api.Tile
import org.powbot.api.rt4.TilePath

object Constants {
    val LOG_COSTS = arrayOf(100, 100, 50, 400, 30, 700, 400, 1100, 550)
    val LOG_AMOUNTS = arrayOf(108, 243, 135, 405, 324, 1134, 4671, 13662, 22005)
    val LOG_LEVELS = arrayOf(1, 15, 30, 35, 45, 50, 60, 75, 90)
    val LOG_IDS = arrayOf(1511, 1521, 1519, 6333, 1517, 6332, 1515, 1513, 19669)
    val FIREMAKING_LEVELS = arrayOf(15, 30, 35, 45, 50, 60, 75, 90, 99)
    val LOG_TYPES = arrayOf("Logs", "Oak logs", "Willow logs", "Teak logs", "Maple logs", "Mahogany logs", "Yew logs",
        "Magic logs", "Redwood logs")

    val BANK_TILE = Tile(3162, 3490, 0)
    val BURNING_START_TILE = Tile(3183, 3496, 0)
    val BURNING_START_TILE_TWO = Tile(3183, 3497, 0)

    val AREA_BURNING = Area(Tile(3153, 3497, 0), Tile(3183, 3496, 0))

    val BANK_PATH = TilePath(arrayOf(Tile(3154, 3497, 0), Tile(3155, 3497, 0),
        Tile(3156, 3497, 0), Tile(3157, 3496, 0), Tile(3158, 3495, 0),
        Tile(3159, 3494, 0), Tile(3160, 3493, 0), Tile(3161, 3492, 0),
        Tile(3162, 3491, 0), BANK_TILE))
    val BURNING_START_PATH = TilePath(arrayOf(Tile(3162, 3490, 0), Tile(3162, 3491, 0),
        Tile(3162, 3492, 0), Tile(3163, 3493, 0), Tile(3164, 3494, 0),
        Tile(3165, 3495, 0), Tile(3166, 3496, 0), Tile(3167, 3496, 0),
        Tile(3168, 3496, 0), Tile(3169, 3496, 0), Tile(3170, 3496, 0),
        Tile(3171, 3496, 0), Tile(3172, 3496, 0), Tile(3173, 3496, 0),
        Tile(3174, 3496, 0), Tile(3175, 3496, 0), Tile(3176, 3496, 0),
        Tile(3177, 3496, 0), Tile(3178, 3496, 0), Tile(3179, 3496, 0),
        Tile(3180, 3496, 0), Tile(3181, 3496, 0), Tile(3182, 3496, 0),
        BURNING_START_TILE))
    val BURNING_START_PATH_TWO = TilePath(arrayOf(Tile(3162, 3490, 0), Tile(3162, 3491, 0),
        Tile(3162, 3492, 0), Tile(3163, 3493, 0), Tile(3164, 3494, 0),
        Tile(3165, 3495, 0), Tile(3166, 3496, 0), Tile(3167, 3497, 0),
        Tile(3168, 3497, 0), Tile(3169, 3497, 0), Tile(3170, 3497, 0),
        Tile(3171, 3497, 0), Tile(3172, 3497, 0), Tile(3173, 3497, 0),
        Tile(3174, 3497, 0), Tile(3175, 3497, 0), Tile(3176, 3497, 0),
        Tile(3177, 3497, 0), Tile(3178, 3497, 0), Tile(3179, 3497, 0),
        Tile(3180, 3497, 0), Tile(3181, 3497, 0), Tile(3182, 3497, 0),
        BURNING_START_TILE_TWO))
}