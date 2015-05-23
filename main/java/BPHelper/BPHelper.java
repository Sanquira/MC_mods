package BPHelper;

import java.util.ArrayList;

import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = BPHelper.Info.ID, name = BPHelper.Info.NAME, version = BPHelper.Info.VERSION)
public class BPHelper {

	public static final class Info {
		public static final String ID = "bphelper";
		public static final String NAME = "BPHelper";
		public static final String VERSION = "1.7.10-1.0";
		public static final String CHANNEL = "bphelper";
	}

	public static SimpleNetworkWrapper network;

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		network = NetworkRegistry.INSTANCE.newSimpleChannel(Info.CHANNEL);
		network.registerMessage(PacketTest.Handler.class, PacketTest.class, 0, Side.SERVER);
		network.registerMessage(PacketTest.Handler.class, PacketTest.class, 1, Side.CLIENT);
		network.registerMessage(PacketResponse.HandlerServer.class, PacketResponse.class, 2, Side.SERVER);
		network.registerMessage(PacketResponse.HandlerClient.class, PacketResponse.class, 3, Side.CLIENT);
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent e) {
		ServerCommandManager ss = (ServerCommandManager) MinecraftServer.getServer().getCommandManager();
		ss.registerCommand(new CommandXRT());
	}

	public static EntityPlayerMP getPlayerFromNick(String nickname) {
		ArrayList<EntityPlayerMP> list = (ArrayList) MinecraftServer.getServer().getEntityWorld().playerEntities;
		for (EntityPlayerMP pl : list) {
			EntityPlayerMP player = pl;
			String username = player.getDisplayName();
			if (username.equals(nickname)) {
				return player;
			}
		}
		throw new PlayerNotFoundException("Player with this nick is not online.", new Object[0]);
	}

}
