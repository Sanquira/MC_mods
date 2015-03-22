package edenweapon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public class ItemLightSpell extends ItemSoulbound {

	public ItemLightSpell() {
		setCreativeTab(CreativeTabs.tabCombat);
		setMaxStackSize(1);
		setNoRepair();

	}

	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.bow;
	}

}
