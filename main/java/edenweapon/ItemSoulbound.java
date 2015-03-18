package edenweapon;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public abstract class ItemSoulbound extends Item {

	public ItemSoulbound() {
		super();
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer p_77624_2_, List par3List, boolean p_77624_4_) {
		super.addInformation(par1ItemStack, p_77624_2_, par3List, p_77624_4_);
		if (par1ItemStack.stackTagCompound == null) {
			return;
		}
		if (!par1ItemStack.stackTagCompound.getString("bindedToPlayer").isEmpty())
		{
			par3List.add(EnumChatFormatting.DARK_PURPLE + "Soulbound to " + par1ItemStack.stackTagCompound.getString("bindedToPlayer"));
		}
	}

	@Override
	public void onCreated(ItemStack par1ItemStack, World p_77622_2_, EntityPlayer par3EntityPlayer) {
		super.onCreated(par1ItemStack, p_77622_2_, par3EntityPlayer);
		testTagCompoundExist(par3EntityPlayer, par1ItemStack);
	}

//	@Override
//	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
//		if (testSoulbound(player, stack)) {
//			punishPlayer(player, stack);
//			return true;
//		}
//		return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
//	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World p_77659_2_, EntityPlayer player) {
		if (testSoulbound(player, stack)) {
			punishPlayer(player, stack);
			return stack;
		}
		return super.onItemRightClick(stack, p_77659_2_, player);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
		if (testSoulbound(player, itemstack)) {
			punishPlayer(player, itemstack);
			return true;
		}
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
		if (testSoulbound((EntityPlayer) p_77644_3_, p_77644_1_)) {
			punishPlayer((EntityPlayer) p_77644_3_, p_77644_1_);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onUpdate(ItemStack itemStack, World p_77663_2_, Entity player, int p_77663_4_, boolean p_77663_5_) {
		if (player instanceof EntityPlayer) {
			if (!((EntityPlayer) player).capabilities.isCreativeMode) {
				testSoulbound((EntityPlayer) player, itemStack);
			}
		}
	}

	protected void testTagCompoundExist(EntityPlayer player, ItemStack itemStack) {
		if (!itemStack.hasTagCompound()) {
			NBTTagCompound nbtc = new NBTTagCompound();
			itemStack.setTagCompound(nbtc);
			itemStack.stackTagCompound.setString("bindedToPlayer", player.getDisplayName());
		}
		if (itemStack.stackTagCompound.getString("bindedToPlayer").isEmpty()) {
			itemStack.stackTagCompound.setString("bindedToPlayer", player.getDisplayName());
		}

	}

	protected boolean testSoulbound(EntityPlayer player, ItemStack itemStack) {
		if (itemStack.stackTagCompound == null) {
			testTagCompoundExist(player, itemStack);
		}
		if (!itemStack.stackTagCompound.getString("bindedToPlayer").equals(player.getDisplayName())) {
			return true;
		}
		return false;
	}

	protected void punishPlayer(EntityPlayer player, ItemStack itemStack) {
		player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "You are not allowed to use this item."));
	}
}
