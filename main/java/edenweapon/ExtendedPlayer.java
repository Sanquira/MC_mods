package edenweapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import cpw.mods.fml.common.FMLCommonHandler;

public class ExtendedPlayer implements IExtendedEntityProperties {

	public final static String EXT_PROP_NAME = EdenWeapon.MODID + "ExtendedPlayer";

	private final EntityPlayer player;
	private int side;

	public ExtendedPlayer(EntityPlayer player) {
		this.player = player;
	}

	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(ExtendedPlayer.EXT_PROP_NAME, new ExtendedPlayer(player));
	}

	public static final ExtendedPlayer get(EntityPlayer player) {
		return (ExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();

		properties.setInteger("playerSide", side);

		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		this.side = properties.getInteger("playerSide");
	}

	public void setSide(int side) {
		this.side = side;
		sync();
	}

	public int getSide() {
		return side;
	}

	public boolean isCorrectSide(int side) {
		return side == this.side;
	}

	public final void sync()
	{
		// We only want to send from the server to the client
		if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
			EntityPlayerMP player1 = (EntityPlayerMP) player;
			EdenWeapon.network.sendTo(new PacketSyncPlayerProps(side), player1);
		}
	}

	@Override
	public void init(Entity entity, World world) {
	}

}
