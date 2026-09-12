package xyz.bluspring.fotweaks;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xyz.bluspring.fotweaks.network.SetElytraFlightStatusPacket;

import net.minecraft.server.level.ServerPlayer;

@Mod(FOTweaks.MODID)
public class FOTweaks {
    public static final String MODID = "fotweaks";

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_REGISTER = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MODID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> ELYTRA_FLIGHT_STATUS = ATTACHMENT_REGISTER.register("elytra_flight_status", () -> AttachmentType.builder(() -> true)
        .serialize(Codec.BOOL)
        .copyOnDeath()
        .build());

    public FOTweaks(IEventBus bus) {
        ATTACHMENT_REGISTER.register(bus);
        bus.addListener((RegisterPayloadHandlersEvent event) -> {
            event.registrar("1")
                .optional()
                .playBidirectional(SetElytraFlightStatusPacket.TYPE, SetElytraFlightStatusPacket.STREAM_CODEC, (packet, ctx) -> {
                    ctx.enqueueWork(() -> {
                        ctx.player().setData(ELYTRA_FLIGHT_STATUS, packet.status());
                    });
                });
        });

        NeoForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedInEvent event) -> {
            if (event.getEntity() instanceof ServerPlayer player && player.connection.hasChannel(SetElytraFlightStatusPacket.TYPE)) {
                player.connection.send(new SetElytraFlightStatusPacket(player.getData(ELYTRA_FLIGHT_STATUS)));
            }
        });
    }
}
