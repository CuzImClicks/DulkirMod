package dulkirmod.command

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TextUtils
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class JoinDungeonCommand : ClientCommandBase("joindungeon") {

    private val numberToName = mapOf(
        '1' to "ONE",
        '2' to "TWO",
        '3' to "THREE",
        '4' to "FOUR",
        '5' to "FIVE",
        '6' to "SIX",
        '7' to "SEVEN"
    )


    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            TextUtils.info("§cInvalid usage: /joindungeon <type><floor>")
            TextUtils.info("§cExample: /joindungeon M1")
            // /joindungeon M1
            // joindungeon master one
            // joindungeon master 1
            return
        }
        val arguments = args.joinToString("")
        val type = when (arguments[0].uppercase()) {
            "M" -> "MASTER_CATACOMBS"
            "F" -> "CATACOMBS"
            else -> {
                if (args[0].uppercase() == "MASTER_CATACOMBS" || args[0].uppercase() == "CATACOMBS") {
                    args[0].uppercase()
                } else {
                    TextUtils.info("§cInvalid dungeon type: ${arguments[0]} (must be M or F)")
                    return
                }
            }
        }

        val num = when (numberToName.containsKey(arguments[1])) {
            true -> numberToName[arguments[1]]
            false -> {
                if (args.size != 2) {
                    TextUtils.info("§cInvalid dungeon floor: ${arguments[1]} (must be 1-7)")
                    return
                }
                if (numberToName.containsValue(args[1].uppercase())) {
                    args[1].uppercase()
                } else if (numberToName.containsKey(args[1].toCharArray()[0])) {
                    numberToName[args[1].toCharArray()[0]]
                } else {
                    TextUtils.info("§cInvalid dungeon floor: ${args[1]} (must be 1-7)")
                    return
                }

            }
        }



        if (DulkirConfig.dungeonCommandConfirm) {
            TextUtils.info("§6Running command: ${type}_FLOOR_$num")
        }
        TextUtils.sendMessage("/joindungeon ${type}_FLOOR_$num")
    }
}
