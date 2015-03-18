package edenweapon;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import com.google.common.collect.Multimap;

public class ItemDarkSword extends ItemSoulbound {

	public ItemDarkSword() {
		super();
		setCreativeTab(CreativeTabs.tabCombat);
		setMaxStackSize(1);
		// Items.diamond_sword
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 12., 0));
		return multimap;
	}

	@Override
	public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
		if (super.hitEntity(p_77644_1_, p_77644_2_, p_77644_3_)) {
			p_77644_2_.addPotionEffect(new PotionEffect(Potion.weakness.id, 10, 1));
			return true;
		}
		return false; 
	}

	@Override
	protected void punishPlayer(EntityPlayer player, ItemStack itemStack) {
		super.punishPlayer(player, itemStack);
		player.attackEntityFrom(DamageSource.magic, 16);
		PotionEffect pot = new PotionEffect(Potion.wither.id, 6000, 1);
		pot.setCurativeItems(new ArrayList<ItemStack>());
		player.addPotionEffect(pot);
	}

}
