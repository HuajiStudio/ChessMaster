package org.huajistudio.chessmaster.client.render.opengl;

import org.huajistudio.chessmaster.api.render.Renderer;


public class OpenGLRenderer implements Renderer {
	@Override
	public void render() {
		Thread renderLoop = new Thread(new RenderLoop());
		renderLoop.start();
	}

	@Override
	public String getName() {
		return "opengl";
	}
}
