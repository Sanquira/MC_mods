package test;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

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
		return "write /test Player";
	}

	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
		for (String string : MinecraftServer.getServer().getAllUsernames()) {
			System.out.println(string);
		}
		if (p_71515_1_ instanceof EntityPlayer) {
			EntityPlayer pl = (EntityPlayer) p_71515_1_;
			pl.addChatMessage(new ChatComponentText(pl.getCommandSenderName() + ", " + pl.getDisplayName()));
		}
	}

}
