package chatEvent;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameData;

@Mod(modid = ChatEvent.Info.ID, name = ChatEvent.Info.NAME, version = ChatEvent.Info.VERSION)
public class ChatEvent {

	int level = -1;
	boolean makeLevel = false;
	

	public static final class Info {
		public static final String ID = "chatevent";
		public static final String NAME = "ChatEvent";
		public static final String VERSION = "1.7.10-1.0";
		public static final String CHANNEL = "chatevent";
	}

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ChatEvent());
	}

	@SubscribeEvent
	public void handleChat(ClientChatReceivedEvent event) {
		IChatComponent message = event.message;
		if (message.getUnformattedText().contains("%")) {
			String[] splitted = message.getUnformattedText().split(" ");
			if (splitted.length != 3) {
				event.message = new ChatComponentText("usage: %setlevel <next|number|stop>");
				return;
			}
			if (splitted[1].contains("setlevel")) {
				if (splitted[2].contains("stop")) {
					makeLevel = false;
					event.message = new ChatComponentText("Stop leveling");
					return;
				}
				makeLevel = true;
				
				if (splitted[2].contains("next")) {
					level++;
				}
				if (splitted[2].matches("^\\d{6}$")) {
					level = Integer.decode(splitted[1]);
				}
				event.message = new ChatComponentText("level set to: " + level + " Now right click with item.");
				return;
			}
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void handleRightClick(PlayerInteractEvent event) {
		if (event.world.isRemote) {
			if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
				try {
					ItemStack useItem = event.entityPlayer.inventory.getStackInSlot(event.entityPlayer.inventory.currentItem);
					if (useItem != null) {
						String stringItem = GameData.getItemRegistry().getNameForObject(useItem.getItem()) + ":" + useItem.getItemDamage();
						System.out.println(stringItem);
					}
				} catch (NullPointerException e) {
					System.err.println("NullPointerException");
					e.printStackTrace();
				}
			}
		}
	}

}
