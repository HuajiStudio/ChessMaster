package org.huajistudio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.pf4j.JarPluginManager;
import ro.fortsoft.pf4j.PluginManager;

public class ChessMaster {
	private static final Logger LOGGER = LoggerFactory.getLogger("ChessMaster");
	private static final PluginManager PLUGIN_MANAGER = new JarPluginManager();

	static {
		PLUGIN_MANAGER.loadPlugins();
		PLUGIN_MANAGER.startPlugins();
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public static PluginManager getPluginManager() {
		return PLUGIN_MANAGER;
	}
}
