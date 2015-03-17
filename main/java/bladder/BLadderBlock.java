package bladder;

import java.util.Random;

import net.minecraft.block.BlockLadder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BLadderBlock extends BlockLadder {

	private Random r = new Random();

	public BLadderBlock() {
		super();
		setHardness(0.7F);
	}

	@SideOnly(Side.CLIENT)
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		if (world.isRemote) {
			if (itemStack.getItem().getUnlocalizedName().contains("ender")) {
				int inCol = 0;
				for (int i = 0; i < world.getHeight(); i++) {
					if (world.getBlock(x, i, z) == this) {
						inCol++;
					}
				}
				if (inCol == 1) {
					entity.playSound(BLadder.soundBLadderEnderPlace.soundName, 1F, 0.5F);
				} else {
					if (world.getBlock(x, y + 1, z) != this && world.getBlock(x, y - 1, z) != this) {
						entity.playSound(BLadder.soundBLadderEnderConnect.soundName, 1F, 0.5F);
					} else {
						entity.playSound(BLadder.soundBLadderEnderPlace.soundName, 1F, 0.5F);
					}
				}
			}
		}
		super.onBlockPlacedBy(world, x, y, z, entity, itemStack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
		if (world.getBlock(x, y, z).getUnlocalizedName().contains("gold")) {
			if (entity.isCollidedHorizontally)
			{
				entity.motionY = 0.5D;
			}
			if (entity.motionY < 0 && !entity.isSneaking()) {
				entity.fallDistance = 0;
				entity.motionY = -0.3D;
				entity.moveEntity(entity.motionX, entity.motionY, entity.motionZ);
			}
		}
		if (world.getBlock(x, y, z).getUnlocalizedName().contains("ender")) {
			for (int i = world.getHeight(); i > y; i--) {
				if (world.getBlock(x, i, z) == this && world.getBlock(x, y + 1, z) != this &&
						world.getBlock(x, i, z).getUnlocalizedName().contains("ender")) {
					entity.setPosition(entity.posX, i + entity.height, entity.posZ);
					entity.playSound(BLadder.soundBLadderEnderCharge.soundName, 1F, 1F);
					entity.performHurtAnimation();
					break;
				}
			}
			return false;
		}
		BLadder.count = BLadder.count + 1;
		if (BLadder.count >= 30 && Math.abs(entity.motionY) >= 0.09) {
			BLadder.count = 0;
			entity.playSound(BLadder.soundBLadderMetal.soundName, 1F, 1F);
		}
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getUnlocalizedName().contains("ender")) {
			return null;
		}
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

}
