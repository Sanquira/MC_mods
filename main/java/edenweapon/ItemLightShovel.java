package edenweapon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLightShovel extends ItemSoulbound {

	public ItemLightShovel() {
		super(EdenWeapon.LIGHTSIDE);
		setCreativeTab(CreativeTabs.tabTools);
		setMaxStackSize(1);
	}

}
