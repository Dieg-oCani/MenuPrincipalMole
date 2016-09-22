package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by kevinalvarez on 15/09/16.
 */
public class PantallaNivel extends ClickListener implements Screen, InputProcessor {


    private OrthographicCamera camara;
    private Viewport vista;

    //Textura
    private Texture texturaFondo;
    private Texture texturaHoyo;
    private Texture texturaMole;
    private Texture texturaGana;



    //SpriteBatch sirve para adminstrar los trazos
    private SpriteBatch batch;
    private final Juego juego;
    private Fondo fondo;
    private Array<Hoyo> hoyos;
    private Array<Mole> moles;



    //contador de objetos golpeados

    private int contador = 0;

    //Estado del juego
    private Estado estado = Estado.JUGANDO;

    private Texto texto;


    public PantallaNivel(Juego juego){
            this.juego = juego;
        }

    @Override
    public void show() {
        inicializarCamara();
        cargarTexturas();
        crearEscena();

        //Quien procesa los eventos
        Gdx.input.setInputProcessor(this);
        texto = new Texto();
    }
     //crear elementos que van a crear mi escena
    private void crearEscena() {

        //Trazos en la escena
        batch = new SpriteBatch();
        fondo = new Fondo(texturaFondo);

        //Crear los Hoyos
        hoyos = new Array<Hoyo>(9);
        for(int i=0; i<2; i++){
            for(int j=0; j<3; j++){
                Hoyo h = new Hoyo(texturaHoyo,200+j*300,200+i*300);
                hoyos.add(h);
            }
        }
        moles = new Array<Mole>(9);
        for(int i=0; i<2; i++){
            for(int j=0; j<3; j++){
                Mole m = new Mole(texturaMole,230+j*300,230+i*300);
                moles.add(m);
            }
        }


    }

    private void cargarTexturas() {
        texturaFondo = new Texture("fondoNivel.jpg");
        texturaHoyo = new Texture("hole.png");
        texturaMole = new Texture("mole.png");
        texturaGana = new Texture("gana.png");
    }

    private void inicializarCamara() {
        camara = new OrthographicCamera(1280,800);
        camara.position.set(1280/2,800/2,0);
        camara.update();
        vista = new StretchViewport(1280,800,camara);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        //Dibujar

        fondo.draw(batch);
        //recorrer hoyos y dibujarlos
        for (Hoyo h : hoyos){
            h.draw(batch);
        }


        //recorrer moles y dibujarlos
        for (Mole m :
                moles){
            if (m.getEstado()!= Mole.Estado.MURIENDO){
            m.draw(batch);
        }
        }
            for (Mole m : moles){
                if (m.getEstado() == Mole.Estado.MURIENDO){
                    m.draw(batch);
                }}

        if (estado==Estado.GANO){
            batch.draw(texturaGana,200,200);

        }

        texto.mostrarMensaje(batch,"Marcador: " + contador, 100, 750);

        //Pregunto si alguno se salvó
        if (estado == Estado.JUGANDO) {
            for (Mole m : moles) {
                if (m.isSalvado()) {
                    contador--;
                    m.setSalvado(false);
                }
            }
        }

                batch.end();


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

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    //screenx valores absolutos de las pantallas
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Preguntar si le pegó a algún topo
        Vector3 v = new Vector3(screenX, screenY, 0);
        camara.unproject(v);
        float x = v.x;
        float y = v.y;

        if (estado == Estado.JUGANDO) {

            for (Mole m :
                    moles) {
                if (m.contiene(x, y)) {
                    //le pegué!!!
                    m.setEstado(Mole.Estado.MURIENDO);
                    contador++; //un topo golpeado más
                    if (contador == 5) {
                        //Ganó
                        estado = Estado.GANO;
                    }
                }
            }
        }   else {
            //preguntar si regresa al menú principal
            if (x > 1000 && y < 200) { //abajo, derecha
                juego.setScreen(new MenuPrincipal(juego)); //cambio de pantalla
            } else if (x<200 && y<=200){ //abajo izquierda
                estado = Estado.JUGANDO;
                contador = 0;
            }

        }
            return true;
        }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public enum Estado {
        JUGANDO,
        GANO,
        PERDIO,
        PAUSADO,
    }
}
