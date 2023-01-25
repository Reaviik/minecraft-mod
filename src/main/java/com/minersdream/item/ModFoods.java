package com.minersdream.item;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;


public class ModFoods {

    public static final FoodProperties BERYL_NUT = (new FoodProperties.Builder())
            .nutrition(4)
            .saturationMod(0.3F)
            .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000, 1),1)
            .meat()
            .build();
    public static final FoodProperties PALEBERRY = (new FoodProperties.Builder())
            .nutrition(4)
            .saturationMod(0.8F)
            .fast()
            .effect(new MobEffectInstance(MobEffects.LUCK, 6000, 1),1)
            .meat()
            .build();
}
