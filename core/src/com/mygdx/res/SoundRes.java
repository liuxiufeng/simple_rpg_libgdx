package com.mygdx.res;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public enum SoundRes {
    MEOW {
       public Sound getSound() {
    	   return (Sound) Gdx.audio.newSound(Gdx.files.internal("sound/Cat-noises.mp3"));
       } 
    };
    
    public abstract Sound getSound();
}
