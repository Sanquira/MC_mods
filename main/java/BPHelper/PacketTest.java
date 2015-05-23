package BPHelper;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTest implements IMessage {

	private String mes;
	private boolean loud;

	public PacketTest() {
	}

	public PacketTest(String message) {
		this(message, false);
	}

	public PacketTest(String message, boolean loud) {
		this.mes = message;
		this.loud = loud;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		loud = buf.readBoolean();
		mes = ByteBufUtils.readUTF8String(buf);

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(loud);
		ByteBufUtils.writeUTF8String(buf, mes);
	}

	public String getMessage() {
		return mes;
	}

	public boolean isLoud() {
		return loud;
	}

	@Override
	public String toString() {
		return "PacketTest [mes=" + mes + ", loud=" + loud + "]";
	}

	public static class Handler implements IMessageHandler<PacketTest, IMessage> {

		@Override
		public IMessage onMessage(PacketTest message, MessageContext ctx) {
			ResourcePackTest.testResourcePack(message);
			return null;
		}
	}

}
