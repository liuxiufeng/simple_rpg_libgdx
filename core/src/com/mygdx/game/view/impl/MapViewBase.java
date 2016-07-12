package com.mygdx.game.view.impl;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.component.view.BaseView;
import com.mygdx.game.view.StateViewBase;
import com.mygdx.model.Hero;
import com.mygdx.model.NPC;
import com.mygdx.res.Assert;
import com.mygdx.res.CharFactory;
import com.mygdx.res.EventMapRes;
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
	String mapid;
	JSONObject mapinfo;

	public MapViewBase(String mapid) {
		this.mapid = mapid;
		String json;
		try {
			json = new String(Gdx.files.internal(Assert.mapinfo).readBytes(), "UTF-8");
			mapinfo = JSON.parseObject(json);
			mapinfo = (JSONObject) mapinfo.getJSONObject(mapid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ch = GlobalManager.hero;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		views = new HashMap<String, BaseView>();

		this.onEnter();
		views.put("hero", ch);
		tiledMapRenderer = new OrthogonalTiledMapRendererWithViews(tiledMap);

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
		tiledMap = new TmxMapLoader().load(mapinfo.getString("tiledmap"));
		if (mapinfo.containsKey("eventmap")) {
			eventMap = EventMapRes.getEventMap(mapinfo.getString("eventmap"));
		}
		JSONObject jobj = mapinfo.getJSONObject("hero");
		ch.setCell(jobj.getIntValue("x"), jobj.getIntValue("y"));
		ch.setState(jobj.getString("state"));

		JSONArray npcArr = mapinfo.getJSONArray("npc");
		for (Object obj : npcArr) {
			JSONObject no = (JSONObject) obj;
			NPC npc = new NPC();
			CharFactory.getInstance().getChar(no.getString("name"), npc);
			npc.setCell(no.getIntValue("x"), no.getIntValue("y"));
			npc.setEventPath(no.getString("eventpath"));
			npc.setState(no.getString("state"));
			views.put(no.getString("name"), npc);
		}

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
