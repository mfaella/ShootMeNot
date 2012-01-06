package com.example.shootmenot;

import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class BadBullet extends Character {
	private static TextureRegion tr;
	private static final float SPEED = 300; // pixels per second 
	
	public static void setTexture(TextureRegion tr) {
		BadBullet.tr = tr;
	}
	
	/* A bullet which goes straight down */
	public BadBullet(GameContext context, float x0, float y0) {
		super(context, x0, y0, tr);
		// TEMPORARY
		setScale(0.6f);
		float y1 = context.camera.getHeight() + this.getHeight();
		float distance = y1 - y0;
		float duration = distance / SPEED;
		
		MoveModifier mod = new MoveModifier(duration, x0, x0, y0, y1);
		this.registerEntityModifier(mod);
		context.scene.attachChild(this);
	}
	
	/*
	 * A bullet with arbitrary direction [heading].
	 * 
	 * TO BE DONE.
	 */
	public BadBullet(GameContext context, float x0, float y0, float heading) {
		super(context, x0, y0, tr);
	}
}
