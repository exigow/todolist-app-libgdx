package com.me.todolist;

public class ConfigLink {
    private float flatHeight;
    public ConfigLink() {
        setFlatHeight(64); // Default size.
    }


    // Flat Height Getter/setter.
    public float getFlatHeight() {
        return flatHeight;
    }
    public void setFlatHeight(float barSize) {
        this.flatHeight = barSize;
    }
}
