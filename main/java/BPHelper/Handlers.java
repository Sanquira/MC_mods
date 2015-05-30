package BPHelper;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class Handlers {

	public static class HandlerTestPacket implements IMessageHandler<PacketString, IMessage> {

		@Override
		public IMessage onMessage(PacketString message, MessageContext ctx) {
			// Message obsahuje:
			// odesilatele
			// prijemce
			// parametry...
			ResourcePackTest.testResourcePack(message);
			return null;
		}
	}

}
