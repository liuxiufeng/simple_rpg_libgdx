package com.mygdx.game.view.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
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
	}

	Character ch;
	TiledMap tiledMap;
	OrthographicCamera camera;
	OrthogonalTiledMapRendererWithCharacters tiledMapRenderer;
	int eventMap[][];
	
	@Override
	public void update(float elapsedTime) {	
		ch.update(elapsedTime);
	    MapLayer objectLayer = tiledMap.getLayers().get("Collisions");
	    Rectangle chRect = new Rectangle();
	    chRect.x = ch.x;
	    chRect.y = ch.y;
	    chRect.width = ch.getWidth();
	    chRect.height = ch.getHeight();
		for (MapObject obj : objectLayer.getObjects()) {
			if (obj instanceof RectangleMapObject) {
				Rectangle rect = ((RectangleMapObject) obj).getRectangle();
			    switch (ch.state) {
			    case 11:
			    	if (this.overLape(rect, chRect)) {
			    		ch.x = (int) (rect.x + rect.width);
			    	}
			    	break;
			    case 21:
			    	if (this.overLape(rect, chRect)) {
			    		ch.x = (int)(rect.x - ch.getWidth()) ;
			    	}
			    	break;
			    case 31:
			    	if (this.overLape(rect, chRect)) {
			    		ch.y = (int)(rect.y + rect.height);
			    	}
			    	break;
			    case 41:
			    	if (this.overLape(rect, chRect)) {
			    		ch.y = (int)(rect.y - ch.getHeight());
			    	}
			    	break;
			    }	
			}
		}
	    
		camera.position.x = ch.x;
		camera.position.y = ch.y;
		
	}

	@Override
	public void render(SpriteBatch batch) {
		
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		triggerEvnt(EventManager.TYPE_AUTO);
	}

	@Override
	public void onEnter() {
		ch.y = 352;
	    ch.x = 224;
	    tiledMap = new TmxMapLoader().load("map/room1.tmx");
	    eventMap = EventMapRes.getEventMap("room1");
	}

	@Override
	public void onExit() {
	    tiledMap.dispose();	
	}
	
	private boolean overLape(Rectangle rectA, Rectangle rectB ) {
	   float minx = Math.max(rectA.x, rectB.x);
	   float miny = Math.max(rectA.y, rectB.y);
	   float maxx = Math.min(rectA.x + rectA.width, rectB.x + rectB.width);
	   float maxy = Math.min(rectA.y + rectA.height, rectB.y + rectB.height);
	   
	   if (minx >= maxx || miny >= maxy) {
		   return false;
	   } else {
		   return true;
	   }
	}
	
	private void triggerEvnt(int type) {
		if (eventMap == null) {
			return;
		}
		int x = (int) (ch.x / Config.CELLWIDTH);
		int y = (int) (ch.y / Config.CELLWIDTH);
		int eventcode = eventMap[y][x];
		if(eventcode == 0) {
			return;
		}
		
		switch(type) {
		case EventManager.TYPE_KEY:
			if (eventcode /100 != 1) {
				return;
			} 
			break;
		case EventManager.TYPE_AUTO:
			if (eventcode / 100 != 2) {
				return;
			}
			eventMap[y][x] = 0;
			break;
		}
		EventManager.excuteEvent(eventcode);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.Z :
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
