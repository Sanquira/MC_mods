package edenweapon;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = EdenWeapon.MODID, name = EdenWeapon.MODNAME, version = EdenWeapon.VERSION)
public class EdenWeapon {
	public static final String MODID = "edenweapon";
	public static final String MODNAME = "Eden Weapon";
	public static final String VERSION = "1.0";

	public static final int LIGHTSIDE = 1;
	public static final int DARKSIDE = 2;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		Item darkSword = new ItemDarkSword().setUnlocalizedName("itemDoubleSaber").setTextureName(getTexture("itemDoubleSaber"));
		Item lightShovel = new ItemLightShovel().setUnlocalizedName("itemLightShovel");
		Item lightSpell = new ItemLightSpell().setUnlocalizedName("itemLightSpell").setTextureName(getTexture("itemLightSpell"));
		Item resetItem = new ItemResetSide().setUnlocalizedName("itemResetSide");
		GameRegistry.registerItem(resetItem, resetItem.getUnlocalizedName());
		GameRegistry.registerItem(lightSpell, lightSpell.getUnlocalizedName());
		GameRegistry.registerItem(lightShovel, lightShovel.getUnlocalizedName());
		GameRegistry.registerItem(darkSword, darkSword.getUnlocalizedName());
		MinecraftForge.EVENT_BUS.register(new EdenWeaponEventHandler());
	}

	public static String getTexture(String name) {
		return EdenWeapon.MODID + ":" + name;
	}
}
