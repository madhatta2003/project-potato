package com.projectpotato.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Potato extends GameObject{
    float x = 0, y = 240, width = 50, height = 50;

    /**
     * Creates a potato object.
     */
    public Potato(float x, float y, float hp) {
        super(x, y, hp);
        img = new Texture(Gdx.files.internal("Potato.jpg"));
        hitbox.setSize(width, height);
    }


}
