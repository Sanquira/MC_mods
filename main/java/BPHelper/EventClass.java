package BPHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventClass {

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			BPHelper.network.sendTo(
					new PacketString("configSynch", "Server", ((EntityPlayerMP) event.entity).getDisplayName(), BPHelper.Config.blocksForTest),
					(EntityPlayerMP) event.entity);
		}
	}

}
