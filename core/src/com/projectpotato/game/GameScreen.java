package com.projectpotato.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.deploy.uitoolkit.Window;

/**
 * Created by jaredserota on 6/18/16.
 */

public class GameScreen implements Screen
{

    final MainGame GAME;
    OrthographicCamera camera;

    Texture img;
    float deltaTime = Gdx.graphics.getDeltaTime();
    ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    ArrayList<GameObject> laserObjects = new ArrayList<GameObject>();
    ArrayList<GameObject> potatoObjects = new ArrayList<GameObject>();
    Ship ship;
    float shotTime;
    private int score;
    private BitmapFont font;
    private String scoreboardString;
    private static java.util.Random rand = new java.util.Random();


    public GameScreen(MainGame game)
    {
        this.GAME = game;

        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);


        // Initialize GameObjects.
        create();

        // The following listens for the Escape key to bring up pause menu

    }


    public void create()
    {

        gameObjects = new ArrayList<GameObject>();
        ship = new Ship(25, 200, 1);
        gameObjects.add(ship);

        score = 0;
        scoreboardString = "score: 0";
        font = new BitmapFont();
    }

    @Override
    public void show()
    {

    }

    private State state = State.RUN;

    @Override
    public void render(float delta)
    {
        switch (state)
        {
            case RUN:
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                // Update objects.
                camera.update();
                checkCollisions();
                checkHP();


                // RENDER DA POTATOES
                if (rand.nextInt(201) <= 1)
                {
                    Potato newPotato = new Potato(800, rand.nextInt(350), 5);
                    potatoObjects.add(newPotato);
                    gameObjects.add(newPotato);
                }

                GAME.batch.setProjectionMatrix(camera.combined);

                controlShip();

                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
                {
                    GAME.pause();
                }

                GAME.batch.begin();

                // Render objects.
                renderGameObjects();
                GAME.font.draw(GAME.batch, scoreboardString, 700, 395);
                GAME.batch.end();
                if (ship.getHP()<=0){
                    setGameState(State.GAMEOVER);
                }
                break;
            case PAUSE:
                GAME.batch.begin();
                GAME.font.draw(GAME.batch, "GAME PAUSED!", 300, 370);
                GAME.font.draw(GAME.batch, "Press 'C' to Continue", 300, 350);
                GAME.font.draw(GAME.batch, "Press 'M' to Quit to Main Menu", 300, 320);
                GAME.font.draw(GAME.batch, "Press 'Q' to Quit Game", 300, 300);
                GAME.batch.end();

                if (Gdx.input.isKeyJustPressed(Keys.C))
                {
                    setGameState(State.RUN);
                }
                if (Gdx.input.isKeyJustPressed(Keys.M))
                {
                    GAME.setScreen(new MainMenu(GAME));
                }
                if (Gdx.input.isKeyJustPressed(Keys.Q))
                {
                    Gdx.app.exit();
                }

                break;
            case RESUME:
                break;
            case GAMEOVER:
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                GAME.batch.begin();
                GAME.font.draw(GAME.batch, "GAME OVER!", 300, 370);
                GAME.font.draw(GAME.batch, "Press 'M' to Quit to Main Menu", 300, 320);
                GAME.font.draw(GAME.batch, "Press 'Q' to Quit Game", 300, 300);
                GAME.font.draw(GAME.batch, "Your score was:  " + score, 300, 250);
                GAME.batch.end();

                if (Gdx.input.isKeyJustPressed(Keys.M))
                {
                    GAME.setScreen(new MainMenu(GAME));
                }
                if (Gdx.input.isKeyJustPressed(Keys.Q))
                {
                    Gdx.app.exit();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {
        this.state = State.PAUSE;

    }

    @Override
    public void resume()
    {
        this.state = State.RESUME;
    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }

    public void setGameState(State s)
    {
        this.state = s;
    }

    private void renderGameObjects()
    {
        for (GameObject obj : gameObjects)
        {
            if (obj.getHP() != 0 && obj.getImage() != null)
            {
                GAME.batch.draw(obj.getImage(), obj.getX(), obj.getY());
            }
        }

        for (GameObject las : laserObjects)
        {

            if (las.getImage() != null)
            {
                las.moveTo(las.getX() + 6, las.getY());
            }
        }

        for (GameObject potato : potatoObjects)
        {
            if (potato.getImage() != null)
            {
                potato.moveTo(potato.getX() - 3, potato.getY());
            }
        }

    }

    /**
     * Runs every render, checking each object's HP.
     * If the HP is less than or equal to zero, the object is destroyed.
     */
    private void checkHP()
    {
        ArrayList<GameObject> garbage = new ArrayList<GameObject>();

        for (GameObject obj : gameObjects)
        {
            if (obj.getHP() <= 0)
            {
                garbage.add(obj);
            }
        }

        for (GameObject obj : garbage)
        {
            destroy(obj);
        }
    }

    /**
     * Destroys an object by removing it from the gameObjects list.
     *
     * @param obj GameObject to be destroyed.
     */
    private void destroy(GameObject obj)
    {
        gameObjects.remove(obj);
        score = score + 10;
        scoreboardString = "score: " + score;

        if (potatoObjects.contains(obj))
        {
            potatoObjects.remove(obj);
            score -= 10;
        }

        if (laserObjects.contains(obj))
        {
            laserObjects.remove(obj);
        }
    }

    /**
     * Runs every render, checking if any objects share coordinates.
     */
    private void checkCollisions()
    {
        for (GameObject obj1 : gameObjects)
        {
            for (GameObject obj2 : gameObjects)
            {
                // If the hitboxes overlap and the objects are not the same.
                if (obj1.getHitbox().overlaps(obj2.getHitbox()) && (obj1 != obj2))
                {
                    collide(obj1, obj2);
                }
            }
        }
    }

    /**
     * Subtracts HP from objects that collide.
     *
     * @param obj1 first object in collision
     * @param obj2 second object in collision
     */
    private void collide(GameObject obj1, GameObject obj2)
    {
        obj1.modifyHP(-1);
        obj2.modifyHP(-1);
    }

    /**
     * Checks to see if the game objects are on the screen.
     */
    private void offScreenCheck()
    {

        for (GameObject obj : gameObjects)
        {
            float x1 = obj.getX();
            float y1 = obj.getY();

            if (x1 < 0 || x1 > 600)
            {
                obj.modifyHP(obj.getHP());
            } else if (y1 < 0 || y1 > 480)
            {
                obj.modifyHP(obj.getHP());
            } else
            {
                continue;
            }
        }
    }

    public void controlShip()
    {
        if (Gdx.input.isKeyPressed(Keys.DOWN) && ship.getY() > 0)
        {
            ship.moveTo(ship.getX(), ship.getY() - 2);
        } else if (Gdx.input.isKeyPressed(Keys.UP) && ship.getY() < 325)
        {
            ship.moveTo(ship.getX(), ship.getY() + 2);
        }

        if (Gdx.input.isKeyJustPressed(Keys.SPACE))
        {
            Projectile laser = new Projectile(ship.getX(), ship.getY(), 1);
            ship.shoot(laser, ship.getX(), ship.getY());
            laserObjects.add(laser);
            gameObjects.add(laser);
            shotTime = deltaTime;
        }
    }

    public enum State
    {
        PAUSE,
        RUN,
        RESUME,
        GAMEOVER
    }
}
