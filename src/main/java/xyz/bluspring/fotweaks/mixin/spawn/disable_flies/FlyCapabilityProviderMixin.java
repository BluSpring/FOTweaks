package xyz.bluspring.fotweaks.mixin.spawn.disable_flies;

import java.util.List;
import java.util.UUID;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import com.ninni.spawn.server.gui.fly.FlyCapabilityProvider;
import com.ninni.spawn.server.gui.fly.FlyData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.nbt.CompoundTag;

@IfModLoaded("spawn")
@Pseudo
@Mixin(FlyCapabilityProvider.class)
public abstract class FlyCapabilityProviderMixin {
    @Shadow @Final private List<FlyData> flies;

    @Inject(method = "setFlies", at = @At("HEAD"), cancellable = true)
    private void avoidSettingFlies(List<FlyData> flies, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "updateFly", at = @At("HEAD"), cancellable = true)
    private void avoidUpdatingFlies(UUID uuid, CompoundTag tag, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "deserializeNBT", at = @At("HEAD"), cancellable = true)
    private void avoidDeserializingFlies(CompoundTag tag, CallbackInfo ci) {
        ci.cancel();
        this.flies.clear();
    }
}
