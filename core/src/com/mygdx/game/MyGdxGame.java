package com.mygdx.game;

import java.util.Stack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.IStateView;
import com.mygdx.game.view.impl.LogoView;
import com.mygdx.utils.FontUtils;
import com.mygdx.utils.GlobalManager;
import com.mygdx.model.Character;
import com.mygdx.res.CharRes;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	public Stack<IStateView> viewStack;

	@Override
	public void create() {
		batch = new SpriteBatch();
		viewStack = new Stack<IStateView>();
		viewStack.push(new LogoView(this));
		GlobalManager.game = this;
		Character ch = new Character();
		CharRes.getMary(ch);
		GlobalManager.hero = ch;
		FontUtils.getFont();
	}

	@Override
	public void render() {
		IStateView view = viewStack.lastElement();
		view.update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		view.render(batch);
		batch.end();
	}
}
