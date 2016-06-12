package com.mygdx.wigets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyButton {
    private Sprite spNormal;
    private Sprite spPressed;
    private String content;
    private Boolean isChecked = false;
    private OnClickListener listener;
    private int x;
    private int y;
    private float width;
    private float height;
   
    public void setPostion(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    public void setSize(float width, float height) {
    	this.width = width;
    	this.height = height;
    }
    
    public void draw(SpriteBatch batch) {
    	Sprite now;
    	if (this.isChecked) {
    		now = this.spPressed;
    	} else {
    		now = this.spNormal;
    	}
    	
		this.setSize(now.getWidth(), now.getHeight());
    	now.setPosition(x, y);
    	now.draw(batch);
    }
    
    public void hitTest(int tx, int ty) {
    	if (this.listener == null) return;
    	if (tx - x > 0 && tx - x < width) {
    		if (ty - y > 0 && ty - y < height) {
    			this.listener.excute();
    		}
    	}
    }
    
	public Sprite getSpNormal() {
		return spNormal;
	}
	public void setSpNormal(Sprite spNormal) {
		this.spNormal = spNormal;
	}
	public Sprite getSpPressed() {
		return spPressed;
	}
	public void setSpPressed(Sprite spPressed) {
		this.spPressed = spPressed;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public OnClickListener getListener() {
		return listener;
	}
	public void setListener(OnClickListener listener) {
		this.listener = listener;
	}
	public Boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public void dispose() {
		this.spNormal.getTexture().dispose();
		this.spPressed.getTexture().dispose();
	}
   
}
