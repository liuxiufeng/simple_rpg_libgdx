package com.mygdx.game.view.impl;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.component.controller.IKeyListener;
import com.mygdx.component.controller.KeyProcess;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.fsm.MainMenuFSM;
import com.mygdx.game.view.IStateView;
import com.mygdx.game.view.StateViewBase;
import com.mygdx.res.BackGroundRes;
import com.mygdx.wigets.MyButton;
import com.mygdx.wigets.OnClickListener;

public class MenuView extends StateViewBase {
	private Sprite bg;
	private MyButton mbStart;
    private List<MyButton> buttons;
    private MainMenuFSM mainmenuFSM;

	public MenuView() {
		this.onEnter();
	}

	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(SpriteBatch batch) {
		bg.draw(batch);
		for(MyButton bt : buttons) {
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

			}
        });
        
        buttons.add(mbStart);
        
        mainmenuFSM = new MainMenuFSM();
        
        this.addListener();
	}

	@Override
	public void onExit() {
		for(MyButton bt: buttons) {
			bt.dispose();
		}
		
	    bg.getTexture().dispose();
	}

	@Override
	public void addListener() {
	    this.addKeyListener(mainmenuFSM);	
	}
}
