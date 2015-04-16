package edenweapon;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class EdenWeaponEventHandler {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		/*
		 * Be sure to check if the entity being constructed is the correct type
		 * for the extended properties you're about to add! The null check may
		 * not be necessary - I only use it to make sure properties are only
		 * registered once per entity
		 */
		if (event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.entity) == null) {
			// This is how extended properties are registered using our
			// convenient method from earlier
			ExtendedPlayer.register((EntityPlayer) event.entity);
			// That will call the constructor as well as cause the init() method
			// to be called automatically
		}
	}

}
