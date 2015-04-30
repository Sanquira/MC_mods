package test;

import java.util.List;

import org.lwjgl.Sys;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandTest extends CommandBase {

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		// TODO Auto-generated method stub
		return "usage: /test <player>";
	}

	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
		if (p_71515_2_.length != 1)
		{
			throw new WrongUsageException(getCommandUsage(null), new Object[0]);
		}
		navesti:
		{
			for (String string : MinecraftServer.getServer().getAllUsernames()) {
				if (string.equals(p_71515_2_[0])) {
					p_71515_1_.addChatMessage(new ChatComponentText("Testing " + EnumChatFormatting.BLUE + p_71515_2_[0] + EnumChatFormatting.RESET + " for X-ray RP. He write you msg with result in second."));
					break navesti;
				}
			}
			throw new PlayerNotFoundException("Player with this nick is not online.", new Object[0]);
		}
	}

	/**
	 * Adds the strings available in this command to the given list of tab completion options.
	 */
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		/**
		 * Returns a List of strings (chosen from the given strings) which the last word in the given string array is a beginning-match for. (Tab completion).
		 */
		return getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
	}

}
