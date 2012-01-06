package com.example.shootmenot;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public abstract class Character extends Sprite {
	protected GameContext context; 
		
	public Character(GameContext context, float x, float y, TextureRegion tr) 
	{
		super(x, y, tr);
		this.context = context;
	}
	
	public void destroy() 
	{ 
		// NOP
	}
}
