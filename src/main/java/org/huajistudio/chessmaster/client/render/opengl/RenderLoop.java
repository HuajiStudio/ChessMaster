package org.huajistudio.chessmaster.client.render.opengl;

import org.huajistudio.chessmaster.ChessMaster;
import org.huajistudio.chessmaster.api.Board;
import org.huajistudio.chessmaster.api.BoardPos;
import org.huajistudio.chessmaster.api.event.client.KeyEvent;
import org.huajistudio.chessmaster.client.render.Rectangle;
import org.huajistudio.chessmaster.util.RenderUtil;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
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
		errorCallback = GLFWErrorCallback.createPrint(System.err);
		if (!glfwInit())
			ChessMaster.getLogger().error("Cannot initialize GLFW!", new RuntimeException());
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

		// Make the window visible
		glfwShowWindow(window);
		board = new Board();
	}

	private void loop() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Rectangle rectangle = new Rectangle(-0.785f + j * 0.2f, 0.785f - i * 0.2f, 0.175f, 0.175f);
				rectangles.put(new BoardPos(i, j), rectangle);
				glColor3i(1, 1, 1);
				glBegin(GL_POLYGON);
				glVertex2fv(RenderUtil.toFloatArray(rectangle.getLeftUp()));
				glVertex2fv(RenderUtil.toFloatArray(rectangle.getRightUp()));
				glVertex2fv(RenderUtil.toFloatArray(rectangle.getRightDown()));
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
