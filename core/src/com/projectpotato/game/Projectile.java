package com.projectpotato.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Projectile extends GameObject{
    float width = 20, height = 3;

    /**
     * Creates a laser projectile.
     */
    public Projectile(float x, float y, float hp) {
        super(x, y, hp);
        img = new Texture(Gdx.files.internal("laser.jpg"));
        hitbox.setSize(width, height);
    }
}