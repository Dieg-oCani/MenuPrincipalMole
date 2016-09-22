package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kevinalvarez on 15/09/16.
 */
public class Fondo {

    private Sprite sprite;
    public Fondo(Texture textura){
        sprite = new Sprite(textura);

    }
    public void draw(SpriteBatch batch){
        sprite.draw(batch);


    }
}
