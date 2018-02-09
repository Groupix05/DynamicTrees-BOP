package dynamictreesbop.trees;

import java.util.List;
import java.util.Random;

import com.ferreusveritas.dynamictrees.ModBlocks;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.blocks.BlockRootyDirt;
import com.ferreusveritas.dynamictrees.trees.DynamicTree;
import com.ferreusveritas.dynamictrees.trees.Species;

import biomesoplenty.api.biome.BOPBiomes;
import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.api.enums.BOPTrees;
import biomesoplenty.api.enums.BOPWoods;
import biomesoplenty.common.block.BlockBOPLeaves;
import biomesoplenty.common.block.BlockBOPLog;
import biomesoplenty.common.block.BlockBOPMushroom;
import dynamictreesbop.DynamicTreesBOP;
import dynamictreesbop.ModContent;
import dynamictreesbop.trees.TreeMagic.SpeciesMagic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.registries.IForgeRegistry;

public class TreeCherry extends DynamicTree {
	
	public class SpeciesPinkCherry extends Species {
		
		SpeciesPinkCherry(DynamicTree treeFamily) {
			super(new ResourceLocation(treeFamily.getName().getResourceDomain(), "pinkcherry"), treeFamily, ModContent.pinkCherryLeavesProperties);
			
			setBasicGrowingParameters(0.3f, 12.0f, upProbability, lowestBranchHeight, 0.8f);
			
			setDynamicSapling(new BlockDynamicSapling("pinkcherrysapling").getDefaultState());
			
			envFactor(Type.COLD, 0.75f);
			envFactor(Type.HOT, 0.50f);
			envFactor(Type.DRY, 0.50f);
			envFactor(Type.FOREST, 1.05f);
			
			addAcceptableSoil(BOPBlocks.grass, BOPBlocks.dirt);
			
			generateSeed();
			
			setupStandardSeedDropping();
		}
		
		@Override
		public boolean isBiomePerfect(Biome biome) {
			return isOneOfBiomes(biome, BOPBiomes.cherry_blossom_grove.get());
		}
		
		@Override
		public boolean rot(World world, BlockPos pos, int neighborCount, int radius, Random random) {
			if(super.rot(world, pos, neighborCount, radius, random)) {
				if(radius > 4 && TreeHelper.isRooty(world, pos.down()) && world.getLightFor(EnumSkyBlock.SKY, pos) < 4) {
					world.setBlockState(pos, random.nextInt(3) == 0 ? BOPBlocks.mushroom.getDefaultState().withProperty(BlockBOPMushroom.VARIANT, BlockBOPMushroom.MushroomType.TOADSTOOL) : BOPBlocks.mushroom.getDefaultState().withProperty(BlockBOPMushroom.VARIANT, BlockBOPMushroom.MushroomType.PORTOBELLO));//Change branch to a mushroom
					world.setBlockState(pos.down(), ModBlocks.blockStates.podzol);//Change rooty dirt to Podzol
				}
				return true;
			}
			
			return false;
		}
		
	}
	
	public class SpeciesWhiteCherry extends Species {
		
		SpeciesWhiteCherry(DynamicTree treeFamily) {
			super(new ResourceLocation(treeFamily.getName().getResourceDomain(), "whitecherry"), treeFamily, ModContent.whiteCherryLeavesProperties);
			
			setBasicGrowingParameters(0.3f, 12.0f, upProbability, lowestBranchHeight, 0.8f);
			
			setDynamicSapling(new BlockDynamicSapling("whitecherrysapling").getDefaultState());
			
			envFactor(Type.COLD, 0.75f);
			envFactor(Type.HOT, 0.50f);
			envFactor(Type.DRY, 0.50f);
			envFactor(Type.FOREST, 1.05f);
			
			addAcceptableSoil(BOPBlocks.grass, BOPBlocks.dirt);
			
			generateSeed();
			
			setupStandardSeedDropping();
		}
		
		@Override
		public boolean isBiomePerfect(Biome biome) {
			return isOneOfBiomes(biome, BOPBiomes.cherry_blossom_grove.get());
		}
		
		@Override
		public boolean rot(World world, BlockPos pos, int neighborCount, int radius, Random random) {
			if(super.rot(world, pos, neighborCount, radius, random)) {
				if(radius > 4 && TreeHelper.isRooty(world, pos.down()) && world.getLightFor(EnumSkyBlock.SKY, pos) < 4) {
					world.setBlockState(pos, random.nextInt(3) == 0 ? BOPBlocks.mushroom.getDefaultState().withProperty(BlockBOPMushroom.VARIANT, BlockBOPMushroom.MushroomType.TOADSTOOL) : BOPBlocks.mushroom.getDefaultState().withProperty(BlockBOPMushroom.VARIANT, BlockBOPMushroom.MushroomType.PORTOBELLO));//Change branch to a mushroom
					world.setBlockState(pos.down(), ModBlocks.blockStates.podzol);//Change rooty dirt to Podzol
				}
				return true;
			}
			
			return false;
		}
		
	}
	
	Species whiteSpecies;
	
	public TreeCherry() {
		super(new ResourceLocation(DynamicTreesBOP.MODID, "cherry"));
		
		IBlockState primLog = BlockBOPLog.paging.getVariantState(BOPWoods.CHERRY);
		setPrimitiveLog(primLog, BlockBOPLog.paging.getVariantItem(BOPWoods.CHERRY));
		
		ModContent.pinkCherryLeavesProperties.setTree(this);
		ModContent.whiteCherryLeavesProperties.setTree(this);
	}
	
	@Override
	public void createSpecies() {
		setCommonSpecies(new SpeciesPinkCherry(this));
		whiteSpecies = new SpeciesWhiteCherry(this);
	}
	
	@Override
	public void registerSpecies(IForgeRegistry<Species> speciesRegistry) {
		super.registerSpecies(speciesRegistry);
		speciesRegistry.register(whiteSpecies);
	}
	
	@Override
	public List<Block> getRegisterableBlocks(List<Block> blockList) {
		blockList.add(getCommonSpecies().getDynamicSapling().getBlock());
		blockList.add(whiteSpecies.getDynamicSapling().getBlock());
		return super.getRegisterableBlocks(blockList);
	}
	
	@Override
	public List<Item> getRegisterableItems(List<Item> itemList) {
		itemList.add(whiteSpecies.getSeed());
		return super.getRegisterableItems(itemList);
	}
	
	@Override
	public int foliageColorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos) {
		return 0xffffff;
	}
	
}