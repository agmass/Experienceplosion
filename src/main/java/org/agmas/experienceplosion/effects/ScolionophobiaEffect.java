package org.agmas.experienceplosion.effects;

import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class ScolionophobiaEffect extends StatusEffect implements PolymerStatusEffect {

    public ScolionophobiaEffect() {
        // category: StatusEffectCategory - describes if the effect is helpful (BENEFICIAL), harmful (HARMFUL) or useless (NEUTRAL)
        // color: int - Color is the color assigned to the effect (in RGB)
        super(StatusEffectCategory.HARMFUL, 0x8DDD46);
    }

    @Override
    public @Nullable StatusEffect getPolymerReplacement(ServerPlayerEntity player) {
        return StatusEffects.UNLUCK.value();
    }
}