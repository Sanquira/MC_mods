package BPHelper;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketString implements IMessage {

	private ArrayList<String> strings = new ArrayList<String>();
	private String header, sender, reciever;

	public PacketString() {
	}

	public PacketString(String header, String sender, String reciever, String... strings) {
		this.strings = new ArrayList<String>();
		this.strings.add(header);
		this.strings.add(sender);
		this.strings.add(reciever);
		for (String string : strings) {
			this.strings.add(string);
		}
	}

	public PacketString(String header, String sender, String reciever, ArrayList<String> strA) {
		this.strings = new ArrayList<String>();
		this.strings.add(header);
		this.strings.add(sender);
		this.strings.add(reciever);
		this.strings.addAll(strA);
	}

	public PacketString(ArrayList<String> strings) {
		strings = new ArrayList<String>(strings);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int num = buf.readInt();
		for (int i = 0; i < num; i++) {
			strings.add(ByteBufUtils.readUTF8String(buf));
		}
		header = strings.remove(0);
		sender = strings.remove(0);
		reciever = strings.remove(0);

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(strings.size());
		for (String string : strings) {
			ByteBufUtils.writeUTF8String(buf, string);
		}
	}

	public ArrayList<String> getStrings() {
		return strings;
	}

	public String getHeader() {
		return header;
	}

	public String getSender() {
		return sender;
	}

	public String getReciever() {
		return reciever;
	}

	// public static class HandlerClientConfig implements IMessageHandler<PacketString, IMessage> {
	//
	// @Override
	// public IMessage onMessage(PacketString message, MessageContext ctx) {
	// ArrayList<String> strings = message.getStrings();
	// BPHelper.Config.strMessageGuilty = strings.remove(0);
	// BPHelper.Config.strMessageGuiltyLoud = strings.remove(0);
	// BPHelper.Config.strMessageInnocent = strings.remove(0);
	// BPHelper.Config.blocksForTest = strings;
	// return null;
	// }
	//
	// }
	//
	// public static class HandlerServerDiff implements IMessageHandler<PacketString, IMessage> {
	//
	// @Override
	// public IMessage onMessage(PacketString message, MessageContext ctx) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// }
	//
	// public static class HandlerClientDiff implements IMessageHandler<PacketString, IMessage> {
	//
	// @Override
	// public IMessage onMessage(PacketString message, MessageContext ctx) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// }

}
