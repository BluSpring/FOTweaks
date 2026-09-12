package xyz.bluspring.fotweaks.network;

import io.netty.buffer.ByteBuf;
import xyz.bluspring.fotweaks.FOTweaks;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetElytraFlightStatusPacket(boolean status) implements CustomPacketPayload {
    public static final Type<SetElytraFlightStatusPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(FOTweaks.MODID, "set_elytra_flight_sstatus"));
    public static final StreamCodec<ByteBuf, SetElytraFlightStatusPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL, SetElytraFlightStatusPacket::status,
        SetElytraFlightStatusPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
