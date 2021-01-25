package com.blockypenguin.labkore.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class Utils {
	
	/**
	 * Returns a random object from an array
	 * 
	 * @param <T> The type of the {@code array}.
	 * @param array The {@code array}
	 * @return A randomly selected object from the {@code array}
	 */
	public static <T> T getRandomObjectFromArray(T[] array) {
		return array[new Random().nextInt(array.length)];
	}
	
	/**
	 * Turns a block into a BlockItem
	 * 
	 * @param block The {@link Block}
	 * @param builder {@link Item.Properties} for the returned {@link BlockItem item}
	 * @param registryName The registry name for the item
	 * @return A BlockItem for {@code block}
	 */
	public static Item getItemFromBlock(Block block, Item.Properties builder, ResourceLocation registryName) {
		return new BlockItem(block, builder).setRegistryName(registryName);
	}
	
	/**
	 * Turns a block into a BlockItem, using the {@code block}'s registry name as the item's regitry name
	 * 
	 * @param block The {@link Block}
	 * @param builder {@link Item.Properties} for the returned {@link BlockItem item}
	 * @return A BlockItem for {@code block}
	 */
	public static Item getItemFromBlock(Block block, Item.Properties builder) {
		return getItemFromBlock(block, builder, block.getRegistryName());
	}
	
	/**
	 * Code taken from <b>wyn_price</b>'s post on <a href="https://forums.minecraftforge.net/topic/74979-1144-rotate-voxel-shapes/">this forge forum post.</a>
	 * The code <b>has</b> been modified.
	 * 
	 * @param from The {@link Direction} the {@link VoxelShape shape} already faces
	 * @param to The {@link Direction} the {@link VoxelShape shape} should be rotated to face
	 * @param shape The {@link VoxelShape} to be rotated
	 * @return The rotated {@link VoxelShape shape}
	 */
	public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
		VoxelShape[] buffer = {shape, VoxelShapes.empty()};

		int times = (to.getHorizontalIndex() - from.getHorizontalIndex() + 4) % 4;
		for(int i = 0; i < times; i++) {
			buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1], VoxelShapes.create(1-maxZ, minY, minX, 1-minZ, maxY, maxX)));
			buffer[0] = buffer[1];
			buffer[1] = VoxelShapes.empty();
		}

		return buffer[0];
	}

	public static BlockState getBlockAtTE(TileEntity tile) {
		BlockPos pos = tile.getPos();
		return tile.getWorld().getBlockState(pos);
	}
}