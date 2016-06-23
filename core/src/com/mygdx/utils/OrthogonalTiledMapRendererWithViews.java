package com.mygdx.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.component.view.BaseView;
import com.mygdx.model.Character;

public class OrthogonalTiledMapRendererWithViews extends OrthogonalTiledMapRenderer {
    private List<BaseView> views;
    private int drawSpritesAfterLayer = 2;

    public OrthogonalTiledMapRendererWithViews(TiledMap map) {
        super(map);
        views = new ArrayList<BaseView>();
    }

    public void addCharacter(BaseView chr){
    	views.add(chr);
    }

    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
        	
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer)layer);
                    currentLayer++;
                    if(currentLayer == drawSpritesAfterLayer){
                        for(BaseView chr : views) {
                        	chr.draw(this.getBatch());
                        }
                    }
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                        object.getProperties();
                    }
                }
            }
        }
        endRender();
    }
}
