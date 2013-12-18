package com.me.todolist;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.me.todolist.flat.Flat;
import com.me.todolist.flat.FlatManager;

public class ToDoList implements ApplicationListener {
    private PolygonSpriteBatch pbatch;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Vector2 mousePosition, windowSize;
    private FlatManager flatManager;
    private Stepper stepper;
    private BitmapFont font;
    private float fontH;
    private MyGestureListener myGestureListener;

    // Constructor.
    private ConfigLink configLink;
    public ToDoList(ConfigLink configLink) {
        super();
        this.configLink = configLink;
    }

    // Create Event.
	public void create() {
        myGestureListener = new MyGestureListener();
        GestureDetector gestureDetector = new GestureDetector(myGestureListener);
        Gdx.input.setInputProcessor(gestureDetector);

        windowSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mousePosition = new Vector2();
        pbatch = new PolygonSpriteBatch();
        stepper = new Stepper();
        flatManager = new FlatManager(windowSize.x, windowSize.y, windowSize.x, configLink.getFlatHeight());
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        Texture tBar = new Texture(Gdx.files.internal("data/bigmask.png"));
        tBar.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("data/comf.fnt"), Gdx.files.internal("data/comf_0.png"), false);
        font.setColor(1, 1, 1, 1);
        fontH = 24;

        flatManager.sendTexture(tBar);

        flatManager.createFlat("Go to gym");
        flatManager.createFlat("Buy groceries");
        flatManager.createFlat("Mow the lawn");
        flatManager.createFlat("Submit article");
        flatManager.createFlat("Reply to morning emails");
        flatManager.createFlat("Wash car");
        flatManager.createFlat("Backup computer");

        flatManager.createFlat("asdd");
        flatManager.createFlat("asdasdasd");
	}

    // Rendering frame.
	public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        mousePosition.set(Gdx.input.getX(), windowSize.y - Gdx.input.getY());

        flatManager.setInputPosition(mousePosition.y);

        flatManager.update(deltaTime);

		Gdx.gl.glClearColor(49f / 256f, 46f / 256f, 43f / 256f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        for (Flat flat: flatManager.getFlatList()) {
            pbatch.setColor(flat.getColor());
            pbatch.begin();
            pbatch.draw(flat.getPolygonRegion(), 0f, 0f);
            pbatch.end();

            batch.begin();
            font.draw(batch, flat.getText(), flat.getSize().y / 2, flat.getPosition() + flat.getSize().y / 2 + fontH / 2);
            batch.end();
        }


        myGestureListener.setPressed(Gdx.input.isKeyPressed(Input.Buttons.LEFT));
        stepper.pickStepFrom(myGestureListener.getPressed());
        switch (stepper.getStep()) {
            case wait: {
                break;
            }
            case press: {
                int slot = flatManager.positionToSlot(mousePosition.y);
                if (slot != -1) {
                    System.out.println("<Stepper> PRESS: slot:" + slot);
                    flatManager.pick(flatManager.slotToFlat(slot));
                    flatManager.getSlotList().remove(slot);
                }
                break;
            }
            case hold: {
                break;
            }
            case release: {
                if (flatManager.picked) {
                    int slot = flatManager.positionToSlot(mousePosition.y - flatManager.getFlatHeight() * .5f);
                    System.out.println("<Stepper> RELEASE: slot:" + slot);
                    if (slot == -1) {
                        flatManager.getSlotList().add(flatManager.pickedFlat);
                    } else {
                        // Zrzut do bufora.
                        for (int i = slot + 1; i < flatManager.getSlotList().size(); i++) {
                            flatManager.getBufList().add(flatManager.getSlotList().get(i));
                        }
                        // Usuniecie kolejki.
                        for (int i = flatManager.getSlotList().size() - 1; i > slot; i--) {
                            flatManager.getSlotList().remove(i);
                        }
                        // Dodanie picka.
                        flatManager.getSlotList().add(flatManager.pickedFlat);
                        // Dodanie bufora.
                        flatManager.getSlotList().addAll(flatManager.getBufList());
                        // Clear bufora.
                        flatManager.getBufList().clear();
                    }
                    flatManager.unpick();
                }
                break;
            }
        }

        shapeRenderer.setColor(1f, 0, 0, 1f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(mousePosition.x, mousePosition.y, 16f + ((false)?(1):(0)) * -8f);
        shapeRenderer.end();
	}

	public void resize(int width, int height) {
	}
	public void pause() {
	}
    public void resume() {
	}
    public void dispose() {
        pbatch.dispose();
    }
}
