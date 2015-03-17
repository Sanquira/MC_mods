package edenweapon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = EdenWeapon.MODID, name = EdenWeapon.MODNAME, version = EdenWeapon.VERSION)
public class EdenWeapon {
	public static final String MODID = "edenweapon";
	public static final String MODNAME = "Eden Weapon";
	public static final String VERSION = "1.0";

	@EventHandler
	public void init(FMLInitializationEvent event) {
		Item plasmaBlaster = new ItemAcientPlasmaBlaster().setTextureName(EdenWeapon.MODID + ":bow").setUnlocalizedName("acientPlasmaBlaster");
		GameRegistry.registerItem(plasmaBlaster, plasmaBlaster.getUnlocalizedName());
		Block test = new BlockStone().setBlockName("testBlock");
		GameRegistry.registerBlock(test, test.getUnlocalizedName());
	}
}
