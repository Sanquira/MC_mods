package edenweapon;

import java.awt.TexturePaint;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
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
		double maxStr = 2.5;

		int j = this.getMaxItemUseDuration(p_77615_1_) - p_77615_4_;
		float f = (float) j / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if ((double) f < 0.1D)
		{
			return;
		}

		if (f > 1.0F)
		{
			f = 1.0F;
		}

		ArrayList<Entity> ents = (ArrayList<Entity>) world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(player.posX - rad, player.posY - rad, player.posZ - rad, player.posX + rad, player.posY + rad, player.posZ + rad));
		for (Entity entity : ents) {
			double vecx = entity.posX - player.posX;
			double vecz = entity.posZ - player.posZ;
			double eucl = Math.sqrt(vecx * vecx - vecz * vecz);
			if (eucl > rad) {
				continue;
			}
			vecx = vecx / eucl;
			vecz = vecz / eucl;
			entity.motionX = vecx * f;
			entity.motionZ = vecz * f;
			entity.motionY = gaussStrength(maxStr * f, rad / 2, eucl);
		}
	}

	private double gaussStrength(double maxStr, double radi, double dist) {
		return maxStr * Math.exp(-((dist * dist) / (2 * radi * radi)));
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
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */

	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		super.onItemRightClick(p_77659_1_, p_77659_2_, p_77659_3_);
		ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return event.result;
		}

		p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));

		return p_77659_1_;
	}

}
