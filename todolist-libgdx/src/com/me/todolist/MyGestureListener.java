package com.me.todolist;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class MyGestureListener implements GestureDetector.GestureListener {
    private boolean pressed;

    public void setPressed(boolean pressSource) {
        pressed = pressSource;
    }
    public boolean getPressed() {
        return pressed;
    }

    public boolean touchDown(float x, float y, int pointer, int button) {
        System.out.println("touchDown");
        return false;
    }

    public boolean tap(float x, float y, int count, int button) {
        System.out.println("tap");
        return false;
    }

    public boolean longPress(float x, float y) {
        System.out.println("longPress");
        return false;
    }

    public boolean fling(float velocityX, float velocityY, int button) {
        System.out.println("fling");
        return false;
    }

    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}