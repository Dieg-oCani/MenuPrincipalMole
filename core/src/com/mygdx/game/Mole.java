package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kevinalvarez on 15/09/16.
 */
public class Mole {

    private Sprite sprite;
    private float alturaActual; //[0-altura]
    private float velocidad = 0.7f; //incremento para recortar la imagen
    private float alturaOriginal;
    private float yOriginal;

    //Estado
    private Estado estado = Estado.BAJANDO;
    //Acumulador de tiempo
    private float tiempoOculto;
    //Se salvó (se ocultó y no le pegué)
    private boolean salvado = false;




    public Mole(Texture textura){
        sprite = new Sprite(textura);
        alturaOriginal = sprite.getHeight();
        alturaActual = alturaOriginal;


    }

    public Mole(Texture textura, float x, float y) {
        this(textura);
        sprite.setPosition(x,y);
        yOriginal = y;
    }


    public void draw(SpriteBatch batch){
        actualizar();
        sprite.draw(batch);


    }
        //deben de ser enteros aunque pida float ya que son pixeles

    private void actualizar() {
        switch (estado){
            case BAJANDO:
                alturaActual -= velocidad;
                if(alturaActual<=0){
                    //se me fue...
                    salvado = true;
                    alturaActual = 0;
                    tiempoOculto = (float)(Math.random()*2+0.5);
                    estado = Estado.OCULTO;
                }
                break;
            case SUBIENDO:
                alturaActual += velocidad;
                if(alturaActual>=alturaOriginal){
                    alturaActual = alturaOriginal;
                    estado = Estado.BAJANDO;
                }
                break;
            case OCULTO:
                tiempoOculto -= Gdx.graphics.getDeltaTime();
                if (tiempoOculto<=0){
                    estado = Estado.SUBIENDO;
                }
                break;
            case MURIENDO:
                sprite.setY(sprite.getY()+10);
                //sprite.setRotation(sprite.getRotation()+30);
                sprite.setScale(sprite.getScaleX()*0.95f,1);
                if (sprite.getY()>= 800){
                    tiempoOculto = (float)(0.5+Math.random()*4);
                    sprite.setY(yOriginal);
                    alturaActual = 0;
                    sprite.setRotation(0);
                    estado = Estado.OCULTO;
                }
                break;

        }

        sprite.setRegion(0,0,(int)sprite.getWidth(),(int)alturaActual);
        sprite.setSize(sprite.getWidth(),alturaActual);

    }

    public boolean contiene(float x, float y) {
        return sprite.getBoundingRectangle().contains(x,y);
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
        if (estado == Estado.MURIENDO) {


        }

    }

    public Estado getEstado() {
        return estado;
    }

    public boolean isSalvado() {
        return salvado;
    }

    public void setSalvado(boolean salvado) {
        this.salvado = salvado;
    }

    //Estados
    public enum Estado{
        BAJANDO,
        SUBIENDO,
        OCULTO,
        MURIENDO
    }

}
