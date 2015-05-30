package BPHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
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
		public static final String VERSION = "1.7.10-1.1";
		public static final String CHANNEL = "bphelper";
	}

	public static SimpleNetworkWrapper network;

	// for (ModContainer str : Loader.instance().getActiveModList()) {
	// System.out.println(str.getName());
	// }

	public static class Config {
		public static String strMessageInnocent = "Im not your hacker... for now";
		public static String strMessageGuilty = "Im hacker!!!";
		public static String strMessageGuiltyLoud = "Im HACKER! Im using X-Ray Resource Pack! Please BAN me!!!";
		public static ArrayList<String> blocksForTest = new ArrayList<String>();
	}

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		Config.blocksForTest.add("stone.png");
		if (event.getSide().isServer()) {
			File config = event.getSuggestedConfigurationFile();
			createOrLoadConfig(config);
		}
		network = NetworkRegistry.INSTANCE.newSimpleChannel(Info.CHANNEL);
		network.registerMessage(Handlers.HandlerTestPacket.class, PacketString.class, 0, Side.CLIENT);
		// network.registerMessage(PacketTest.Handler.class, PacketTest.class, 0, Side.SERVER);
		// network.registerMessage(PacketTest.Handler.class, PacketTest.class, 1, Side.CLIENT);
		// network.registerMessage(PacketResponse.HandlerServer.class, PacketResponse.class, 2, Side.SERVER);
		// network.registerMessage(PacketResponse.HandlerClient.class, PacketResponse.class, 3, Side.CLIENT);
		// network.registerMessage(PacketString.HandlerClientConfig.class, PacketString.class, 4, Side.CLIENT);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
//		MinecraftForge.EVENT_BUS.register(new EventClass());
	}

	private void createOrLoadConfig(File config) {
		if (config.exists()) {
			tryReadConfig(config);
		} else {
			writeConfig(config);
		}
	}

	private void writeConfig(File config) {
		try {
			FileOutputStream out = new FileOutputStream(config);

			BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
			bf.write("message_guilty=");
			bf.write(Config.strMessageGuilty);
			bf.newLine();
			bf.write("message_guilty_loud=");
			bf.write(Config.strMessageGuiltyLoud);
			bf.newLine();
			bf.write("message_innocent=");
			bf.write(Config.strMessageInnocent);
			bf.newLine();
			bf.write("Name of texture file to test=");
			bf.newLine();

			for (Object blck : Block.blockRegistry.getKeys()) {
				if (((String) blck).contains("minecraft:")) {
					Block blc = (Block) Block.blockRegistry.getObject(blck);
					if (blc.isOpaqueCube()) {
						bf.write(((String) blck).substring(10));
						bf.newLine();
					}
				}
			}
			bf.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void tryReadConfig(File config) {
		try {
			FileInputStream out = new FileInputStream(config);
			BufferedReader bf = new BufferedReader(new InputStreamReader(out, "UTF-8"));

			boolean nextBlocks = false;
			String line;
			while ((line = bf.readLine()) != null) {
				if ("".contains(line) || line.startsWith(";;")) {
					continue;
				}
				System.out.println(line);
				if (line.contains("message_guilty=")) {
					Config.strMessageGuilty = line.substring(line.indexOf("=") + 1);
				}
				if (line.contains("message_guilty_loud=")) {
					Config.strMessageGuiltyLoud = line.substring(line.indexOf("=") + 1);
				}
				if (line.contains("message_innocent=")) {
					Config.strMessageInnocent = line.substring(line.indexOf("=") + 1);
				}
				if (line.contains("Name of texture file to test=")) {
					nextBlocks = true;
					Config.blocksForTest.clear();
					continue;
				}
				if (nextBlocks) {
					Config.blocksForTest.add(line + ".png");
				}
			}
			bf.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
