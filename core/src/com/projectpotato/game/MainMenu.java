package com.projectpotato.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by jaredserota on 6/18/16.
 */
public class MainMenu implements Screen {
    final MainGame game;
    OrthographicCamera camera;

    /**
     * The following is staging for buttons
     */
    private Stage stage;
    private TextButton button;
    private TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;
    private BitmapFont font1;

    public MainMenu(final MainGame gam){
        game = gam;


        stage = new Stage(new ExtendViewport(800,480));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center().center();
        stage.addActor(table);

        font1 = new BitmapFont();
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font1;
        TextButton button1 = new TextButton("Begin Game!", textButtonStyle);
        TextButton button2 = new TextButton(" ", textButtonStyle);
        TextButton button3 = new TextButton("Quit Game!", textButtonStyle);

        stage.addActor(button1);
        stage.addActor(button2);
        stage.addActor(button3);

        Gdx.input.setInputProcessor(stage);

        button1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new GameScreen(game));
            }
        });

        /* For later button Functionality

        button2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //ADD CODE HERE
            }
        });
        */

        button3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });


        table.add(button1);
        table.row();
        table.add(button2);
        table.row();
        table.add(button3);
        table.row();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Project Potato!", 300, 350);
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();

    }

}
