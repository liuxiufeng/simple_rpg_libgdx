package com.mygdx.game.view.impl;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.MyGdxGame;
import com.mygdx.res.EventMapRes;

public class CorridorView extends MapViewBase{

	public CorridorView(MyGdxGame game) {
		super(game);
	}
	
	@Override
	public void onEnter() {
	    tiledMap = new TmxMapLoader().load("map/corridor1.tmx");
	    //eventMap = EventMapRes.getEventMap("room1");
	}
}
