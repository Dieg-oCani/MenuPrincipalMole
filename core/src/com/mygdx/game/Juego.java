package com.mygdx.game;

import com.badlogic.gdx.Game;

/**
 * Administrador de pantallas
 * Created by dc on 9/1/16.
 */

public class Juego extends Game
{

    @Override
    public void create() {
        setScreen(new MenuPrincipal(this));
    }
}
