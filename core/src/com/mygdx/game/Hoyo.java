package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kevinalvarez on 15/09/16.
 */
public class Hoyo {

    private Sprite sprite;
    public Hoyo(Texture textura){
        sprite = new Sprite(textura);

    }

    public Hoyo(Texture textura, float x, float y) {
        this(textura);
        sprite.setPosition(x,y);
    }
    public void draw(SpriteBatch batch){
        sprite.draw(batch);


    }
}
