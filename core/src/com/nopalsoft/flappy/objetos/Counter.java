package com.nopalsoft.flappy.objetos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Counter {
    public static float WIDTH = .1f;
    public static float HEIGHT = 1.85f;

    public static int STATE_NORMAL = 0;
    public static int STATE_REMOVE = 1;

    public int state; // Guarda el estado actual

    public static float SPEED_Y = Drop.SPEED_Y; // Velocidad de las gotas

    public Vector2 position;

    public Counter() {
        position = new Vector2();
        state = STATE_NORMAL;
    }

    public void update(Body body) {
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;
    }

}
