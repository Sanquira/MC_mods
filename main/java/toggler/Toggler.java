package toggler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Toggler.Info.ID, version = Toggler.Info.VERSION)
public class Toggler {
	public static final boolean DEBUG = false;
	@Mod.Instance("Toggler")
	public static Toggler instance;

	public static final class Info {
		public static final String ID = "Toggler";
		public static final String NAME = "Toggler";
		public static final String VERSION = "1.7.10-1.0";
		public static final String CHANNEL = "Toggler";
	}

	static class Config {
		static boolean toggleLeftEnabled = true;
		static boolean toggleRightEnabled = true;
		static boolean toggleSneakEnabled = true;
		static boolean toggleRunEnabled = true;
		static boolean toggleSprintEnabled = true;
		static boolean toggleJumpEnabled = true;
		static boolean holdToAttackEnabled = true;
		static boolean autoOnlyAttackEnabled = true;
		static boolean notifyStatus = true;
	}

	protected static class Keys {
		protected static KeyBinding toggleLeft = new KeyBinding("Toggle Left Click", Keyboard.KEY_G, Toggler.Info.ID);
		protected static KeyBinding toggleRight = new KeyBinding("Toggle Right Click", Keyboard.KEY_H, Toggler.Info.ID);
		protected static KeyBinding toggleSneak = new KeyBinding("Toggle Sneak", Keyboard.KEY_J, Toggler.Info.ID);
		protected static KeyBinding toggleRun = new KeyBinding("Toggle Run", Keyboard.KEY_K, Toggler.Info.ID);
		protected static KeyBinding toggleSprint = new KeyBinding("Toggle Sprint", Keyboard.KEY_L, Toggler.Info.ID);
		protected static KeyBinding toggleJump = new KeyBinding("Toggle Jump", Keyboard.KEY_F, Toggler.Info.ID);
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		Config.toggleLeftEnabled = config.get("general", "ToggleLeft", true).getBoolean(false);
		Config.toggleRightEnabled = config.get("general", "ToggleRight", true).getBoolean(false);
		Config.toggleSneakEnabled = config.get("general", "ToggleSneak", true).getBoolean(false);
		Config.toggleRunEnabled = config.get("general", "ToggleRun", true).getBoolean(false);
		Config.toggleSprintEnabled = config.get("general", "ToggleSprint", true).getBoolean(false);
		Config.toggleJumpEnabled = config.get("general", "ToggleJump", true).getBoolean(false);
		Config.holdToAttackEnabled = config.get("general", "HoldToAttack", true).getBoolean(false);
		Config.notifyStatus = config.get("general", "NotifyStatus", true).getBoolean(false);
		config.save();
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		boolean[] noRepeatingKeys = { false };
		if (Config.toggleLeftEnabled) {
			ClientRegistry.registerKeyBinding(Keys.toggleLeft);
		}
		if (Config.toggleRightEnabled) {
			ClientRegistry.registerKeyBinding(Keys.toggleRight);
		}
		if (Config.toggleSneakEnabled) {
			ClientRegistry.registerKeyBinding(Keys.toggleSneak);
		}
		if (Config.toggleRunEnabled) {
			ClientRegistry.registerKeyBinding(Keys.toggleRun);
		}
		if (Config.toggleSprintEnabled) {
			ClientRegistry.registerKeyBinding(Keys.toggleSprint);
		}
		if (Config.toggleJumpEnabled) {
			ClientRegistry.registerKeyBinding(Keys.toggleJump);
		}
		// ClientRegistry.registerTickHandler(new
		// TogglerTickHandler(EnumSet.of(TickType.CLIENT)), Side.CLIENT);
		FMLCommonHandler.instance().bus().register(new TogglerKeyHandler());
	}

	static void notifyStatus(String message) {
		if (!Config.notifyStatus) {
			return;
		}
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "[" + EnumChatFormatting.WHITE + message + EnumChatFormatting.YELLOW + "]"));
	}

	static void notifyStatus(String toggled, boolean onOff) {
		String onOffText = EnumChatFormatting.RED + "Off";
		if (onOff) {
			onOffText = EnumChatFormatting.GREEN + "On";
		}
		notifyStatus(toggled + EnumChatFormatting.YELLOW + ":" + onOffText);
	}
}
