package com.mygdx.game;

import java.util.Stack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.component.controller.KeyProcess;
import com.mygdx.game.view.IStateView;
import com.mygdx.game.view.impl.LogoView;
import com.mygdx.utils.AssetManagerUtils;
import com.mygdx.utils.GlobalManager;
import com.mygdx.model.Character;
import com.mygdx.res.CharRes;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	private SpriteBatch batch;
	private BitmapFont font;
	private static Stack<IStateView> viewStack;

	@Override
	public void create() {
		batch = new SpriteBatch();
		viewStack = new Stack<IStateView>();
		viewStack.push(new LogoView());

		GlobalManager.game = this;
		Character ch = new Character();
		CharRes.getMary(ch);
		GlobalManager.hero = ch;
		font = new BitmapFont();
		AssetManagerUtils.getInstance().preLoad();

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		IStateView view = viewStack.lastElement();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		KeyProcess.process();

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
	}

	public static void switchState(final IStateView view) {
		IStateView preView = viewStack.pop();
		preView.onExit();

		viewStack.push(view);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (KeyProcess.isProcess) {

				}
				KeyProcess.clear();
				view.addListener();
			}

		}).start();
	}

	public static void addState(final IStateView view) {
		viewStack.push(view);
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (KeyProcess.isProcess) {

				}
				KeyProcess.clear();
				view.addListener();
			}

		}).start();
	}

	public static void removeState() {
		IStateView preView = viewStack.pop();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (KeyProcess.isProcess) {

				}
				KeyProcess.clear();
				viewStack.firstElement().addListener();
			}

		}).start();
		
		preView.onExit();
	}
	
	public static IStateView getView() {
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
