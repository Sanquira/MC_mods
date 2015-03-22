//package toggler;
//
//import net.minecraft.client.Minecraft;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//
//@SideOnly(Side.CLIENT)
//public class TogglerTickHandler // implements ITickHandler
//{
//	// private final EnumSet<TickType> ticksToGet;
//	 private static Minecraft mc = Minecraft.getMinecraft();
//	// static boolean toggleLeftActive = false;
//	// static boolean onlyAttackActive = false;
//	// static boolean toggleRunActive = false;
//	// static boolean toggleRunPressed = false;
//	//
//	// public TogglerTickHandler(EnumSet<TickType> ticksToGet)
//	// {
//	// this.ticksToGet = ticksToGet;
//	// }
//	//
//	// public void tickStart(EnumSet<TickType> type, Object... tickData) {}
//	//
//	// public void tickEnd(EnumSet<TickType> type, Object... tickData) {}
//	//
//	// public EnumSet<TickType> ticks()
//	// {
//	// return this.ticksToGet;
//	// }
//	//
//	// public String getLabel()
//	// {
//	// return "TogglerTickHandler";
//	// }
//	//
//	// public static void togglerTick()
//	// {
//	// if (mc.currentScreen != null)
//	// {
//	// toggleRunActive = false;
//	// toggleRunPressed = false;
//	// toggleLeftActive = false;
//	// onlyAttackActive = false;
//	// mc.gameSettings.keyBindForward.pressed = false;
//	// return;
//	// }
//	// if (toggleRunActive)
//	// {
//	// if ((toggleRunPressed) && (!mc.gameSettings.keyBindForward.pressed))
//	// {
//	// toggleRunPressed = false;
//	// mc.gameSettings.keyBindForward.pressed = true;
//	// }
//	// if ((!toggleRunPressed) &&
//	// (GameSettings.isKeyDown(mc.gameSettings.keyBindForward)))
//	// {
//	// toggleRunActive = false;
//	// toggleRunPressed = false;
//	// Toggler.notifyStatus(Toggler.Keys.toggleRun.keyDescription, false);
//	// }
//	// if (mc.gameSettings.keyBindBack.pressed)
//	// {
//	// toggleRunActive = false;
//	// toggleRunPressed = false;
//	// mc.gameSettings.keyBindForward.pressed = false;
//	// Toggler.notifyStatus(Toggler.Keys.toggleRun.keyDescription, false);
//	// }
//	// }
//	// if ((GameSettings.isKeyDown(mc.gameSettings.keyBindAttack)) &&
//	// (toggleLeftActive))
//	// {
//	// toggleLeftActive = false;
//	// onlyAttackActive = false;
//	// Toggler.notifyStatus(Toggler.Keys.toggleLeft.keyDescription, false);
//	// }
//	// if ((GameSettings.isKeyDown(mc.gameSettings.keyBindAttack)) ||
//	// (mc.gameSettings.keyBindAttack.pressed) || (onlyAttackActive)) {
//	// attackSwing();
//	// }
//	// if ((onlyAttackActive) &&
//	// (!GameSettings.isKeyDown(mc.gameSettings.keyBindAttack)) &&
//	// (!toggleLeftActive)) {
//	// onlyAttackActive = false;
//	// }
//	// }
//	//
//	static void attackSwing() {
//		if (Toggler.Config.holdToAttackEnabled) {
//			mc.thePlayer.swingItem();
//			if ((mc.objectMouseOver != null) && (mc.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY)) {
//				if ((Toggler.Config.autoOnlyAttackEnabled) && (mc.gameSettings.keyBindAttack.pressed)) {
//					mc.gameSettings.keyBindAttack.pressed = false;
//					onlyAttackActive = true;
//				}
//				mc.playerController.attackEntity(mc.thePlayer, mc.objectMouseOver.entityHit);
//			}
//		}
//	}
//}
////
////
