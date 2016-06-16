package com.mygdx.game.view.impl;

import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.IStateView;
import com.mygdx.game.view.StateViewBase;
import com.mygdx.model.Character;
import com.mygdx.model.NPC;
import com.mygdx.res.CharRes;
import com.mygdx.res.EventMapRes;
import com.mygdx.utils.Config;
import com.mygdx.utils.EventManager;
import com.mygdx.utils.GlobalManager;
import com.mygdx.utils.OrthogonalTiledMapRendererWithCharacters;

public class MapViewBase extends StateViewBase implements IStateView, InputProcessor {
	Character ch;
	TiledMap tiledMap;
	OrthographicCamera camera;
	OrthogonalTiledMapRendererWithCharacters tiledMapRenderer;
	int eventMap[][];
	MapObjects collisionObjecs;
	HashMap<String, NPC> npcs;
	public boolean isEvnet;
	
	public MapViewBase(MyGdxGame game) {
		super(game);
		ch = GlobalManager.hero;
		Gdx.input.setInputProcessor(this);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		npcs = new HashMap<String, NPC>();
		this.isEvnet = false;

		this.onEnter();

		tiledMapRenderer = new OrthogonalTiledMapRendererWithCharacters(tiledMap);
		tiledMapRenderer.addCharacter(ch);
		
		for (String key : npcs.keySet()) {
			tiledMapRenderer.addCharacter(npcs.get(key));
		}
		
		MapLayer objectLayer = tiledMap.getLayers().get("Collisions");
		collisionObjecs = objectLayer.getObjects();
	}

	

	@Override
	public void update(float elapsedTime) {
		ch.update(elapsedTime);
		camera.position.x = ch.x;
		camera.position.y = ch.y;

		for (String key : npcs.keySet()) {
			NPC npc = npcs.get(key);
			npc.update(elapsedTime);
			if(ch.targetX == npc.cellX && ch.targetY == npc.cellY ) {
				ch.setCell(ch.cellX, ch.cellY);
				ch.setTarget(Character.NOTMOVE, Character.NOTMOVE);
			}
		}
		triggerEvnt(EventManager.TYPE_AUTO);
		if (!this.isEvnet) {
			ch.updateMove(collisionObjecs);
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
		ch.setCell(6, 11);
		tiledMap = new TmxMapLoader().load("map/room1.tmx");
		eventMap = EventMapRes.getEventMap("room1");
	    
		NPC npc = new NPC();
		CharRes.getReisen(npc);
		npc.setCell(7, 3);
		npc.setEventNum(202);
		
		npcs.put("reisen", npc);
	}

	@Override
	public void onExit() {
		tiledMap.dispose();
	}

	private void triggerEvnt(int type) {
		if (eventMap == null) {
			return;
		}
		
		if (ch.cellY > eventMap.length - 1 || ch.cellY < 0||
				ch.cellX > eventMap[0].length  - 1|| ch.cellX < 0 ) {
			return;
		}
		
		int eventcode = eventMap[ch.cellY][ch.cellX];
		if (eventcode == 0) {
			return;
		}

		switch (type) {
		case EventManager.TYPE_KEY:
			if (eventcode / 100 != 1) {
				return;
			}
			break;
		case EventManager.TYPE_AUTO:
			if (eventcode / 100 != 2) {
				return;
			}
			break;
		}
		EventManager.excuteEvent(eventcode, this);
	}
	
	public NPC getNPC(String key) {
		return npcs.get(key);
	}
	
	public int[][] getEVENTMAP() {
		return this.eventMap;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Config.KEYCONFIRM) {
			this.triggerEvnt(EventManager.TYPE_KEY);
			
			for(String key: npcs.keySet()) {
				NPC npc = npcs.get(key);
				npc.talked(ch.cellX, ch.cellY, ch.state);
			}
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
