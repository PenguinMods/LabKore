package com.blockypenguin.labkore;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class AbstractWaterLoggableBlock extends Block implements IWaterLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public AbstractWaterLoggableBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	/**
	 * Handles ticking the water if waterlogged. Call "super.neighborChanged()" first.
	 */
	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if(!world.isRemote)
			if(state.get(WATERLOGGED))
				world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
	}
	
	/**
	 * Handles the "waterlogged" state for you. Substitute "this.getDefaultState().with(...)" with "super.getStateForPlacement().with(...)"
	 */
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(WATERLOGGED, context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER);
	}
	
	/**
	 * Gets the "waterlogged" state for you. Use "super.getFluidState(state).with(...)"
	 */
	@Override
	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}
	
	/**
	 * Handles ticking the water if waterlogged. Call "super.updatePostPlacement()" first.
	 */
	@Override
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if(state.get(WATERLOGGED))
			world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));

		return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
	}
	
	/**
	 * Handles ticking the water if waterlogged. Call "super.onBlockAdded()" first.
	 */
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
		world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
	}
	
	/**
	 * Handles setting the "waterlogged" state on bucket click.
	 */
	@Override
	public boolean receiveFluid(IWorld world, BlockPos pos, BlockState state, FluidState fluidState) {
		if(!state.get(BlockStateProperties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
			world.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(true)), 3);
			world.getPendingFluidTicks().scheduleTick(pos, fluidState.getFluid(), Fluids.WATER.getTickRate(world));
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Adds the "waterlogged" state. Call "super.fillStateContainer()" first.
	 */
	@Override
	public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}
}
