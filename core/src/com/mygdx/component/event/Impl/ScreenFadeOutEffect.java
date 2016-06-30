package com.mygdx.component.event.Impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.command.impl.EventEndCommand;
import com.mygdx.game.command.impl.EventStartCommand;

public class ScreenFadeOutEffect extends EffectEventBase {
	private Sprite black;
    private float statetime;
    private float duration;
    
    public ScreenFadeOutEffect(float duration) {
    	this.duration = duration;
        this.statetime = 0;	
    	Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Format.RGBA8888 );
    	pixmap.setColor(0, 0, 0, 1.0f );
    	pixmap.fill();
    	Texture pixmaptex = new Texture( pixmap );
    	pixmap.dispose();
        black = new Sprite(pixmaptex);
        black.setPosition(0, 0);
    }
    
	@Override
	public void before() {
	   new EventStartCommand().execute();
	}

	@Override
	public void excute() {
	    this.statetime += Gdx.graphics.getDeltaTime();
	    black.setAlpha(1.0f * (statetime/ duration));
	    if (this.statetime > this.duration) {
	    	this.listener.callback(this);
	    	return;
	    }
	}

	@Override
	public void after() {
	   black.getTexture().dispose();
	   new EventEndCommand().execute();
	}

	@Override
	public void render(Batch batch) {
		black.draw(batch);
	}
}
