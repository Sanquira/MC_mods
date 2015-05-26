package edenweapon;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncPlayerProps implements IMessage {

	private int side;

	public PacketSyncPlayerProps() {
	}

	public PacketSyncPlayerProps(int side) {
		this.side = side;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		side = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(side);
	}

	@Override
	public String toString() {
		return "packet handle this side: " + side;
	}

	public int getSide() {
		return side;
	}

	public static class HandlerClient implements IMessageHandler<PacketSyncPlayerProps, IMessage> {

		@Override
		public IMessage onMessage(PacketSyncPlayerProps message, MessageContext ctx) {
			ExtendedPlayer.get(Minecraft.getMinecraft().thePlayer).setSide(message.getSide());
			return null;
		}

	}

}
