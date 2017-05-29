package org.huajistudio.chessmaster.util.i18n;

import org.huajistudio.chessmaster.ChessMaster;
import ro.fortsoft.pf4j.PluginWrapper;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public interface I18n {
	AtomicReference<Locale> CURRENT_LOCALE = new AtomicReference<>(Locale.getDefault());
	Map<Locale, LanguageManager> LANGUAGE_MANAGER_MAP = new ConcurrentHashMap<>();

	static String format(String key, Object... args) {
		try {
			PluginWrapper plugin = ChessMaster.getPluginManager().whichPlugin(Class.forName(new Throwable().getStackTrace()[1].getClassName()));
			if (plugin == null)
				return format(CURRENT_LOCALE.get(), "chessmaster", key, args);
			else
				return format(CURRENT_LOCALE.get(), plugin.getPluginId(), key, args);
		} catch (ClassNotFoundException ignored) {
			return "";
		}
	}

	static String format(Locale locale, String domain, String key, Object... args) {
		if (LANGUAGE_MANAGER_MAP.containsKey(locale))
			return String.format(LANGUAGE_MANAGER_MAP.get(locale).getKey(domain, key), args);
		else {
			LANGUAGE_MANAGER_MAP.put(locale, new LanguageManager(locale));
			return format(locale, domain, key, args);
		}
	}
}
