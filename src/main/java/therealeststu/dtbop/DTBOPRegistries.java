package therealeststu.dtbop;

import biomesoplenty.api.biome.BOPBiomes;
import biomesoplenty.worldgen.feature.misc.HugeToadstoolFeature;
import biomesoplenty.worldgen.feature.misc.SmallRedMushroomFeature;
import biomesoplenty.worldgen.feature.misc.SmallToadstoolFeature;
import com.dtteam.dynamictrees.api.cell.CellKit;
import com.dtteam.dynamictrees.event.TypeRegistryEvent;
import com.dtteam.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.dtteam.dynamictrees.api.worldgen.FeatureCanceller;
import com.dtteam.dynamictrees.block.leaves.LeavesProperties;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.systems.genfeature.BeeNestGenFeature;
import com.dtteam.dynamictrees.systems.genfeature.GenFeature;
import com.dtteam.dynamictrees.tree.species.Species;
import com.dtteam.dynamictrees.utility.CommonVoxelShapes;
import com.dtteam.dynamictrees.worldgen.featurecancellation.MushroomFeatureCanceller;
import com.dtteam.dynamictrees.event.RegistryEvent;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.bus.api.SubscribeEvent;
import therealeststu.dtbop.block.CobwebLeavesProperties;
import therealeststu.dtbop.cell.DTBOPCellKits;
import therealeststu.dtbop.genfeature.DTBOPGenFeatures;
import therealeststu.dtbop.growthlogic.DTBOPGrowthLogicKits;
import therealeststu.dtbop.tree.*;

import java.util.Objects;

@EventBusSubscriber(modid = DynamicTreesBOP.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DTBOPRegistries {

    public static final VoxelShape GLOWSHROOM_AGE0 = Shapes.create(0, 0, 0, 1, 0.75, 1);
    public static final VoxelShape TOADSTOOL_AGE0 = Shapes.create(2/16f, 0, 2/16f, 14/16f, 1, 14/16f);
    public static final VoxelShape MUSHROOM_CAP_SHORT_ROUND = Block.box(5D, 3D, 5D, 11D, 7D, 11D);
    public static final VoxelShape ROUND_SHORT_MUSHROOM = Shapes.or(CommonVoxelShapes.MUSHROOM_STEM, MUSHROOM_CAP_SHORT_ROUND);
    public static final VoxelShape TOADSTOOL_CAP = Block.box(5.5D, 3.0D, 5.5D, 10.5D, 10.0D, 10.5D);
    public static final VoxelShape TOADSTOOL = Shapes.or(CommonVoxelShapes.MUSHROOM_STEM, TOADSTOOL_CAP);

    public static void setup() {
        CommonVoxelShapes.SHAPES.put(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "glowshroom_age0").toString(), GLOWSHROOM_AGE0);
        CommonVoxelShapes.SHAPES.put(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "toadstool_age0").toString(), TOADSTOOL_AGE0);
        CommonVoxelShapes.SHAPES.put(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "round_short_mushroom").toString(), ROUND_SHORT_MUSHROOM);
        CommonVoxelShapes.SHAPES.put(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "toadstool").toString(), TOADSTOOL);
    }

    @SubscribeEvent
    public static void onGenFeatureRegistry(final RegistryEvent<GenFeature> event) {
        if (!event.isEntryOfType(GenFeature.class)) return;
        DTBOPGenFeatures.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void onGrowthLogicKitRegistry(final RegistryEvent<GrowthLogicKit> event) {
        if (!event.isEntryOfType(GrowthLogicKit.class)) return;
        DTBOPGrowthLogicKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void onCellKitRegistry(final RegistryEvent<CellKit> event) {
        if (!event.isEntryOfType(CellKit.class)) return;
        DTBOPCellKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(TypeRegistryEvent<LeavesProperties> event) {
        event.registerType(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "cobweb"), CobwebLeavesProperties.TYPE);
    }

    @SubscribeEvent
    public static void registerSpeciesTypes(final TypeRegistryEvent<Species> event) {
        event.registerType(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "twiglet"), TwigletSpecies.TYPE);
        event.registerType(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "poplar"), PoplarSpecies.TYPE);
        event.registerType(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "cypress"), CypressSpecies.TYPE);
        event.registerType(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "generates_on_stone"), GenOnStoneSpecies.TYPE);
    }

    @SubscribeEvent
    public static void registerSpecies(final RegistryEvent<Species> event) {

    }

    @SubscribeEvent
    public static void onBlocksRegistry(final RegisterEvent event) {
        Bush.INSTANCES.forEach(Bush::setup);

        final Species floweringOak = Species.REGISTRY.get(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "flowering_oak"));
        final Species floweringAppleOak = Species.REGISTRY.get(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "flowering_apple_oak"));
        final Species infested = Species.REGISTRY.get(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "infested"));
            LeavesProperties floweringLeaves = LeavesProperties.REGISTRY.get(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "flowering_oak"));
        if (floweringOak.isValid() && floweringLeaves.isValid()) {
            floweringLeaves.setFamily(floweringOak.getFamily());
            floweringOak.addValidLeafBlocks(floweringLeaves);
        }

        if (floweringAppleOak.isValid())
            if (floweringLeaves.isValid()) floweringAppleOak.addValidLeafBlocks(floweringLeaves);
            if (infested.isValid()) {
            LeavesProperties silkLeaves = LeavesProperties.REGISTRY.get(ResourceLocation.fromNamespaceAndPath(DynamicTreesBOP.MOD_ID, "silk"));
            infested.addValidLeafBlocks(silkLeaves);
        }
    }

}
