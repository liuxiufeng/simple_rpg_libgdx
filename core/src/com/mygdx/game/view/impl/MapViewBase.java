package com.mygdx.game.view.impl;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.component.controller.KeyProcess;
import com.mygdx.component.view.BaseView;
import com.mygdx.game.view.IStateView;
import com.mygdx.game.view.StateViewBase;
import com.mygdx.model.Character;
import com.mygdx.model.Hero;
import com.mygdx.model.NPC;
import com.mygdx.res.CharRes;
import com.mygdx.res.EventMapRes;
import com.mygdx.utils.EventManager;
import com.mygdx.utils.GlobalManager;
import com.mygdx.utils.MapUtils;
import com.mygdx.utils.OrthogonalTiledMapRendererWithViews;

public class MapViewBase extends StateViewBase {
	Hero ch;
	TiledMap tiledMap;
	OrthographicCamera camera;
	OrthogonalTiledMapRendererWithViews tiledMapRenderer;
	int eventMap[][];
	MapObjects collisionObjecs;
	HashMap<String, BaseView> views;

	public MapViewBase() {
		ch = GlobalManager.hero;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		views = new HashMap<String, BaseView>();

		this.onEnter();

		tiledMapRenderer = new OrthogonalTiledMapRendererWithViews(tiledMap);
		tiledMapRenderer.addCharacter(ch);

		for (String key : views.keySet()) {
			tiledMapRenderer.addCharacter(views.get(key));
		}

		MapLayer objectLayer = tiledMap.getLayers().get("Collisions");
		if (objectLayer != null) {
			collisionObjecs = objectLayer.getObjects();
		}
		
		MapUtils.collisionObjecs = collisionObjecs;
		MapUtils.viewMap = views;
		MapUtils.eventMap = eventMap;
	}

	@Override
	public void update(float elapsedTime) {

		ch.update(elapsedTime);

		camera.position.x = ch.x;
		camera.position.y = ch.y;

		for (String key : views.keySet()) {
			views.get(key).update(elapsedTime);
		}
	}

	@Override
	public void render(SpriteBatch batch) {

		camera.update();
		batch.end();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		batch.begin();
	}

	@Override
	public void onEnter() {
		//System.out.println("MapViewBase");
		ch.setCell(6, 11);
		tiledMap = new TmxMapLoader().load("map/room1.tmx");
		eventMap = EventMapRes.getEventMap("room1");
		
		NPC npc = new NPC();
		CharRes.getReisen(npc);
		npc.setCell(7, 3);
		npc.setEventPath("data/event/event02.json");

		views.put("reisen", npc);
		
		this.addListener();
	}

	@Override
	public void onExit() {
		tiledMap.dispose();
	}

	public NPC getNPC(String key) {
		return (NPC) views.get(key);
	}

	public int[][] getEVENTMAP() {
		return this.eventMap;
	}

	public MapObjects getMapObjects() {
		return this.collisionObjecs;
	}
	
	@Override
	public void addListener() {
		this.addKeyListener(ch);
	}
}
