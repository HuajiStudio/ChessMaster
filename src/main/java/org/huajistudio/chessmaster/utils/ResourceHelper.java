package org.huajistudio.chessmaster.utils;

import org.huajistudio.chessmaster.ChessMaster;
import org.huajistudio.chessmaster.file.ResourceLocation;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.InputStream;
import java.util.Optional;

public interface ResourceHelper {
	static Optional<InputStream> openInputStream(ResourceLocation location) {
		if (location.getDomain().equals("chessmaster"))
			return Optional.ofNullable(ChessMaster.class.getClassLoader().getResourceAsStream(location.toString()));
		PluginWrapper plugin = ChessMaster.getPluginManager().getPlugin(location.getDomain());
		if (plugin == null)
			return Optional.empty();
		return Optional.ofNullable(plugin.getPluginClassLoader().getResourceAsStream(location.toString()));
	}
}
