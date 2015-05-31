package BPHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class Handlers {

	// Message obsahuje:
	// oznaceni
	// odesilatele
	// prijemce
	// parametry...

	public static class HandlerPacketServer implements IMessageHandler<PacketString, IMessage> {
		@Override
		public IMessage onMessage(PacketString message, MessageContext ctx) {
			if (message.getHeader().contains("sendLoud")) {
				IChatComponent ichatcomponent = new ChatComponentText(message.getSender() + ": " + BPHelper.Config.strMessageGuiltyLoud);
				MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ichatcomponent);
				return null;
			}
			if (message.getHeader().contains("returnXRTRes")) {
				String chatMess = "[XRT] " + message.getSender() + ": " + (message.getStrings().get(0).contains("1") ?
						(BPHelper.Config.strMessageGuilty + " catched on " + message.getStrings().get(1))
						: BPHelper.Config.strMessageInnocent);
				System.out.println(chatMess);
				if (message.getReciever().contains("Server")) {
					return null;
				}
				BPHelper.network.sendTo(new PacketString("responseTesting", message.getSender(), message.getReciever(), chatMess),
						BPHelper.getPlayerFromNick(message.getSender()));
				return null;
			}
			if (message.getHeader().contains("diffClient")) {
				ModsDifferenceTest.handleModDiff(message);
				return null;
			}

			System.err.println("Unknown header: " + message.getHeader());
			return null;
		}

	}

	public static class HandlerPacketClient implements IMessageHandler<PacketString, IMessage> {

		@Override
		public IMessage onMessage(PacketString message, MessageContext ctx) {
			if (message.getHeader().contains("requestTesting")) {
				if (message.getStrings().contains("diff")) {
					BPHelper.network.sendToServer(
							new PacketString("diffClient", Minecraft.getMinecraft().thePlayer.getDisplayName(), message.getStrings().get(0),
									ModsDifferenceTest.getActiveMods()));
				}
				if (message.getStrings().contains("img") && !message.getStrings().get(0).contains("Server")) {
					ImageHandler.sendScreen(message);
				}
				ResourcePackTest.testResourcePack(message);
				return null;
			}
			if (message.getHeader().contains("responseTesting")) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message.getStrings().get(0)));
				return null;
			}
			if (message.getHeader().contains("configSynch")) {
				BPHelper.Config.blocksForTest = message.getStrings();
				return null;
			}
			if (message.getHeader().contains("responseDiff")) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message.getStrings().get(0)));
				return null;
			}

			System.err.println("Unknown header: " + message.getHeader());
			return null;
		}
	}

	public static class HandlerPacketImageServer implements IMessageHandler<PacketImage, IMessage> {

		@Override
		public IMessage onMessage(PacketImage message, MessageContext ctx) {
			BPHelper.network.sendTo(message, BPHelper.getPlayerFromNick(message.getReciever()));
			return null;
		}

	}

	public static class HandlerPacketImageClient implements IMessageHandler<PacketImage, IMessage> {

		@Override
		public IMessage onMessage(PacketImage message, MessageContext ctx) {
			System.out.println(message.toString());
			ImageHandler.reconstructImage(message.getTotal(), message.getCount(), message.getImage(), message.getSender());
			return null;
		}

	}

}
