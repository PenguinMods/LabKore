package com.blockypenguin.labkore.container;

import com.blockypenguin.labkore.util.Utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class LabKontainer extends Container {
	
	protected final TileEntity tileEntity;
	protected final PlayerEntity playerEntity;
    protected final IItemHandler playerInventory;

	protected LabKontainer(ContainerType<?> type, int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
		super(type, windowId);
		
		this.tileEntity = world.getTileEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
	}
	
	@Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, Utils.getBlockAtTE(tileEntity).getBlock());
    }
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
    	ItemStack stack = ItemStack.EMPTY;
    	Slot slot = this.inventorySlots.get(index);
    	
    	if(slot != null && slot.getHasStack()) {
    		ItemStack slotStack = slot.getStack();
    		stack = slotStack.copy();
    		
    		if(index < this.inventorySlots.size()) {
    			if(!this.mergeItemStack(slotStack, this.inventorySlots.size(), this.inventorySlots.size(), true))
    				return ItemStack.EMPTY;
    			
    		}else if(!this.mergeItemStack(slotStack, 0, this.inventorySlots.size(), false)) return ItemStack.EMPTY;

    		if(slotStack.isEmpty()) slot.putStack(ItemStack.EMPTY);
    		else slot.onSlotChanged();
    	}

    	return stack;
    }
	
	/**
     * @param inv Inventory of {@link Slot}s to add.
     * @param index Starting index of {@link Slot}s to add.
     * @param x Starting {@link Integer x}of {@link Slot}s to add.
     * @param y Starting {@link Integer y}of {@link Slot}s to add.
     * @param amount How many {@link Slot}s to add.
     * @param dx Distance between {@link Slot}s horizontally.(Usually 18)
     * @return {@link Integer Index}of next slot
     */
	protected int addSlotRow(IItemHandler inv, int index, int x, int y, int amount, int dx) {
        for(int i = 0 ; i < amount ; i++) {
            this.addSlot(new SlotItemHandler(inv, index, x, y));
            x += dx;
            index++;
        }
        
        return index;
    }
    
    /**
     * @param inv Inventory of {@link Slot}s to add.
     * @param index Starting index of {@link Slot}s to add.
     * @param x Starting {@link Integer x} of {@link Slot}s to add.
     * @param y Starting {@link Integer y} of {@link Slot}s to add.
     * @param horAmount How many {@link Slot}s to add horizontally.
     * @param dx Distance between {@link Slot}s horizontally. (Usually 18)
     * @param verAmount How many {@link Slot}s to add vertically.
     * @param dy Distance between {@link Slot}s vertically. (Usually 18)
     * @return {@link Integer Index} of next slot
     */
    protected int addSlotBox(IItemHandler inv, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for(int j = 0 ; j < verAmount ; j++) {
            index = addSlotRow(inv, index, x, y, horAmount, dx);
            y += dy;
        }
        
        return index;
    }
    
    protected void layoutPlayerInventorySlots(int x, int y) {
        addSlotBox(playerInventory, 9, x, y, 9, 18, 3, 18);
        
        y += 58;
        addSlotRow(playerInventory, 0, x, y, 9, 18);
    }
}
