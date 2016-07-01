package com.mygdx.component.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BlackView extends BaseView {
    private Sprite black;
    
    BlackView() {
    	Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Format.RGBA8888 );
    	pixmap.setColor(0, 0, 0, 1.0f );
    	pixmap.fill();
    	Texture pixmaptex = new Texture( pixmap );
    	pixmap.dispose();
        black = new Sprite(pixmaptex);
        black.setPosition(0, 0);	
    }
	
	@Override
	public void update(float elapsedTime) {
	    black.setAlpha(this.alpha);	
	}

	@Override
	public void draw(Batch batch) {
		black.draw(batch);
	}

	@Override
	public void dispose() {
	    this.black.getTexture().dispose();	
	}

}
