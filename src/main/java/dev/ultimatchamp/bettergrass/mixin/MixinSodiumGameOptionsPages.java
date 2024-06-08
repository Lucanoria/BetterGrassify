package dev.ultimatchamp.bettergrass.mixin;

import dev.ultimatchamp.bettergrass.FabricBetterGrassBakedModel;
import dev.ultimatchamp.bettergrass.config.FabricBetterGrassConfig;
import dev.ultimatchamp.bettergrass.config.SodiumOptionsStorage;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = SodiumGameOptionPages.class, remap = false)
public class MixinSodiumGameOptionsPages {
    @Redirect(method = "quality", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;add(Lme/jellysquid/mods/sodium/client/gui/options/Option;)Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;", ordinal = 1), remap = false)
    private static OptionGroup.Builder grassMode(OptionGroup.Builder instance, Option<?> option) {
        return instance.add(OptionImpl.createBuilder(FabricBetterGrassBakedModel.BetterGrassMode.class, SodiumOptionsStorage.INSTANCE)
                .setName(Text.translatable("yacl3.config.bettergrass:bettergrass_config.betterGrassMode"))
                .setTooltip(Text.translatable("yacl3.config.bettergrass:bettergrass_config.betterGrassMode.desc"))
                .setControl((opt) -> new CyclingControl<>(opt, FabricBetterGrassBakedModel.BetterGrassMode.class, new Text[]{
                        Text.translatable("yacl3.config.enum.BetterGrassMode.off"),
                        Text.translatable("yacl3.config.enum.BetterGrassMode.fast"),
                        Text.translatable("yacl3.config.enum.BetterGrassMode.fancy")
                }))
                .setBinding((options, value) -> FabricBetterGrassConfig.instance().betterGrassMode = value,
                    (options) -> FabricBetterGrassConfig.instance().betterGrassMode)
                .setImpact(OptionImpact.VARIES)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build());
    }
}
