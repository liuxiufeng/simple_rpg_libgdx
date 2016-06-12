package com.mygdx.game.view.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.IStateView;
import com.mygdx.game.view.StateViewBase;
import com.mygdx.res.SoundRes;

public class LogoView extends StateViewBase implements IStateView {
    private String group;
	private BitmapFont font;
	private Sound sdMeow;
    
	public LogoView(MyGdxGame game) {
		super(game);
		onEnter();
	}

	@Override
	public void update(float elapsedTime) {
	    this.stateTime += elapsedTime;	
	}

	@Override
	public void render(SpriteBatch batch) {
	    int len = Math.min(group.length(), (int) (stateTime / this.FRAME));
	    font.draw(batch, group.substring(0, len), Gdx.graphics.getWidth()/2 - 50, Gdx.graphics.getHeight()/2 -5);
	    if (stateTime > (this.FRAME * group.length() + 0.5f)) {
	    	this.onExit();
	    }
	}

	@Override
	public void onEnter() {
       group = "Shadow  Team";
       font = new BitmapFont();
       sdMeow = SoundRes.MEOW.getSound();
       sdMeow.play(1.2f);
	}

	@Override
	public void onExit() {
	   font.dispose();
	   sdMeow.dispose();
	   this.game.viewStack.pop(); 
	   this.game.viewStack.push(new MenuView(game));
	}

}