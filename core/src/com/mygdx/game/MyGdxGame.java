package com.mygdx.game;

import java.util.Stack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.component.controller.KeyProcess;
import com.mygdx.component.event.Impl.ScreenFadeInEffect;
import com.mygdx.game.view.StateViewBase;
import com.mygdx.game.view.impl.LogoView;
import com.mygdx.model.Hero;
import com.mygdx.res.CharRes;
import com.mygdx.utils.AssetManagerUtils;
import com.mygdx.utils.EventManager;
import com.mygdx.utils.GlobalManager;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	private SpriteBatch batch;
	private BitmapFont font;
	private static Stack<StateViewBase> viewStack;

	@Override
	public void create() {
		batch = new SpriteBatch();
		viewStack = new Stack<StateViewBase>();
		viewStack.push(new LogoView());

		GlobalManager.game = this;
		Hero ch = new Hero();
		CharRes.getMary(ch);
		GlobalManager.hero = ch;
		font = new BitmapFont();
		AssetManagerUtils.getInstance().preLoad();

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		StateViewBase view = viewStack.lastElement();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		KeyProcess.process(view.getKeyListeners());
		
		if (AssetManagerUtils.getInstance().update()) {
			view.update(Gdx.graphics.getDeltaTime());
			batch.begin();
			view.render(batch);
			batch.end();
		} else {
			batch.begin();
			font.draw(batch, "Loading......", Gdx.graphics.getWidth() / 2 - font.getSpaceWidth() * 6,
					Gdx.graphics.getHeight() / 2);
			batch.end();
		}
		EventManager.getInstance().process();
		batch.begin();
		EventManager.getInstance().render(batch);
		batch.end();
	}

	public static void switchState(final StateViewBase view) {
		StateViewBase preView = viewStack.pop();
		preView.onExit();

		viewStack.push(view);
		EventManager.getInstance().addEvents(new ScreenFadeInEffect(2.0f));
	}

	public static void addState(final StateViewBase view) {
		viewStack.push(view);
	}
	
	public static void removeState() {
		StateViewBase preView = viewStack.pop();
		preView.onExit();
	}
	
	public static StateViewBase getView() {
		return viewStack.firstElement();
	}

	@Override
	public boolean keyDown(int keycode) {
		KeyProcess.keyDown(keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		KeyProcess.keyUp(keycode);
		return true;
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
