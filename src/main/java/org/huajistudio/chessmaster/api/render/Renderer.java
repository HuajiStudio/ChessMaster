package org.huajistudio.chessmaster.api.render;

import ro.fortsoft.pf4j.ExtensionPoint;

public interface Renderer extends ExtensionPoint {
	void render();
	String getName();
}
