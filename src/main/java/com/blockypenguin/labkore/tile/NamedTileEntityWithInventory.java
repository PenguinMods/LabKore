package com.blockypenguin.labkore.tile;

import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class NamedTileEntityWithInventory extends TileEntityWithInventory implements INamedContainerProvider, INameable {
	
	protected TranslationTextComponent name;

	public NamedTileEntityWithInventory(TileEntityType<?> type, int inventorySize, TranslationTextComponent name) {
		super(type, inventorySize);
		this.name = name;
	}

	@Override
	public ITextComponent getDisplayName() {
		return getName();
	}

	@Override
	public ITextComponent getName() {
		return name;
	}
}
