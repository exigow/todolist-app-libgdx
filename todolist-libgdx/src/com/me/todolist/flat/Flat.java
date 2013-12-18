package com.me.todolist.flat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class Flat {
    private TextureRegion textureRegion;
    private PolygonRegion polygonRegion;
    private float position, targetPosition, rotation, rotationTarget, move, push, border;
    private int id;
    private String text;
    private Vector2 size, rotationVector;
    float offset;
    public Color color;

    public Flat(Texture texture) {
        position = 0;
        targetPosition = 0;
        move = 0;
        size = new Vector2();
        rotationVector = new Vector2();
        border = 4f;
        rotation = 0f;
        rotationTarget = 0f;

        /*color = new Color(
        .75f + (-.1f + (float)Math.random() * .2f),
        .375f + (-.1f + (float)Math.random() * .2f),
        .125f + (-.1f + (float)Math.random() * .2f), 1);*/
        color = new Color(178f / 256f, 132f / 256f, 73f / 256f, 1f);

        textureRegion = new TextureRegion(texture);
        polygonRegion = new PolygonRegion(textureRegion, new float[] {0, 0, 0, 0, 0, 0, 0, 0}, new short[] {0, 1, 2, 1, 2, 3});
        //float[] coords = new float[] {0, 1, 0, 0, 1, 1, 1, 0};
        float[] coords = new float[] {0, .125f, 0, 0, .625f, .125f, .625f, 0};
        for (int i = 0; i < 8; i++) {
            polygonRegion.getTextureCoords()[i] = coords[i];
        }

        updateVertices();
    }

    private void updateVertices() {
        float[] vertices = polygonRegion.getVertices();

        vertices[0] = border + rotationVector.x;
        vertices[1] = position + border + rotationVector.y + offset;

        vertices[2] = border - rotationVector.x;
        vertices[3] = position + size.y - border - rotationVector.y - offset;

        vertices[4] = size.x - border - rotationVector.x;
        vertices[5] = position + border + rotationVector.y + offset;

        vertices[6] = size.x - border + rotationVector.x;
        vertices[7] = position + size.y - border - rotationVector.y - offset;

        for (int i = 0; i < 8; i++) {
            polygonRegion.getVertices()[i] = vertices[i];
        }
    }

    public void update(float deltaTime) {
        move += (targetPosition - position) * .125f;
        position += move * deltaTime * 24f;
        rotationTarget = -move * .0125f + (push * .25f);
        rotation += (rotationTarget - rotation) * deltaTime * 8f;
        move *= 60f * deltaTime * .8125f;

        rotationVector.x = (float)Math.cos(rotation + Math.PI / 2) * 16f;
        rotationVector.y = (float)Math.sin(rotation + Math.PI / 2) * (size.y - border * 2) * .5f;
        updateVertices();
    }

    public void setPush(int level) {
        push = level;
    }
    public float getPush() {
        return push;
    }

    // Text.
    public String getText() {
        return text;
    }

    // Size.
    public void setSize(float w, float h) {
        size.set(w, h);
        offset = (size.y / 2) - border;
    }
    public Vector2 getSize() {
        return size;
    }

    // Position.
    public void setPosition(float position) {
        this.position = position;
    }
    public float getPosition() {
        return position;
    }
    public void setTargetPosition(float position) {
        this.targetPosition = position;
    }

    // ID.
    public void setID(int id) {
        this.id = id;
    }
    public int getID() {
        return id;
    }

    // TextureRegion.
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
    // PolygonRegion.
    public PolygonRegion getPolygonRegion() {
        return polygonRegion;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void updateColor() {

    }
}
