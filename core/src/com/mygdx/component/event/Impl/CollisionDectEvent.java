package com.mygdx.component.event.Impl;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.component.event.ActionListener;
import com.mygdx.component.view.BaseView;
import com.mygdx.utils.MapUtils;
import com.mygdx.utils.RectUtils;

public class CollisionDectEvent extends EventBase {
    
    private BaseView view;
    private float tar_x;
    private float tar_y;
	
    public CollisionDectEvent(BaseView view, float tar_x, float tar_y, ActionListener listener) {
    	super(listener);
    	this.view = view;
    	this.tar_x = tar_x;
    	this.tar_y = tar_y;

    }
    
    @Override
	public void update() {
    	Rectangle chRect = new Rectangle();
		chRect.x = tar_x;
		chRect.y = tar_y;
		chRect.width = view.width;
		chRect.height = view.height;
	    
		for (MapObject obj : MapUtils.collisionObjecs) {
			if (obj instanceof RectangleMapObject) {
				Rectangle rect = ((RectangleMapObject) obj).getRectangle();
				if (RectUtils.isOverlap(chRect, rect)) {
					this.result = false;
				}
			}
		}	
		
		if (this.result) {
			for(String key : MapUtils.viewMap.keySet()) {
				BaseView view = MapUtils.viewMap.get(key);
				
				Rectangle viewRect = new Rectangle();
				viewRect.x = view.x;
				viewRect.y = view.y;
				viewRect.width = view.width;
				viewRect.height = view.height;
				
				if(RectUtils.isOverlap(chRect, viewRect)) {
					this.result = false;
				}
			}
		}
		
		this.listener.callback(this);
	}
}
