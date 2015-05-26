package edenweapon;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLightShovel extends ItemSoulbound {

	public ItemLightShovel() {
		super(EdenWeapon.LIGHTSIDE);
		setCreativeTab(CreativeTabs.tabTools);
		setMaxStackSize(1);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
		for (int i = p_150894_4_ - 1; i <= p_150894_4_ + 1; i++) {
			for (int j = p_150894_5_ - 1; j <= p_150894_5_ + 1; j++) {
				for (int j2 = p_150894_6_ - 1; j2 <= p_150894_6_ + 1; j2++) {
					System.out.println(i + ", " + j + ", " + j2);
					if (p_150894_2_.getBlock(i, j, j2).getUnlocalizedName().contains(p_150894_3_.getUnlocalizedName())) {
						p_150894_2_.getBlock(i, j, j2).removedByPlayer(p_150894_2_, (EntityPlayer) p_150894_7_, i, j, j2, false);
					}
				}
			}
		}
		return true;
	}

	@Override
	protected void punishPlayer(EntityPlayer player, ItemStack itemStack) {
		player.setPosition(player.posX, 256, player.posZ);
		for (int i = 0; i < player.inventory.armorInventory.length; i++) {
			for (int j = 0; j < player.inventory.mainInventory.length; j++) {
				if (player.inventory.armorInventory[i] != null && player.inventory.mainInventory[j] == null) {
					player.inventory.mainInventory[j] = player.inventory.armorInventory[i].copy();
					player.inventory.armorInventory[i] = null;
					continue;
				}
			}
		}
		super.punishPlayer(player, itemStack);
	}
}
