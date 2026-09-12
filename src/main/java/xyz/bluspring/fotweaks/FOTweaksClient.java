package xyz.bluspring.fotweaks;

import com.mojang.blaze3d.platform.InputConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import xyz.bluspring.fotweaks.network.SetElytraFlightStatusPacket;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

@Mod(value = FOTweaks.MODID, dist = Dist.CLIENT)
public class FOTweaksClient {
    public static final KeyMapping TOGGLE_FLIGHT_KEY = new KeyMapping("key.fotweaks.toggle_elytra_flight", InputConstants.KEY_NUMPADENTER, "category.fotweaks.apothic_attributes");

    public FOTweaksClient(IEventBus bus) {
        bus.addListener((RegisterKeyMappingsEvent event) -> {
            if (ModList.get().isLoaded("apothic_attributes"))
                event.register(TOGGLE_FLIGHT_KEY);
        });

        NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post event) -> {
            Minecraft mc = Minecraft.getInstance();

            if (TOGGLE_FLIGHT_KEY.consumeClick() && mc.player != null) {
                boolean original = mc.player.getData(FOTweaks.ELYTRA_FLIGHT_STATUS);
                mc.player.setData(FOTweaks.ELYTRA_FLIGHT_STATUS, !original);
                mc.player.connection.send(new SetElytraFlightStatusPacket(!original));
            }
        });
    }
}
