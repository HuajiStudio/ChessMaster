package org.huajistudio.chessmaster.client.render.opengl;

import org.huajistudio.chessmaster.ChessMaster;
import org.huajistudio.chessmaster.api.Board;
import org.huajistudio.chessmaster.api.BoardPos;
import org.huajistudio.chessmaster.api.event.client.KeyEvent;
import org.huajistudio.chessmaster.client.render.Rectangle;
import org.huajistudio.chessmaster.file.ResourceLocation;
import org.huajistudio.chessmaster.util.RenderUtil;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL13;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class RenderLoop implements Runnable {
	private long window;
	private GLFWErrorCallback errorCallback;
	private Board board;
	private int width = 600;
	private int height = 800;
	private Map<BoardPos, Rectangle> rectangles = new ConcurrentHashMap<>();

	private void init() {
		ChessMaster.getLogger().info("Initializing GLFW");
		errorCallback = GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit())
			ChessMaster.getLogger().error("Cannot initialize GLFW!", new RuntimeException());
		ChessMaster.getLogger().info("GLFW Creation complete");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		window = glfwCreateWindow(600, 600, "Chess Master", MemoryUtil.NULL, MemoryUtil.NULL);
		if (window == MemoryUtil.NULL)
			ChessMaster.getLogger().error("Cannot create window!", new RuntimeException());
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			switch (action) {
				case GLFW_RELEASE:
					ChessMaster.postEvent(new KeyEvent.Release(window, key, scancode, mods));
					break;
				case GLFW_PRESS:
					ChessMaster.postEvent(new KeyEvent.Press(window, key, scancode, mods));
					break;
				case GLFW_REPEAT:
					ChessMaster.postEvent(new KeyEvent.Repeat(window, key, scancode, mods));
			}
		});
		glfwSetWindowSizeCallback(window, (window1, w, h) -> {
			if (w > 0 && h > 0) {
				width = w;
				height = h;
			}
		});
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		}
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);

		glEnable(GL_TEXTURE_2D);

		// Make the window visible
		glfwShowWindow(window);
		board = new Board();
		ChessMaster.getLogger().info("Initialization Complete");
	}

	private void loop() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		int textureId = OpenGLUtils.bindTexture(new ResourceLocation("chessmaster", "board.png"));

		GL13.glActiveTexture(textureId);

		glBindTexture(GL_TEXTURE_2D, textureId);

		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Rectangle rectangle = new Rectangle(-0.785f + j * 0.2f, 0.785f - i * 0.2f, 0.175f, 0.175f);
				rectangles.put(new BoardPos(i, j), rectangle);
				glColor3i(1, 1, 1);
				glBegin(GL_POLYGON);
				glTexCoord2i(0, 0);
				glVertex2fv(RenderUtil.toFloatArray(rectangle.getLeftUp()));
				glTexCoord2i(1, 0);
				glVertex2fv(RenderUtil.toFloatArray(rectangle.getRightUp()));
				glTexCoord2i(1, 1);
				glVertex2fv(RenderUtil.toFloatArray(rectangle.getRightDown()));
				glTexCoord2i(0, 1);
				glVertex2fv(RenderUtil.toFloatArray(rectangle.getLeftDown()));
				glEnd();
			}
		}

		glfwSwapBuffers(window); // swap the color buffers

		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
	}

	@Override
	public void run() {
		init();
		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		// Set the clear color
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		while (!glfwWindowShouldClose(window)) {
			loop();
		}

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(errorCallback).free();
	}

	public long getWindow() {
		return window;
	}

	public Board getBoard() {
		return board;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Map<BoardPos, Rectangle> getRectangles() {
		return rectangles;
	}
}
