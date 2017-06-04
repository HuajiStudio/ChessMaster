package org.huajistudio.chessmaster.client.render.opengl;

import org.huajistudio.chessmaster.api.render.Renderer;
import org.huajistudio.chessmaster.file.ResourceLocation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class OpenGLRenderer implements Renderer {
	private static Map<ResourceLocation, Integer> resourceTextureIdMap;

	@Override
	public void render() {
		resourceTextureIdMap = new ConcurrentHashMap<>();
		Thread renderLoop = new Thread(new RenderLoop());
		renderLoop.start();
	}

	public static Map<ResourceLocation, Integer> getResourceTextureIdMap() {
		return resourceTextureIdMap;
	}

	@Override
	public String getName() {
		return "opengl";
	}
}
