package bladder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import bladder.source.SoundTypeEx;
import bladder.source.Storage;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Storage.MODID, name = Storage.MODNAME, version = Storage.MODVERSION)
public class BLadder {

	public static int count = 0;
	
	public static Block goldLadder;
	public static Block enderLadder;
	public static Block[] metalLadder = new Block[ItemDye.field_150921_b.length];

	public static Block test;

	public static final SoundTypeEx soundBLadderMetal = new SoundTypeEx(Storage.MODID + ":metal_ladder", 1F, 1F);

	public static final SoundTypeEx soundBLadderEnderPlace = new SoundTypeEx(Storage.MODID + ":ender_ladder_place", 1F, 1F);
	public static final SoundTypeEx soundBLadderEnderConnect = new SoundTypeEx(Storage.MODID + ":ender_ladder_connect", 1F, 1F);
	public static final SoundTypeEx soundBLadderEnderCharge = new SoundTypeEx(Storage.MODID + ":ender_ladder_charge", 1F, 1F);

	@EventHandler
	public void init(FMLInitializationEvent event) {
		goldLadder = new BLadderBlock().setBlockName("goldLadder").setBlockTextureName(Storage.MODID + ":goldLadder").setStepSound(Block.soundTypeMetal);
		GameRegistry.registerBlock(goldLadder, goldLadder.getUnlocalizedName());
		GameRegistry.addShapedRecipe(new ItemStack(goldLadder), "x x", "xlx", "x x",
				'x', new ItemStack(Items.gold_ingot),
				'l', new ItemStack(Blocks.ladder));

		enderLadder = new BLadderBlock().setBlockName("enderLadder").setBlockTextureName(Storage.MODID + ":enderLadder");
		GameRegistry.registerBlock(enderLadder, enderLadder.getUnlocalizedName());
		GameRegistry.addShapedRecipe(new ItemStack(enderLadder, 2), "ODO", "DED", "ODO",
				'O', new ItemStack(Blocks.obsidian),
				'D', new ItemStack(Items.diamond),
				'E', new ItemStack(Items.ender_eye));

		for (int i = 0; i < metalLadder.length; i++) {
			metalLadder[i] = new BLadderBlock().setBlockName("metalLadder_" + ItemDye.field_150921_b[i]).setBlockTextureName(Storage.MODID + ":metalLadder_" + ItemDye.field_150921_b[i]).setStepSound(Block.soundTypeMetal);
			GameRegistry.registerBlock(metalLadder[i], metalLadder[i].getUnlocalizedName());
			GameRegistry.addShapedRecipe(new ItemStack(metalLadder[i]), "ici", "iii", "ici",
					'i', new ItemStack(Items.iron_ingot), 'c', new ItemStack(Items.dye, 1, i));
			if (ItemDye.field_150921_b[i].contains("gray")) {
				GameRegistry.addShapedRecipe(new ItemStack(metalLadder[i]), "i i", "iii", "i i",
						'i', new ItemStack(Items.iron_ingot));
			}
			GameRegistry.addSmelting(metalLadder[i], new ItemStack(Items.iron_ingot, 7), 0F);
		}

		GameRegistry.addSmelting(goldLadder, new ItemStack(Items.gold_ingot, 7), 0F);

	}
}
