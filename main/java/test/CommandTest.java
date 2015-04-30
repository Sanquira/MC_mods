package test;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class CommandTest extends CommandBase {

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		// TODO Auto-generated method stub
		return "write /test Player";
	}

	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
		// TODO Auto-generated method stub
		if (p_71515_1_ instanceof EntityPlayer) {
			EntityPlayer pl = (EntityPlayer) p_71515_1_;
			pl.addChatMessage(new ChatComponentText(pl.getCommandSenderName() + ", " + pl.getDisplayName()));
		}
	}

}