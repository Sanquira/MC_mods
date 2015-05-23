package BPHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketResponse implements IMessage {

	private String mes;
	private String adr;

	public PacketResponse() {
	}

	public PacketResponse(String adresat, String message) {
		this.mes = message;
		this.adr = adresat;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		mes = ByteBufUtils.readUTF8String(buf);
		adr = ByteBufUtils.readUTF8String(buf);

	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, mes);
		ByteBufUtils.writeUTF8String(buf, adr);
	}

	public String getMessage() {
		return mes;
	}

	public String getAdresat() {
		return adr;
	}

	public static class HandlerServer implements IMessageHandler<PacketResponse, IMessage> {

		@Override
		public IMessage onMessage(PacketResponse message, MessageContext ctx) {
			if ("".contains(message.getAdresat())) {
				IChatComponent ichatcomponent = new ChatComponentText(message.mes);
				MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ichatcomponent);
				return null;
			}
			BPHelper.network.sendTo(new PacketResponse(ctx.getServerHandler().playerEntity.getCommandSenderName(), message.getMessage()),
					BPHelper.getPlayerFromNick(message.getAdresat()));
			return null;
		}
	}

	public static class HandlerClient implements IMessageHandler<PacketResponse, IMessage> {

		@Override
		public IMessage onMessage(PacketResponse message, MessageContext ctx) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Message from " + message.adr + ": " + message.mes));
			return null;
		}
	}

}
