package dynamictreesbop.worldgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeSpeciesSelector;
import com.ferreusveritas.dynamictrees.trees.Species;

import biomesoplenty.api.biome.BOPBiomes;
import dynamictreesbop.DynamicTreesBOP;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class BiomeSpeciesSelector implements IBiomeSpeciesSelector {

	Species swamp, apple, jungle, spruce, birch, oak, oakFloweringVine, yellowAutumn, orangeAutumn, magic, floweringOak, umbran, umbranConifer, umbranConiferMega, oakDying, decayed, fir, firSmall, pinkCherry, whiteCherry, maple, dead, jacaranda;
	
	HashMap<Integer, ITreeSelector> fastTreeLookup = new HashMap<Integer, ITreeSelector>();
	
	@Override
	public ResourceLocation getName() {
		return new ResourceLocation(DynamicTreesBOP.MODID, "default");
	}

	@Override
	public int getPriority() {
		return 1;
	}

	@Override
	public Decision getSpecies(World world, Biome biome, BlockPos pos, IBlockState state, Random rand) {
		if (biome == null) return new Decision();
		
		int biomeId = Biome.getIdForBiome(biome);
		ITreeSelector select;
				
		if(fastTreeLookup.containsKey(biomeId)) {
			select = fastTreeLookup.get(biomeId);//Speedily look up the selector for the biome id
		} else {
			if (biome == BOPBiomes.alps_foothills.get()) select = new StaticDecision(new Decision(firSmall));
			
			else if (biome == BOPBiomes.boreal_forest.get()) select = new RandomDecision(rand).addSpecies(yellowAutumn, 4).addSpecies(spruce, 4).addSpecies(oak, 5);
			
			else if (biome == BOPBiomes.cherry_blossom_grove.get()) select = new RandomDecision(rand).addSpecies(pinkCherry, 6).addSpecies(whiteCherry, 4);
			
			else if (biome == BOPBiomes.coniferous_forest.get()) select = new RandomDecision(rand).addSpecies(fir, 3).addSpecies(firSmall, 5);
			
			else if (biome == BOPBiomes.dead_forest.get()) select = new RandomDecision(rand).addSpecies(spruce, 3).addSpecies(decayed, 1).addSpecies(oakDying, 8);
			
			else if (biome == BOPBiomes.dead_swamp.get()) select = new RandomDecision(rand).addSpecies(decayed, 1).addSpecies(dead, 2);
			
			else if (biome == BOPBiomes.fen.get()) select = new RandomDecision(rand).addSpecies(decayed, 1);
			
			else if (biome == BOPBiomes.land_of_lakes.get()) select = new RandomDecision(rand).addSpecies(spruce, 3).addSpecies(birch, 1).addSpecies(oak, 5);
			
			else if (biome == BOPBiomes.lavender_fields.get()) select = new RandomDecision(rand).addSpecies(floweringOak, 1).addSpecies(jacaranda, 3);
			
			else if (biome == BOPBiomes.lush_desert.get()) select = new RandomDecision(rand).addSpecies(decayed, 1);
			
			else if (biome == BOPBiomes.lush_swamp.get()) select = new StaticDecision(new Decision(swamp));
			
			else if (biome == BOPBiomes.maple_woods.get()) select = new RandomDecision(rand).addSpecies(spruce, 1).addSpecies(maple, 5);
			
			else if (biome == BOPBiomes.meadow.get()) select = new StaticDecision(new Decision(spruce));
			
			else if (biome == BOPBiomes.mountain.get()) select = new RandomDecision(rand).addSpecies(oak, 1);
			
			else if (biome == BOPBiomes.mountain_foothills.get()) select = new RandomDecision(rand).addSpecies(oak, 1);
			
			else if (biome == BOPBiomes.mystic_grove.get()) select = new RandomDecision(rand).addSpecies(magic, 17).addSpecies(oakFloweringVine, 10).addSpecies(floweringOak, 8).addSpecies(jacaranda, 9);
			
			else if (biome == BOPBiomes.ominous_woods.get()) select = new RandomDecision(rand).addSpecies(umbran, 4).addSpecies(umbranConifer, 5).addSpecies(umbranConiferMega, 4).addSpecies(decayed, 3).addSpecies(dead, 1);
			
			else if (biome == BOPBiomes.orchard.get()) select = new RandomDecision(rand).addSpecies(floweringOak, 6).addSpecies(apple, 1);
			
			else if (biome == BOPBiomes.rainforest.get()) select = new RandomDecision(rand).addSpecies(jungle, 1).addSpecies(birch, 4).addSpecies(oak, 4).addSpecies(floweringOak, 7);
			
			else if (biome == BOPBiomes.seasonal_forest.get()) select = new RandomDecision(rand).addSpecies(yellowAutumn, 4).addSpecies(orangeAutumn, 5).addSpecies(oak, 1).addSpecies(oakDying, 2).addSpecies(maple, 4);
			
			else if (biome == BOPBiomes.shield.get()) select = new RandomDecision(rand).addSpecies(spruce, 4);
			
			else if (biome == BOPBiomes.snowy_coniferous_forest.get()) select = new RandomDecision(rand).addSpecies(fir, 2).addSpecies(firSmall, 4);
			
			else if (biome == BOPBiomes.snowy_forest.get()) select = new RandomDecision(rand).addSpecies(oak, 3).addSpecies(oakDying, 1);
			
			else if (biome == BOPBiomes.tropical_rainforest.get()) select = new RandomDecision(rand).addSpecies(jungle, 2);
			
			else if (biome == BOPBiomes.wasteland.get()) select = new RandomDecision(rand).addSpecies(decayed, 3).addSpecies(dead, 1);
			
			else if (biome == BOPBiomes.wetland.get()) select = new RandomDecision(rand).addSpecies(spruce, 5);
			
			else if (biome == BOPBiomes.woodland.get()) select = new StaticDecision(new Decision(oak));
			
			
			else if (biome == Biomes.FOREST || biome == Biomes.FOREST_HILLS) select = new RandomDecision(world.rand).addSpecies(oak, 8).addSpecies(birch, 2).addSpecies(floweringOak, 1);
			
			else if (biome == Biomes.EXTREME_HILLS || biome == Biomes.EXTREME_HILLS_WITH_TREES) select = new RandomDecision(world.rand).addSpecies(spruce, 3).addSpecies(jacaranda, 1);
			
			else return new Decision();
			
			fastTreeLookup.put(biomeId, select); //Cache decision for future use
		}
		
		return select.getDecision();
	}

	@Override
	public void init() {
		swamp = TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "oakswamp"));
		apple = TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "oakapple"));
		jungle = TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "jungle"));
		spruce = TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "spruce"));
		birch = TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "birch"));
		oak = TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "oak"));
		oakFloweringVine = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "oakfloweringvine"));
		
		yellowAutumn = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "yellowautumn"));
		orangeAutumn = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "orangeautumn"));
		magic = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "magic"));
		umbran = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "umbran"));
		umbranConifer = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "umbranconifer"));
		umbranConiferMega = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "umbranconifermega"));
		oakDying = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "oakdying"));
		fir = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "fir"));
		firSmall = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "firsmall"));
		pinkCherry = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "pinkcherry"));
		whiteCherry = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "whitecherry"));
		maple = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "maple"));
		dead = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "dead"));
		jacaranda = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "jacaranda"));
		
		floweringOak = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "floweringoak"));
		decayed = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, "decayed"));
	}
	
	private interface ITreeSelector {
		Decision getDecision();
	}
	
	private class StaticDecision implements ITreeSelector {
		final Decision decision;
		
		public StaticDecision(Decision decision) {
			this.decision = decision;
		}

		@Override
		public Decision getDecision() {
			return decision;
		}
	}
	
	private class RandomDecision implements ITreeSelector {

		private class Entry {
			public Entry(Decision d, int w) {
				decision = d;
				weight = w;
			}
			
			public Decision decision;
			public int weight;
		}
		
		ArrayList<Entry> decisionTable = new ArrayList<Entry>();
		int totalWeight;
		Random rand;
		
		public RandomDecision(Random rand) {
			this.rand = rand;
		}
		
		public RandomDecision addSpecies(Species species, int weight) {
			decisionTable.add(new Entry(new Decision(species), weight));
			totalWeight += weight;
			return this;
		}
		
		@Override
		public Decision getDecision() {
			int chance = rand.nextInt(totalWeight);
			
			for(Entry entry: decisionTable) {
				if(chance < entry.weight) {
					return entry.decision;
				}
				chance -= entry.weight;
			};

			return decisionTable.get(decisionTable.size() - 1).decision;
		}
		
	}

}
