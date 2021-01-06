package com.blockypenguin.labkore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LabKore.MODID)
public class LabKore {
    private static final Logger LOGGER = LogManager.getLogger("LabKore - Main");
    
    public static final String MODID = "labkore";

    public LabKore() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("LabKore is Installed!");
    }
}
