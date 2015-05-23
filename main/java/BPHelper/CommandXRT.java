package BPHelper;

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
		return "usage: /xrt <player> <loud>";
	}

	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
		boolean loud = false;
		if (p_71515_2_.length > 2 || p_71515_2_.length == 0) {
			throw new WrongUsageException(getCommandUsage(null), new Object[0]);
		}
		if (p_71515_2_.length == 2) {
			if (p_71515_2_[1].contains("1")) {
				loud = true;
			}
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
					BPHelper.network.sendTo(new PacketTest(p_71515_1_.getCommandSenderName(), loud), player);
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
