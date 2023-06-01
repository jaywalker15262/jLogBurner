package com.jay.logburner

import com.google.common.eventbus.Subscribe
import com.jay.logburner.branch.IsLoggedIn
import com.jay.logburner.helpers.GrandExchangeHelper
import org.powbot.api.Color
import org.powbot.api.event.MessageEvent
import org.powbot.api.event.MessageType
import org.powbot.api.rt4.*
import org.powbot.api.rt4.walking.model.Skill
import org.powbot.api.script.*
import org.powbot.api.script.paint.Paint
import org.powbot.api.script.paint.PaintBuilder
import org.powbot.api.script.tree.TreeComponent
import org.powbot.api.script.tree.TreeScript
import org.powbot.mobile.script.ScriptManager
import org.powbot.mobile.service.ScriptUploader
import java.util.logging.Logger

@ScriptManifest(
    name = "jLogBurner",
    description = "Buys & burns logs at the GE.",
    version = "1.0.0",
    category = ScriptCategory.Firemaking,
    author = "Jaywalker"
)
@ScriptConfiguration.List(
    [
        ScriptConfiguration(
            "buyLogs", "Buy logs on script start? (based on level to stop training firemaking)",
            optionType = OptionType.BOOLEAN, defaultValue = "false"
        ),
        ScriptConfiguration(
            "stopAtLvl", "Stop at lvl(values >99 or <1 means it will not stop based on lvl):",
            optionType = OptionType.INTEGER, defaultValue = "99"
        ),
        ScriptConfiguration(
            "stopAfterMinutes", "Stop after X minutes(0, for the bot to not stop based on time):",
            optionType = OptionType.INTEGER, defaultValue = "0"
        ),
    ]
)

class LogBurner : TreeScript() {
    private val burnLogsErrorMessage = "You can't light a fire here."
    @ValueChanged("buyLogs")
    fun buyLogsChanged(newValue: Boolean) {
        Variables.buyLogs = newValue
    }

    @ValueChanged("stopAtLvl")
    fun stopAtLevelChanged(newValue: Int) {
        Variables.stopAtLvl = newValue
    }

    @ValueChanged("stopAfterMinutes")
    fun stopAfterMinutesChanged(newValue: Int) {
        Variables.stopAfterMinutes = if (newValue > 0)
            newValue else 0
    }

    override val rootComponent: TreeComponent<*> by lazy {
        IsLoggedIn(this)
    }

    override fun onStart() {
        if (Variables.stopAtLvl > 0 && Skills.realLevel(Skill.Firemaking) >= Variables.stopAtLvl) {
            info("We've already reached that level.")
            ScriptManager.stop()
            return
        }

        val p: Paint = PaintBuilder.newBuilder()
            .addString("Last Leaf:") { lastLeaf.name }
            .addString("Stop At Level: ") { Variables.stopAtLvl.toString() }
            .trackSkill(Skill.Firemaking)
            .backgroundColor(Color.argb(255, 199, 57, 18))
            .build()
        addPaint(p)

        if (Variables.buyLogs) {
            for (n in 1..20) {
                if (GrandExchangeHelper.buyLogs())
                    break
                else if (ScriptManager.isStopping())
                    return
            }

            if (Variables.buyLogs) {
                info("We failed to buy logs using the GE.")
                ScriptManager.stop()
            }
        }
    }

    fun info(message: String) {
        log.info("JayLOGS: $message")
    }

    fun severe(message: String) {
        log.severe("JayLOGS: $message")
    }

    @Subscribe
    private fun message(messageEvent: MessageEvent) {
        if (messageEvent.messageType != MessageType.Game)
            return

        if (messageEvent.message == burnLogsErrorMessage)
            Variables.skipTile = true
    }
}

object LoggingService {
    private val logger = Logger.getLogger(this.javaClass.name)
    fun info(message: String) {
        logger.info("JayLOGS: $message")
    }

    fun severe(message: String) {
        logger.severe("JayLOGS: $message")
    }
}

fun main(args: Array<String>) {
    ScriptUploader().uploadAndStart("jLogBurner", "", "127.0.0.1:5595", true, false)
}