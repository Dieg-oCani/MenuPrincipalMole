package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.glass.ui.Menu;

/**
 * Created by dc on 9/1/16.
 */
public class PantallaAcercaDe implements Screen {

    private final Juego juego;
    private Stage escena;
    private Texture texturaBtnBack;
    private Texture texturaCara;

    //Camara virtual
    private OrthographicCamera camara;
    private Viewport vista;
    private final int ANCHO_MUNDO = 1280;
    private final int ALTO_MUNDO = 800;

    public PantallaAcercaDe(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {

        //Camara
        camara = new OrthographicCamera(ANCHO_MUNDO,ALTO_MUNDO);
        camara.position.set(ANCHO_MUNDO/2, ALTO_MUNDO/2,0);
        camara.update();

        vista = new StretchViewport(ANCHO_MUNDO,ALTO_MUNDO, camara);



        //Para poner el boton Back de ésta pantalla
        texturaBtnBack = new Texture("back.png");
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(new TextureRegion(texturaBtnBack));
        ImageButton btnBack = new ImageButton(trdBtnBack);

        escena = new Stage();
        escena.addActor(btnBack);

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Regresar al menu principal
                juego.setScreen(new MenuPrincipal(juego));
            }
        });

        Gdx.input.setInputProcessor(escena);

        // Para agregar caras:

        //Aqui para simplificar lo estamos cargando directamente
        // De manera correcta se hace con Asset Manager.
        texturaCara = new Texture("cara.png");

        for(int i=0;i<2;i++){
            for(int j=0; j<3; j++){
                Image imagenCara = new Image(texturaCara);
                imagenCara.setPosition(100+j*200,100+i*200);
                escena.addActor(imagenCara);
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.setViewport(vista);
        escena.draw();
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
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

    }
}
