package org.huajistudio.chessmaster.render;

import org.huajistudio.chessmaster.ChessMaster;
import org.lwjgl.bgfx.BGFXPlatformData;
import org.lwjgl.glfw.GLFWNativeCocoa;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.glfw.GLFWNativeX11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Platform;

import static org.lwjgl.bgfx.BGFX.*;
import static org.lwjgl.bgfx.BGFXPlatform.bgfx_set_platform_data;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class RenderManager {
	private int renderer = BGFX_RENDERER_TYPE_COUNT;
	private int pciId = BGFX_PCI_ID_NONE;
	private int width = 800;
	private int height = 600;
	private long window;
	private int reset = BGFX_RESET_VSYNC;

	public void init() {
		try (MemoryStack stack = MemoryStack.stackPush()) {

			if (!glfwInit())
				throw new RuntimeException("Cannot initialize GLFW");
			bgfx_init(renderer, pciId, 0, null, null);

			// the client (renderer) API is managed by bgfx
			glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);

			window = glfwCreateWindow(width, height, "Chess Master", 0, 0);

			if (window == 0)
				throw new RuntimeException("Error creating GLFW window");

			glfwSetFramebufferSizeCallback(window, this::resize);

			BGFXPlatformData platformData = BGFXPlatformData.callocStack(stack);

			switch (Platform.get()) {
				case LINUX:
					platformData.ndt(GLFWNativeX11.glfwGetX11Display());
					platformData.nwh(GLFWNativeX11.glfwGetX11Window(window));
					break;
				case MACOSX:
					platformData.ndt(NULL);
					platformData.nwh(GLFWNativeCocoa.glfwGetCocoaWindow(window));
					break;
				case WINDOWS:
					platformData.ndt(NULL);
					platformData.nwh(GLFWNativeWin32.glfwGetWin32Window(window));
					break;
			}

			platformData.context(NULL);
			platformData.backBuffer(NULL);
			platformData.backBufferDS(NULL);

			bgfx_set_platform_data(platformData);

			if (!bgfx_init(renderer, pciId, 0, null, null))
				throw new RuntimeException("Error initializing bgfx renderer");

			if (renderer == BGFX_RENDERER_TYPE_COUNT)
				renderer = bgfx_get_renderer_type();

			String rendererName = bgfx_get_renderer_name(renderer);
			if (rendererName.equals("NULL"))
				throw new RuntimeException("Error identifying bgfx renderer");

			ChessMaster.getLogger().info("Using renderer: {}", rendererName);

			bgfx_reset(width, height, reset);

			bgfx_set_debug(BGFX_DEBUG_TEXT);

			bgfx_set_view_clear(0,
				BGFX_CLEAR_COLOR | BGFX_CLEAR_DEPTH,
				0x23333333,
				1.0f,
				0);

			long lastTime;
			long startTime = lastTime = glfwGetTimerValue();

			while (!org.lwjgl.glfw.GLFW.glfwWindowShouldClose(window)) {
				glfwPollEvents();

				long now = glfwGetTimerValue();
				long frameTime = now - lastTime;
				lastTime = now;

				double freq = glfwGetTimerFrequency();
				double toMs = 1000.0 / freq;

				double time = (now - startTime) / freq;

				bgfx_set_view_rect(0, 0, 0, width, height);

				bgfx_touch(0);

				bgfx_dbg_text_clear(0, false);

				frame((float) time, (float) (frameTime * toMs));

				bgfx_frame(false);
			}

			dispose();

			bgfx_shutdown();

			glfwDestroyWindow(window);
			glfwTerminate();
		} catch (Exception e) {
			ChessMaster.getLogger().error("Cannot push stack!", e);
		}
	}

	public void resize(long window, int width, int height) {
		this.width = width;
		this.height = height;
		bgfx_reset(width, height, reset);
	}

	public long getWindow() {
		return window;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void frame(float time, float frameTime) {
		bgfx_dbg_text_printf(0, 1, 0x0f, String.format("Frame: %7.3f[ms]", frameTime));
	}

	public void dispose() {
		// TODO Dispose the window
	}
}
