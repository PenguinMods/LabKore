package com.blockypenguin.labkore.registry;

import java.lang.reflect.Field;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LabKoreRegistry {
	private static final Logger LOGGER = LogManager.getLogger("LabKore - Registry");
	
	private static final Set<Block> BLOCKS = Sets.newHashSet();
	private static final Set<Item> ITEMS = Sets.newHashSet();
	
	/**
	 * Scans all classes in a package for objects to register
	 * @param p Package name, e.g. "com.blockypenguin.labkit.registry"
	 */
	public static void scanMod(String p) {
		Reflections reflections = new Reflections(p);
		
        reflections.getFieldsAnnotatedWith(RegisterAnnotations.Block.class).forEach(b -> addTo(BLOCKS, b));
        reflections.getFieldsAnnotatedWith(RegisterAnnotations.Item.class).forEach(i -> addTo(ITEMS, i));
	}
	
	@SuppressWarnings("unchecked")
	private static <T> void addTo(Set<T> list, Field obj) {
		try {
			list.add((T)obj.get(null));
		}catch(IllegalArgumentException | IllegalAccessException e) {
			LOGGER.error("Could not register " + obj.getName() + " from class " + obj.getClass().getSimpleName());
		}
	}
	
	/**
	 * Only public because Forge needs it.
	 * DO NOT USE!
	 */
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> e) {
		BLOCKS.forEach(b -> e.getRegistry().register(b));
	}
	
	/**
	 * Only public because Forge needs it.
	 * DO NOT USE!
	 */
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> e) {
		ITEMS.forEach(i -> e.getRegistry().register(i));
	}
}
