package xyz.bluspring.fotweaks.mixin.apothic_attributes.toggle_elytra_flight;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.bluspring.fotweaks.FOTweaks;

import net.minecraft.world.entity.LivingEntity;

@Mixin(value = IItemExtension.class, priority = 1050)
public interface IItemExtensionMixin {
    @Definition(id = "entity", local = @Local(type = LivingEntity.class, argsOnly = true))
    @Definition(id = "getAttributeValue", method = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D")
    @Definition(id = "ELYTRA_FLIGHT", field = "Ldev/shadowsoffire/apothic_attributes/api/ALObjects$Attributes;ELYTRA_FLIGHT:Lnet/minecraft/core/Holder;")
    @Expression("entity.getAttributeValue(ELYTRA_FLIGHT) > 0.0")
    @TargetHandler(mixin = "dev.shadowsoffire.apothic_attributes.mixin.IItemExtensionMixin", name = "canElytraFly")
    @ModifyExpressionValue(method = "@MixinSquared:Handler", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean checkPlayerFlightActive(boolean original, @Local(argsOnly = true) LivingEntity entity) {
        return original && entity.getData(FOTweaks.ELYTRA_FLIGHT_STATUS);
    }

    @Definition(id = "entity", local = @Local(type = LivingEntity.class, argsOnly = true))
    @Definition(id = "getAttributeValue", method = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D")
    @Definition(id = "ELYTRA_FLIGHT", field = "Ldev/shadowsoffire/apothic_attributes/api/ALObjects$Attributes;ELYTRA_FLIGHT:Lnet/minecraft/core/Holder;")
    @Expression("entity.getAttributeValue(ELYTRA_FLIGHT) > 0.0")
    @TargetHandler(mixin = "dev.shadowsoffire.apothic_attributes.mixin.IItemExtensionMixin", name = "elytraFlightTick")
    @ModifyExpressionValue(method = "@MixinSquared:Handler", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean checkPlayerFlightActive2(boolean original, @Local(argsOnly = true) LivingEntity entity) {
        return original && entity.getData(FOTweaks.ELYTRA_FLIGHT_STATUS);
    }
}
