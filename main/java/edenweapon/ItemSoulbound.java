package edenweapon;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public abstract class ItemSoulbound extends Item {

	private int side = 0;

	public ItemSoulbound(int side) {
		super();
		this.side = side;
	}

	public ItemSoulbound() {
		super();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer p_77624_2_, List par3List, boolean p_77624_4_) {
		super.addInformation(par1ItemStack, p_77624_2_, par3List, p_77624_4_);
		if (par1ItemStack.stackTagCompound == null) {
			return;
		}
		if (!par1ItemStack.stackTagCompound.getString("bindedToPlayer").isEmpty()) {
			par3List.add(EnumChatFormatting.DARK_PURPLE + "Soulbound to " + par1ItemStack.stackTagCompound.getString("bindedToPlayer"));
		}
		// if (!isCorrectSideWithCheck(p_77624_2_)) {
		// par3List.add(EnumChatFormatting.RED + "This item is from oposite god. If you use it, you will be punished!!");
		// }
	}

	@Override
	public void onCreated(ItemStack par1ItemStack, World p_77622_2_, EntityPlayer par3EntityPlayer) {
		super.onCreated(par1ItemStack, p_77622_2_, par3EntityPlayer);
		testTagCompoundExist(par3EntityPlayer, par1ItemStack);
		// Side side = FMLCommonHandler.instance().getEffectiveSide()
		// isCorrectSideWithCheck(par3EntityPlayer);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!isCorrectSideWithCheck((EntityPlayer) player) && !world.isRemote) {
			killHereticPlayer((EntityPlayer) player);
		}
		if (testSoulbound(player, stack)) {
			punishPlayer(player, stack);
			return stack;
		}
		return super.onItemRightClick(stack, world, player);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
		// if (!isCorrectSideWithCheck((EntityPlayer) player)) {
		// killHereticPlayer((EntityPlayer) player);
		// }
		if (testSoulbound(player, itemstack)) {
			punishPlayer(player, itemstack);
			return true;
		}
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
		// if (!isCorrectSideWithCheck((EntityPlayer) p_77644_3_)) {
		// killHereticPlayer((EntityPlayer) p_77644_3_);
		// }
		if (testSoulbound((EntityPlayer) p_77644_3_, p_77644_1_)) {
			punishPlayer((EntityPlayer) p_77644_3_, p_77644_1_);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity player, int p_77663_4_, boolean p_77663_5_) {
		if (player instanceof EntityPlayer) {
			if (!((EntityPlayer) player).capabilities.isCreativeMode) {
				testSoulbound((EntityPlayer) player, itemStack);
				if (!world.isRemote) {
					isCorrectSideWithCheck((EntityPlayer) player);
				}
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

	public void resetSide(EntityPlayer player) {
		ExtendedPlayer prop = ExtendedPlayer.get(player);
		prop.setSide(0);
	}

	/**
	 * Check if player is on correct side to equip this item. If does not have defined side, write him item's side.
	 * 
	 * @param player
	 * @return true if is on correct side or not yet defined else false
	 */
	protected boolean isCorrectSideWithCheck(EntityPlayer player) {
		ExtendedPlayer prop = ExtendedPlayer.get(player);
		int sideres = prop.getSide();
		System.out.println(sideres);
		if (sideres == 0) {
			prop.setSide(side);
			return true;
		} else if (side != sideres) {
			return false;
		}
		return true;
	}

	protected void killHereticPlayer(EntityPlayer player) {
		// player.inventory.clearInventory(null, -1);
		// player.experienceTotal = 0;
		// player.attackEntityFrom(DamageSource.magic, 1000);
		player.performHurtAnimation();
		player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "You are heretic!!!"));
	}
}
