package edenweapon;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemLightSpell extends ItemSoulbound {

	public ItemLightSpell() {
		super(EdenWeapon.LIGHTSIDE);
		setCreativeTab(CreativeTabs.tabCombat);
		setMaxStackSize(1);
		setNoRepair();

	}

	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.bow;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World world, EntityPlayer player, int p_77615_4_) {
		double rad = 10;
		ArrayList<Entity> ents = (ArrayList<Entity>) world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(player.posX - rad, player.posY - rad, player.posZ - rad, player.posX + rad, player.posY + rad, player.posZ + rad));
		for (Entity entity : ents) {
			entity.motionY = 1;
		}
	}

	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
		return p_77654_1_;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return event.result;
		}

		p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));

		return p_77659_1_;
	}

}
