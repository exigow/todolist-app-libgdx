package com.me.todolist;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "todo";
		cfg.useGL20 = true;
		cfg.width = 320;
		cfg.height = 480;
        cfg.resizable = false;
        ConfigLink link = new ConfigLink();
		new LwjglApplication(new ToDoList(link), cfg);
	}
}
