package top.ioart;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ioart.items.UsefulItems;

public class Useful implements ModInitializer {
	public static final String MOD_ID = "useful";
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		UsefulItems.initialize();
		LOGGER.info("Hello Fabric world!");
	}
}