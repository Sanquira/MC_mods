package edenweapon;

import java.util.List;

import scala.reflect.api.Internals.ReificationSupportApi.ImplicitParamsExtractor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemResetSide extends ItemSoulbound {

	public ItemResetSide() {
		super();
		setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity player, int p_77663_4_, boolean p_77663_5_) {
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer p_77624_2_, List par3List, boolean p_77624_4_) {
		super.addInformation(par1ItemStack, p_77624_2_, par3List, p_77624_4_);
		par3List.add("Dont use this item if you have other god binded item in inventory.");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World p_77659_2_, EntityPlayer player) {
		resetSide(player);
		if (p_77659_2_.isRemote) {
			player.addChatMessage(new ChatComponentText("Your side is now clean."));
			player.addChatMessage(new ChatComponentText("Your side is: " + player.getEntityData().getInteger("playerSide")));
		}
		return stack;
	}

	public void resetSide(EntityPlayer player) {
		ExtendedPlayer prop = ExtendedPlayer.get(player);
		prop.setSide(0);
	}

}
