package edenweapon;

import scala.reflect.api.Internals.ReificationSupportApi.ImplicitParamsExtractor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemResetSide extends ItemSoulbound {

	public ItemResetSide() {
		super();
		setCreativeTab(CreativeTabs.tabTools);
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

}
