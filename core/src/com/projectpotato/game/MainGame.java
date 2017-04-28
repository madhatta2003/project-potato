package com.projectpotato.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game
{
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create()
    {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenu(this));


		/* Meant to be for different text style
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Tempe.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 12;
		BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		font = new BitmapFont();
		*/
    }

    @Override
    public void render()
    {
        super.render();
		/* Removed for testing of Menu screen

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.end();
		*/
    }
}

