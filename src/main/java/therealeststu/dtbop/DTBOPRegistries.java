package therealeststu.dtbop;

import biomesoplenty.api.biome.BOPBiomes;
import biomesoplenty.common.worldgen.feature.misc.SmallRedMushroomFeature;
import com.ferreusveritas.dynamictrees.DynamicTrees;
import com.ferreusveritas.dynamictrees.api.cell.CellKit;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.ferreusveritas.dynamictrees.api.worldgen.FeatureCanceller;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.systems.genfeature.BeeNestGenFeature;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictrees.util.CommonVoxelShapes;
import com.ferreusveritas.dynamictrees.worldgen.featurecancellation.MushroomFeatureCanceller;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import therealeststu.dtbop.block.CobwebLeavesProperties;
import therealeststu.dtbop.cell.DTBOPCellKits;
import therealeststu.dtbop.genfeature.DTBOPGenFeatures;
import therealeststu.dtbop.growthlogic.DTBOPGrowthLogicKits;
import therealeststu.dtbop.tree.*;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTBOPRegistries {

    public static final VoxelShape SHROOM_AGE0 = Shapes.create(0, 0, 0, 1, 0.75, 1);
    public static final VoxelShape MUSHROOM_CAP_SHORT_ROUND = Block.box(5D, 3D, 5D, 11D, 7D, 11D);
    public static final VoxelShape ROUND_SHORT_MUSHROOM = Shapes.or(CommonVoxelShapes.MUSHROOM_STEM, MUSHROOM_CAP_SHORT_ROUND);

    public static void setup() {
        CommonVoxelShapes.SHAPES.put(new ResourceLocation(DynamicTreesBOP.MOD_ID, "glowshroom_age0").toString(), SHROOM_AGE0);
        CommonVoxelShapes.SHAPES.put(new ResourceLocation(DynamicTreesBOP.MOD_ID, "round_short_mushroom").toString(), ROUND_SHORT_MUSHROOM);
    }

    @SubscribeEvent
    public static void onGenFeatureRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<GenFeature> event) {
        DTBOPGenFeatures.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void onGrowthLogicKitRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<GrowthLogicKit> event) {
        DTBOPGrowthLogicKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void onCellKitRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<CellKit> event) {
        DTBOPCellKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(TypeRegistryEvent<LeavesProperties> event) {
        event.registerType(new ResourceLocation(DynamicTreesBOP.MOD_ID, "cobweb"), CobwebLeavesProperties.TYPE);
    }

    @SubscribeEvent
    public static void registerSpeciesTypes(final TypeRegistryEvent<Species> event) {
        event.registerType(new ResourceLocation(DynamicTreesBOP.MOD_ID, "twiglet"), TwigletSpecies.TYPE);
        event.registerType(new ResourceLocation(DynamicTreesBOP.MOD_ID, "poplar"), PoplarSpecies.TYPE);
        event.registerType(new ResourceLocation(DynamicTreesBOP.MOD_ID, "cypress"), CypressSpecies.TYPE);
        event.registerType(new ResourceLocation(DynamicTreesBOP.MOD_ID, "maple"), MapleSpecies.TYPE);
        event.registerType(new ResourceLocation(DynamicTreesBOP.MOD_ID, "generates_on_stone"), GenOnStoneSpecies.TYPE);
    }

    @SubscribeEvent
    public static void registerSpecies(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<Species> event) {

    }

    @SubscribeEvent
    public static void onBlocksRegistry(final RegisterEvent event) {
        Bush.INSTANCES.forEach(Bush::setup);

        final Species floweringOak = Species.REGISTRY.get(new ResourceLocation(DynamicTreesBOP.MOD_ID, "flowering_oak"));
        final Species floweringAppleOak = Species.REGISTRY.get(new ResourceLocation(DynamicTreesBOP.MOD_ID, "flowering_apple_oak"));
        final Species infested = Species.REGISTRY.get(new ResourceLocation(DynamicTreesBOP.MOD_ID, "infested"));
        final Species rainbow_birch = Species.REGISTRY.get(new ResourceLocation(DynamicTreesBOP.MOD_ID, "rainbow_birch"));

        LeavesProperties floweringLeaves = LeavesProperties.REGISTRY.get(new ResourceLocation(DynamicTreesBOP.MOD_ID, "flowering_oak"));
        if (floweringOak.isValid() && floweringLeaves.isValid()) {
            floweringLeaves.setFamily(floweringOak.getFamily());
            floweringOak.addValidLeafBlocks(floweringLeaves);
        }
        if (floweringAppleOak.isValid())
            if (floweringLeaves.isValid()) floweringAppleOak.addValidLeafBlocks(floweringLeaves);

        if (infested.isValid()) {
            LeavesProperties silkLeaves = LeavesProperties.REGISTRY.get(new ResourceLocation(DynamicTreesBOP.MOD_ID, "silk"));
            infested.addValidLeafBlocks(silkLeaves);
        }
        //This has to be added in-code as the worldgen chance function cannot be set by the treepack
        if (rainbow_birch.isValid()) {
            rainbow_birch.addGenFeature(new BeeNestGenFeature(new ResourceLocation("dynamictrees", "bee_nest"))
                    .with(BeeNestGenFeature.WORLD_GEN_CHANCE_FUNCTION, (world, pos) -> {
                        Holder<Biome> biomeHolder = world.getUncachedNoiseBiome(pos.getX() >> 2, pos.getY() >> 2, pos.getZ() >> 2);
                        if (biomeHolder.is(BOPBiomes.AURORAL_GARDEN))
                            return 0.02;
                        else return biomeHolder.is(BiomeTags.IS_FOREST) ? 0.0005 : 0.0;
                    }));
            rainbow_birch.addGenFeature(new BeeNestGenFeature(new ResourceLocation("dynamictrees", "bee_nest"))
                    .with(BeeNestGenFeature.WORLD_GEN_CHANCE_FUNCTION, (world, pos) ->
                            Objects.requireNonNull(ForgeRegistries.BIOMES.tags()).isKnownTagName(Tags.Biomes.IS_LUSH) ? 0.0005 : 0.0));
        }
    }

    public static final FeatureCanceller MUSHROOM_CANCELLER = new MushroomFeatureCanceller<>(new ResourceLocation(DynamicTreesBOP.MOD_ID,"mushroom"), HugeMushroomFeatureConfiguration.class){
        @Override
        public boolean shouldCancel(final ConfiguredFeature<?, ?> configuredFeature, final BiomePropertySelectors.NormalFeatureCancellation featureCancellations) {
            final ResourceLocation featureRegistryName = ForgeRegistries.FEATURES.getKey(configuredFeature.feature());
            if (featureRegistryName == null) {return false;}

            if (configuredFeature.config() instanceof HugeMushroomFeatureConfiguration) {
                return true;
            }
            if (configuredFeature.feature() instanceof SmallRedMushroomFeature){
                return true;
            }

            return super.shouldCancel(configuredFeature, featureCancellations);
        }
    };

    @SubscribeEvent
    public static void onFeatureCancellerRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<FeatureCanceller> event) {
        event.getRegistry().registerAll(MUSHROOM_CANCELLER);
    }

}
