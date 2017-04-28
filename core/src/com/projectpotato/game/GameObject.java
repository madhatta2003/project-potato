package com.projectpotato.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

/**
 * All visible objects inherit from GameObject.
 * Provides methods and properties for interacting with the game engine.
 */
public abstract class GameObject {
    protected float x;
    protected float y;
    protected float hp;
    protected Rectangle hitbox;
    protected Texture img;

    /**
     * Constructor requires an x, y, and hp.
     */
    public GameObject(float x, float y, float hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        hitbox = new Rectangle(0, 0, 0, 0);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() { return y; }

    public void setY(float y) { this.y = y; }

    public float getHP() {
        return hp;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {this.hitbox = hitbox;}

    /**
     * Sets HP by modifying it up or down.
     * An object with 0 or less HP is destroyed.
     * @param amount the amount to adjust the hp, can be negative or positive
     */
    public void modifyHP(float amount) {
        hp += amount;
        setHP(amount);
    }

    protected void setHP(float hp) {
        this.hp = hp;
    }

    /**
     * Sets X and Y simultaneously.
     */
    public void moveTo(float x, float y) {
        setX(x);
        setY(y);
        hitbox.setPosition(x, y);
    }

    /**
     * Gets object's image.
     */
    public Texture getImage()
    {
        return img;
    }

    public void setImage(Texture img)
    {
        this.img = img;
    }
}