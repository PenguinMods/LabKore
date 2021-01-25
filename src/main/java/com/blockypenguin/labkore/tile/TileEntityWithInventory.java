package com.blockypenguin.labkore.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.blockypenguin.labkore.util.UpdateDetectingItemStackHandler;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityWithInventory extends TileEntity {
	
	 private UpdateDetectingItemStackHandler inventoryHandler;
	 private LazyOptional<UpdateDetectingItemStackHandler> inventory;

	public TileEntityWithInventory(TileEntityType<?> type, int inventorySize) {
		super(type);
		
		this.inventoryHandler = new UpdateDetectingItemStackHandler(inventorySize, this::inventoryContentsChanged);
		this.inventory = LazyOptional.of(() -> inventoryHandler);
	}
	
	@Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return inventory.cast();
        
        return super.getCapability(cap, side);
    }
	
	@Override
    public void remove() {
        super.remove();
        inventory.invalidate();
    }
	
	@Override
    public void read(BlockState state, CompoundNBT tag) {
        inventoryHandler.deserializeNBT(tag.getCompound("inv"));
        
        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", inventoryHandler.serializeNBT());
        
        return super.write(tag);
    }
	
	protected void inventoryContentsChanged(int slot) {
		this.markDirty();
	}
}
