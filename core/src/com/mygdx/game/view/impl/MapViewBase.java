package com.mygdx.game.view.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.IStateView;
import com.mygdx.game.view.StateViewBase;
import com.mygdx.model.Character;
import com.mygdx.model.EventManager;
import com.mygdx.res.EventMapRes;
import com.mygdx.utils.Config;
import com.mygdx.utils.GlobalManager;
import com.mygdx.utils.OrthogonalTiledMapRendererWithCharacters;

public class MapViewBase extends StateViewBase implements IStateView, InputProcessor {
	public MapViewBase(MyGdxGame game) {
		super(game);
		ch = GlobalManager.hero;
		Gdx.input.setInputProcessor(this);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);

		this.onEnter();

		tiledMapRenderer = new OrthogonalTiledMapRendererWithCharacters(tiledMap);
		tiledMapRenderer.addCharacter(ch);
		MapLayer objectLayer = tiledMap.getLayers().get("Collisions");
		collisionObjecs = objectLayer.getObjects();
	}

	Character ch;
	TiledMap tiledMap;
	OrthographicCamera camera;
	OrthogonalTiledMapRendererWithCharacters tiledMapRenderer;
	int eventMap[][];
	MapObjects collisionObjecs;

	@Override
	public void update(float elapsedTime) {
		ch.update(elapsedTime, collisionObjecs);
		camera.position.x = ch.x;
		camera.position.y = ch.y;

	}

	@Override
	public void render(SpriteBatch batch) {

		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		// triggerEvnt(EventManager.TYPE_AUTO);
	}

	@Override
	public void onEnter() {
		ch.setCell(9, 6);
		tiledMap = new TmxMapLoader().load("map/room1.tmx");
		eventMap = EventMapRes.getEventMap("room1");
	}

	@Override
	public void onExit() {
		tiledMap.dispose();
	}

	private void triggerEvnt(int type) {
		if (eventMap == null) {
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
			eventMap[ch.cellY][ch.cellX] = 0;
			break;
		}
		EventManager.excuteEvent(eventcode);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.Z:
			this.triggerEvnt(EventManager.TYPE_KEY);
			break;
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
