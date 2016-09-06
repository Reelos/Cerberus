package de.reelos.lwjgltest;

import static de.reelos.stu.logic.GameWorld.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class MainDisplay implements Runnable {

	private long winID;

	@Override
	public void run() {
		init();
		loop();
	}

	private void init() {
		System.out.println("LWJGL VERSION " + Version.getVersion());

		if (!glfwInit()) {
			throw new IllegalStateException("Init failed");
		}

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		winID = glfwCreateWindow(WORLD_X, WORLD_Y, "Cerberus", NULL, NULL);
		if (winID == NULL)
			throw new RuntimeException("FAILED TO CREATE WINDOW");

		glfwSetKeyCallback(winID, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(winID, true);
			}
		});

		long priMon = glfwGetPrimaryMonitor();
		GLFWVidMode vidmod = glfwGetVideoMode(priMon);

		glfwSetWindowPos(winID, (vidmod.width() - WORLD_X) / 2, (vidmod.height() - WORLD_Y) / 2);
		glfwMakeContextCurrent(winID);
		glfwShowWindow(winID);
	}

	private void loop() {
		GL.createCapabilities();
		System.out.println("OPENGL VERSION " + glGetString(GL_VERSION));
		glClearColor(0, 0, 0, 0);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WORLD_X, WORLD_Y, 0, -1, 1);

		while (!glfwWindowShouldClose(winID)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			drawMe();
			glfwSwapBuffers(winID);
			glfwPollEvents();
		}
	}

	public long getID() {
		return winID;
	}

	public static void main(String[] args) {
		new MainDisplay().run();
	}

	public void drawMe() {
		glBegin(GL_QUADS);
		glColor3f(0.0f, 0.0f, 0.2f);
		glVertex2f(-1, 1);
		glVertex2f(-0.5f, 1);
		glVertex2f(-0.5f, 0.5f);
		glVertex2f(-1, 0.5f);
		glEnd();
	}
}
