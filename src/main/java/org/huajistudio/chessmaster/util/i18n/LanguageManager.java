package org.huajistudio.chessmaster.util.i18n;

import org.huajistudio.chessmaster.ChessMaster;
import ro.fortsoft.pf4j.PluginWrapper;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class LanguageManager {
	// A domain-bundle language map.
	private Map<String, ResourceBundle> bundleMap = new ConcurrentHashMap<>();

	public LanguageManager(Locale locale) {
		bundleMap.put("chessmaster", ResourceBundle.getBundle("assets/lang/", locale, ChessMaster.class.getClassLoader(), new UTF8Control()));
		for (PluginWrapper plugin : ChessMaster.getPluginManager().getPlugins())
			bundleMap.put(plugin.getPluginId(), ResourceBundle.getBundle("assets/lang/", locale, plugin.getPluginClassLoader(), new UTF8Control()));
	}

	public ResourceBundle getBundle(String domain) {
		return bundleMap.get(domain);
	}

	public String getKey(String domain, String location) {
		return bundleMap.containsKey(domain) ? (bundleMap.get(domain).containsKey(location) ? bundleMap.get(domain).getString(location) : location) : location;
	}
}
