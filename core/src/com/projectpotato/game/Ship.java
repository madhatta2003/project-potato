package com.projectpotato.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ship extends GameObject{
    SpriteBatch batch;
    float x = 0, y = 240, width = 64, height = 64;

    /**
     * Creates a ship object.
     */
    public Ship(float x, float y, float hp)
    {
        super(x, y, hp);
        batch = new SpriteBatch();
        img = new Texture(Gdx.files.internal("Ship.jpg"));
        hitbox.setSize(width, height);
    }

    /**
     * Fires the projectile from the front of the ship.
     * @param shipX Requires the ship's current x coordinate.
     * @param shipY Requires the ship's current y coordinate.
     */
    public void shoot(Projectile laser, float shipX, float shipY)
    {
        laser.setX(shipX + width);
        laser.setY(shipY + (height + 1)/2);
        laser.hitbox.setCenter(laser.getX(), laser.getY());
    }

    /**
     * Moves the ship.
     */
    public void move()
    {
        setY(getY() + 1);
    }
}
