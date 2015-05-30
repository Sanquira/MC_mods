package BPHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandXRT extends CommandBase {

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return true;
	}

	@Override
	public String getCommandName() {
		return "xrt";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "usage: /xrt <player> <loud,diff> ";
	}

	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
		if (p_71515_2_.length > 3 || p_71515_2_.length == 0) {
			throw new WrongUsageException(getCommandUsage(null), new Object[0]);
		}
		navesti:
		{
			List list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				EntityPlayerMP player = (EntityPlayerMP) iter.next();
				String username = player.getDisplayName();
				if (username.equals(p_71515_2_[0])) {
					p_71515_1_.addChatMessage(new ChatComponentText("Testing " + EnumChatFormatting.BLUE + p_71515_2_[0] + EnumChatFormatting.RESET + " for X-ray RP."));
					ArrayList<String> arr = new ArrayList<String>();
					arr.add(p_71515_1_.getCommandSenderName());
					for (String string : p_71515_2_) {
						arr.add(string);
					}
					BPHelper.network.sendTo(new PacketString("requestTesting", "Server", username, arr), player);
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
