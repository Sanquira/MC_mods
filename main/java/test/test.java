package test;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.PlayerSelector;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeHooks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = test.Info.ID, name = test.Info.NAME, version = test.Info.VERSION)
public class test {

	public static final class Info {
		public static final String ID = "test";
		public static final String NAME = "Test";
		public static final String VERSION = "1.7.10-1.0";
		public static final String CHANNEL = "test";
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
//		MinecraftServer.getServer().getConfigurationManager().playerEntityList.get(0);
//		PlayerSelector.matchOnePlayer(p_82386_0_, p_82386_1_)
		
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent e){
		ServerCommandManager ss = (ServerCommandManager)MinecraftServer.getServer().getCommandManager();
		ss.registerCommand(new CommandTest());
		
	}
}
