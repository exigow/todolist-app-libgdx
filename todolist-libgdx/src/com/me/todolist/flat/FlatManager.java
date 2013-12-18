package com.me.todolist.flat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class FlatManager {
    // Lists.
    private ArrayList<Flat>
        flatList, // List that contains the flats.
        slotList, // Slots with references to flats.
        bufList; // Buffer.

    private int counter; // How many flats.
    private Texture flatTexture;
    private float flatHeight, flatWidth; //
    private float inputPosition, localPickPosition;
    public Flat pickedFlat;
    public boolean picked;
    public Color baseColor;
    private float sizeX, sizeY;

    // Constructor.
    public FlatManager(float sizeX, float sizeY, float flatWidth, float flatHeight) {
        this.sizeX = sizeX; // Manager width.
        this.sizeY = sizeY; // Manager height.
        this.flatWidth = flatWidth; // Flat width.
        this.flatHeight = flatHeight; // Flat height.

        flatList = new ArrayList<Flat>();
        slotList = new ArrayList<Flat>();
        bufList = new ArrayList<Flat>();
        counter = 0;

        baseColor = new Color(178f / 256f, 132f / 256f, 73f / 256f, 1f);

        localPickPosition = 0;

        unpick();
    }

    // List getters.
    public ArrayList<Flat> getFlatList() {
        return flatList;
    }
    public ArrayList<Flat> getSlotList() {
        return slotList;
    }
    public ArrayList<Flat> getBufList() {
        return bufList;
    }

    // Touch position.
    public void setInputPosition(float position) {
        inputPosition = position;
    }

    // Adding flat.
    public Flat createFlat() {
        return createFlat("<default_name>");
    }
    public Flat createFlat(String string) {
        Flat flat = new Flat(flatTexture);
        flat.setID(counter);
        flat.setSize(flatWidth, flatHeight);
        flat.setText(string);

        flatList.add(flat);

        slotList.add(flat);
        counter++;
        return flat;
    }

    // Update per frame.
    public void update(float deltaTime) {
        // Update all flats.
        for (Flat flat: flatList) {
            flat.update(deltaTime);
            //flat.setText("id: " + flat.getID() + " push: " + flat.getPush());
            //flat.setText("id: " + flat.getID());
        }

        // If some flat is picked, set targetPos as mouse.
        if (picked) {
            pickedFlat.setTargetPosition(inputPosition - flatHeight * .5f + localPickPosition);
        }

        // Updating target position (from slot perspective)
        for (int i = 0; i < slotList.size(); i++) {
            float pos = slotToPosition(i) + slotList.get(i).getPush() * flatHeight * .125f;
            slotList.get(i).setTargetPosition(pos);
        }

        // Clear push.
        for (Flat flat: flatList) {
            flat.setPush(0);
        }
        // Push effect.
        if (picked) {
            int underSlot = positionToSlot(pickedFlat.getPosition() + 64f);
            if (underSlot != -1) {
                int dist = 3;
                for (int i = Math.max(underSlot - dist, 0); i < Math.min(slotList.size(), underSlot + dist); i++) {
                    int side = (i < underSlot)?(-1):(1);
                    int len = (dist - Math.abs(i - underSlot)) * side;
                    //len = dist - Math.abs(i - underSlot);

                    slotToFlat(i).setPush(len);
                    //System.out.print("[" + i + "] " + len + " ");
                }
            }
            localPickPosition *= .1f;
        }
    }

    // Flat texture setter.
    public void sendTexture(Texture texture) {
        this.flatTexture = texture;
    }

    // Convert slot number to screen position.
    public float slotToPosition(int slot) {
        //return sizeY - ((1 + slot) * flatHeight);
        return (slot * flatHeight);
    }

    // Convert position to best slot (returns -1 when failed)
    public int positionToSlot(float pos) {
        int a = -1;
        for (int i = 0; i < slotList.size(); i++) {
            if (slotToPosition(i) < pos && slotToPosition(i + 1) > pos) {
                a = i;
            }
        }
        return a;
    }

    // Returns flat that is attached to the slot.
    public Flat slotToFlat(int slot) {
        return slotList.get(slot);
    }

    // Picking flat.
    public void pick(Flat flat) {
        // Is picked.
        picked = true;
        pickedFlat = flat;

        // Compute local position.
        localPickPosition = pickedFlat.getPosition() - inputPosition + flatHeight / 2;

        // On top list (its about draw order)
        flatList.remove(flat);
        flatList.add(flat);
    }

    // Unpick flat.
    public void unpick() {
        picked = false;
        pickedFlat = null;
    }

    // Flat size getters.
    public float getFlatHeight() {
        return flatHeight;
    }
    public float getFlatWidth() {
        return flatWidth;
    }
}
