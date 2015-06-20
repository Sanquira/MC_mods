package BPHelper;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ScreenShotHelper;

import org.apache.commons.lang3.ArrayUtils;

public class ImageHandler {

	public static void sendScreen(PacketString message) {
		Minecraft mc = Minecraft.getMinecraft();
		ScreenShotHelper.saveScreenshot(mc.mcDataDir, "xray", mc.displayWidth, mc.displayHeight, mc.getFramebuffer());

		try {
			BufferedImage originalImage = ImageIO.read(new File(new File(mc.mcDataDir, "screenshots"), "xray"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "png", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			System.out.println(imageInByte.length);
			double sizePacket = 31000;
			int numOfPkts = (int) Math.ceil(imageInByte.length / sizePacket);
			int i = 0;
			for (; i < numOfPkts - 1; i++) {
				byte[] buf = Arrays.copyOfRange(imageInByte, (int) (i * sizePacket), (int) ((i + 1) * sizePacket));
				System.out.println(buf.length);
				BPHelper.network.sendToServer(new PacketImage("sendImage", mc.thePlayer.getDisplayName(), message.getStrings().get(0), numOfPkts, i, buf));
			}
			byte[] buf = Arrays.copyOfRange(imageInByte, (int) (i * sizePacket), imageInByte.length);
			BPHelper.network.sendToServer(new PacketImage("sendImage", mc.thePlayer.getDisplayName(), message.getStrings().get(0), numOfPkts, i, buf));
		} catch (IOException e) {
			e.printStackTrace();
		}
		File f = new File(new File(mc.mcDataDir, "screenshots"), "xray");
		f.delete();
	}

	public static void reconstructImage(int totalParts, int numPart, byte[] partOfImage, String sender) {
		BPHelper.particularImages.put(numPart, partOfImage);
		if (BPHelper.particularImages.size() == totalParts) {
			byte[] allImage = {};
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
			for (int i = 0; i < totalParts; i++) {
				allImage = ArrayUtils.addAll(allImage, BPHelper.particularImages.get(i));
			}
			ByteArrayInputStream bais = new ByteArrayInputStream(allImage);
			try {
				BufferedImage img = ImageIO.read(bais);
				ImageIO.write(img, "png", new File(new File(
						Minecraft.getMinecraft().mcDataDir, "screenshots"),
						"XRT_" + sender + "_" + dateFormat.format(new Date()).toString() + ".png"
						));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
