package BPHelper;

import java.util.ArrayList;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

public class ModsDifferenceTest {

	public static void handleModDiff(PacketString mes) {
		ArrayList<String> serverMods = getActiveMods();
		ArrayList<String> diffMods = new ArrayList<String>();
		boolean cmisHere = false;
		navesti: for (String cm : mes.getStrings()) {
			for (String sm : serverMods) {
				if (cm.contentEquals(sm)) {
					continue navesti;
				}
			}
			diffMods.add(cm);
		}
		String message = "[XRT] diff mods: " + diffMods.toString();
		System.out.println(message);
		if (mes.getReciever().contains("Server")) {
			return;
		}
		BPHelper.network.sendTo(new PacketString("responseDiff", mes.getSender(), mes.getReciever(), message), BPHelper.getPlayerFromNick(mes.getReciever()));
	}

	public static ArrayList<String> getActiveMods() {
		ArrayList<String> ret = new ArrayList<String>();
		for (ModContainer str : Loader.instance().getActiveModList()) {
			ret.add(str.getModId());
		}
		return ret;
	}

}
