package BPHelper;

import io.netty.buffer.ByteBuf;

import java.util.Arrays;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketImage implements IMessage {

	private String header, sender, reciever;
	private byte[] image;
	private int total, count;

	public PacketImage() {
	}

	public PacketImage(String header, String sender, String reciever, int total, int count, byte[] image) {
		super();
		this.header = header;
		this.sender = sender;
		this.reciever = reciever;
		this.total = total;
		this.count = count;
		this.image = image;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		header = ByteBufUtils.readUTF8String(buf);
		sender = ByteBufUtils.readUTF8String(buf);
		reciever = ByteBufUtils.readUTF8String(buf);
		total = buf.readInt();
		count = buf.readInt();
		image = new byte[buf.array().length - buf.readerIndex() - 1];
		buf.readBytes(image);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, header);
		ByteBufUtils.writeUTF8String(buf, sender);
		ByteBufUtils.writeUTF8String(buf, reciever);
		buf.writeInt(total);
		buf.writeInt(count);
		buf.writeBytes(image);
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

	public byte[] getImage() {
		return image;
	}

	public int getTotal() {
		return total;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "PacketImage [header=" + header + ", sender=" + sender + ", reciever=" + reciever + ", total=" + total + ", count=" + count + ", image=" + image.length + "]";
	}

	public String toStringBig() {
		return "PacketImage [header=" + header + ", sender=" + sender + ", reciever=" + reciever + ", total=" + total + ", count=" + count + ", image=" + Arrays.toString(image) + "]";
	}

}
