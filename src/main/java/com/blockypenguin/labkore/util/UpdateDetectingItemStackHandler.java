package com.blockypenguin.labkore.util;

import java.util.function.Consumer;

import net.minecraftforge.items.ItemStackHandler;

public class UpdateDetectingItemStackHandler extends ItemStackHandler {
	
	protected Consumer<Integer> onContentsChanged;
	
	public UpdateDetectingItemStackHandler(int invSize, Consumer<Integer> onContentsChanged) {
		super(invSize);
		this.onContentsChanged = onContentsChanged;
	}

	@Override
	protected void onContentsChanged(int slot) {
		onContentsChanged.accept(slot);
	}
}
