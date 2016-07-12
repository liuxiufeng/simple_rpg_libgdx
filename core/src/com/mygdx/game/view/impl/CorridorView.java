package com.mygdx.game.view.impl;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.MyGdxGame;
import com.mygdx.res.EventMapRes;

public class CorridorView extends MapViewBase{

	public CorridorView(String mapid) {
		super(mapid);
	}
	
	@Override
	public void onEnter() {
	    tiledMap = new TmxMapLoader().load("map/corridor1.tmx");
	    this.addListener();
	}
}
