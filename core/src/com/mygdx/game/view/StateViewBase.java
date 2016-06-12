package com.mygdx.game.view;

import com.mygdx.game.MyGdxGame;

public class StateViewBase  {
	protected MyGdxGame game;
	protected float stateTime;
	protected final float FRAME = 0.033f;
	
	public StateViewBase(MyGdxGame game){
		this.game = game;
		stateTime = 0;
	}

}
