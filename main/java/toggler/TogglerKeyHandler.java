package toggler;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TogglerKeyHandler {

	private Minecraft mc = Minecraft.getMinecraft();
	private boolean pressed = false;

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if (mc.currentScreen != null) {
			return;
		}
		if (this.pressed) {
			return;
		}
		this.pressed = true;
		boolean newState = false;
		KeyBinding toggledKey = null;
		String message = "If you read this, something is wrong.";
		if (Toggler.Keys.toggleLeft.getIsKeyPressed()) {
			toggledKey = mc.gameSettings.keyBindAttack;
			message = Toggler.Keys.toggleLeft.getKeyDescription();
		} else if (Toggler.Keys.toggleRight.getIsKeyPressed()) {
			toggledKey = mc.gameSettings.keyBindUseItem;
			message = Toggler.Keys.toggleRight.getKeyDescription();
		} else if (Toggler.Keys.toggleSneak.getIsKeyPressed()) {
			toggledKey = mc.gameSettings.keyBindSneak;
			message = Toggler.Keys.toggleSneak.getKeyDescription();
		} else if (Toggler.Keys.toggleRun.getIsKeyPressed()) {
			toggledKey = mc.gameSettings.keyBindForward;
			message = Toggler.Keys.toggleRun.getKeyDescription();
		} else if (Toggler.Keys.toggleJump.getIsKeyPressed()) {
			toggledKey = mc.gameSettings.keyBindJump;
			message = Toggler.Keys.toggleJump.getKeyDescription();
		} else if (Toggler.Keys.toggleSprint.getIsKeyPressed()) {
			if (!mc.gameSettings.keyBindForward.getIsKeyPressed()) {
				pressButton(mc.gameSettings.keyBindForward);
			}
			newState = !mc.thePlayer.isSprinting();
			mc.thePlayer.setSprinting(newState);
			message = Toggler.Keys.toggleSprint.getKeyDescription();
		} else if (Toggler.Keys.toggleDuplicate.getIsKeyPressed()) {
			mc.thePlayer.capabilities.allowFlying = true;
			// mc.playerController.setGameType(GameType.CREATIVE);
			// Field t;
			// try {
			// if (Toggler.DEBUG) {
			// t = EntityClientPlayerMP.class.getDeclaredField("invulnerable");
			// } else {
			// t = EntityClientPlayerMP.class.getDeclaredField("field_78774_b");
			// }
			// t.setAccessible(true);
			// t.setBoolean(mc.thePlayer, true);
			// NetHandlerPlayClient obj = (NetHandlerPlayClient)
			// t.get(mc.playerController);
			// obj.addToSendQueue(new C10PacketCreativeInventoryAction(-1,
			// new
			// ItemStack(Items.spawn_egg,32,120)));
			// } catch (NoSuchFieldException e) {
			// e.printStackTrace();
			// for (Field f : PlayerControllerMP.class.getDeclaredFields())
			// {
			// System.out.println(f.getName());
			// }
			// } catch (SecurityException e) {
			// e.printStackTrace();
			// } catch (IllegalArgumentException e) {
			// e.printStackTrace();
			// } catch (IllegalAccessException e) {
			// e.printStackTrace();
			// } catch (NoSuchFieldException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
		if (toggledKey != null) {
			newState = !toggledKey.getIsKeyPressed();
			pressButton(toggledKey, newState);
		}
		if (Toggler.Keys.toggleSprint.getIsKeyPressed()) {
			Toggler.notifyStatus(message);
		} else {
			if (toggledKey != null) {
				Toggler.notifyStatus(message, newState);
			}
		}
		this.pressed = false;
	}

	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		if (Toggler.Config.holdToAttackEnabled && mc.gameSettings.keyBindAttack.getIsKeyPressed()) {
			mc.thePlayer.swingItem();
			if ((mc.objectMouseOver != null) && (mc.objectMouseOver.typeOfHit == MovingObjectType.ENTITY)) {
				if ((Toggler.Config.autoOnlyAttackEnabled)) {
					mc.playerController.attackEntity(mc.thePlayer, mc.objectMouseOver.entityHit);
				}
			}
		}
	}

	private void pressButton(KeyBinding key) {
		pressButton(key, true);
	}

	private void pressButton(KeyBinding key, boolean press) {
		Field t;
		int pt = 1;
		try {
			if (Toggler.DEBUG) {
				t = KeyBinding.class.getDeclaredField("pressed");
				t.setAccessible(true);
				t.setBoolean(key, press);
				t = KeyBinding.class.getDeclaredField("pressTime");
				t.setAccessible(true);
				t.setInt(key, pt);
			} else {
				t = KeyBinding.class.getDeclaredField("field_74513_e");
				t.setAccessible(true);
				t.setBoolean(key, press);
				t = KeyBinding.class.getDeclaredField("field_151474_i");
				t.setAccessible(true);
				t.setInt(key, pt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
