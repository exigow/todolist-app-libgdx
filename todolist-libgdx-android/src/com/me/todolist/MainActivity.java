package com.me.todolist;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    ConfigLink configLink = new ConfigLink();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Why it does not work???!!!!
        System.out.println("(inActivity) setBarSize: " + configLink.getFlatHeight());
        configLink.setFlatHeight(64);
        // ^end

        AndroidApplicationConfiguration gfxConfig = new AndroidApplicationConfiguration();
        gfxConfig.useGL20 = false;

        ToDoList instance = new ToDoList(configLink);
        initialize(instance, gfxConfig);
    }
}