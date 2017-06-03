package org.huajistudio.chessmaster;

import javafx.application.Application;
import net.hakugyokurou.aeb.EventBus;
import org.huajistudio.chessmaster.client.render.RenderManager;
import org.huajistudio.chessmaster.selector.RenderEngineSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.pf4j.JarPluginManager;
import ro.fortsoft.pf4j.PluginManager;

public class ChessMaster {
	private static final Logger LOGGER = LoggerFactory.getLogger("ChessMaster");
	private static final PluginManager PLUGIN_MANAGER = new JarPluginManager();
	private static final EventBus EVENT_BUS = new EventBus("ChessMaster");
	private static final RenderManager RENDER_MANAGER = new RenderManager();

	public static Logger getLogger() {
		return LOGGER;
	}

	public static PluginManager getPluginManager() {
		return PLUGIN_MANAGER;
	}

	public static EventBus getEventBus() {
		return EVENT_BUS;
	}

	public static void postEvent(Object event) {
		EVENT_BUS.post(event);
	}

	public static RenderManager getRenderManager() {
		return RENDER_MANAGER;
	}

	public static void main(String[] args) {
		PLUGIN_MANAGER.loadPlugins();
		PLUGIN_MANAGER.startPlugins();
//		RENDER_MANAGER.init();
		Application.launch(RenderEngineSelector.class, args);
	}
}
