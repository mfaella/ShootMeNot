package com.example.shootmenot;

import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class BasicEnemy extends Enemy {
	
	// ===========================================================
	// Static
	// ===========================================================
	
	private static TextureRegion tr;
	private static final float SPEED = 100; // pixels per second 
	private static final int LIFE = 20;    // life points 
	private static final int DAMAGE = 20;   // damage inflicted on collision 
	private static final int SCOREVALUE = 20; 
	
	public static void setTexture(TextureRegion tr) {
		BasicEnemy.tr = tr;
	}
	
	// ===========================================================
	// Instance 
	// ===========================================================
	
	public BasicEnemy(GameContext context, float x, float y) 
	{
		super(context, x, y, tr, LIFE, DAMAGE, SCOREVALUE, Enemy.ShootEnum.STRAIGHT, 0.8f);
	}
}