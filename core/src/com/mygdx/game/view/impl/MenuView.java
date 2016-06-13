package com.mygdx.game.view.impl;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.fsm.MainMenuFSM;
import com.mygdx.game.view.IStateView;
import com.mygdx.game.view.StateViewBase;
import com.mygdx.res.BackGroundRes;
import com.mygdx.wigets.MyButton;
import com.mygdx.wigets.OnClickListener;

public class MenuView extends StateViewBase implements IStateView,InputProcessor {
	private Sprite bg;
	private MyButton mbStart;
    private List<MyButton> buttons;
    private MainMenuFSM mainmenuFSM;

	public MenuView(MyGdxGame game) {
		super(game);
		this.onEnter();
	}

	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(SpriteBatch batch) {
		bg.draw(batch);
		for(MyButton bt: buttons) {
			bt.draw(batch);
		}
	}

	@Override
	public void onEnter() {
		bg = BackGroundRes.VALLEY.getSprit();
		buttons = new ArrayList<MyButton>();
		
        mbStart = new MyButton();
        Texture txTep = new Texture("widgets/btn_startgame.png");
        mbStart.setSpNormal(new Sprite(txTep));
        mbStart.setSpPressed(new Sprite(txTep));
        mbStart.setPostion(250, 350);
        mbStart.setListener(new OnClickListener(){
			@Override
			public void excute() {

				game.viewStack.pop();
				game.viewStack.push(new MapViewBase(game));
			}
        });
        
        buttons.add(mbStart);
        
        mainmenuFSM = new MainMenuFSM();
         
       Gdx.input.setInputProcessor(this);
	}

	@Override
	public void onExit() {
		for(MyButton bt: buttons) {
			bt.dispose();
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		mainmenuFSM.keydown(keycode);
		return false;
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
		int x = screenX;
		int y = Gdx.graphics.getHeight() - screenY;
		for(MyButton bt: buttons) {
			bt.hitTest(x, y);
		}
		
		return true;
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
