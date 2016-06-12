package com.mygdx.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.model.Character;

public class OrthogonalTiledMapRendererWithCharacters extends OrthogonalTiledMapRenderer {
    private List<Character> chrs;
    private int drawSpritesAfterLayer = 2;

    public OrthogonalTiledMapRendererWithCharacters(TiledMap map) {
        super(map);
        chrs = new ArrayList<Character>();
    }

    public void addCharacter(Character chr){
    	chrs.add(chr);
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
                        for(Character chr : chrs) {
                        	this.getBatch().draw(chr.getFrame(), chr.x, chr.y);
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
