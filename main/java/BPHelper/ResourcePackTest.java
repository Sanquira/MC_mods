package BPHelper;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackRepository;

public class ResourcePackTest {

	public static void testResourcePack(PacketTest message) {
		List rpt = Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries();
		navesti: {
			ZipFile zf;
			for (int i = 0; i < rpt.size(); i++) {
				try {
					zf = new ZipFile(Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks().getAbsolutePath() + File.separator
							+ ((ResourcePackRepository.Entry) rpt.get(i)).getResourcePackName());
					Enumeration<? extends ZipEntry> en = zf.entries();
					while (en.hasMoreElements()) {
						ZipEntry ze = en.nextElement();
						if (ze.getName().contains("/stone.png")) {
							if (isAlfa(zf, ze)) {
								isHacker(message);
								break navesti;
							}
						}
					}
					zf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			BPHelper.network.sendToServer(new PacketResponse(message.getMessage(), "Im not your hacker... for now"));
		}
	}

	private static boolean isAlfa(ZipFile zip, ZipEntry file) {
		BufferedInputStream bis;
		byte[] finalByteArray = new byte[(int) file.getSize()];
		try {
			bis = new BufferedInputStream(zip.getInputStream(file));

			int bufferSize = 2048;
			byte[] buffer = new byte[2048];
			int chunkSize = 0;
			int bytesRead = 0;
			try {
				while (true) {
					chunkSize = bis.read(buffer, 0, bufferSize);
					if (chunkSize == -1) {
						break;
					}
					System.arraycopy(buffer, 0, finalByteArray, bytesRead, chunkSize);
					bytesRead += chunkSize;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				bis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage bgTileSprite;
		try {
			bgTileSprite = ImageIO.read(new ByteArrayInputStream(finalByteArray));

			int[] srcbuff = bgTileSprite.getRGB(0, 0, bgTileSprite.getWidth(), bgTileSprite.getHeight(), null, 0, bgTileSprite.getHeight());

			int sum = 0;
			for (int i = 0; i < srcbuff.length; i++) {
				sum += ((srcbuff[i] >> 24) & 0xff) - 255;
			}
			if (sum < 0) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void isHacker(PacketTest message) {
		if (message.isLoud()) {
			System.out.println("Bude load");
			BPHelper.network.sendToServer(new PacketResponse("",
					Minecraft.getMinecraft().thePlayer.getDisplayName() + ": Im HACKER! Im using X-Ray Resource Pack! Please BAN me!!!"));
		}
		BPHelper.network.sendToServer(new PacketResponse(message.getMessage(), "Im hacker!!!"));
	}

}
