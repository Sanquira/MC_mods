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

	@Override
	public String toString() {
		return "PacketString [header=" + header + ", sender=" + sender + ", reciever=" + reciever + ", strings=" + strings + "]";
	}

}
