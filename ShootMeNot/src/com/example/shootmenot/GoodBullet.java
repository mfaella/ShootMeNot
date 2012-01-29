package com.example.shootmenot;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;

public class GoodBullet extends Character {
	
	// Static stuff
	private static TextureRegion tr;
	private static final float SPEED = 400; // pixels per second 
	private static final int DAMAGE = 10; // inflicted damage (life points) 
	private static final int SCOREVALUE = 0; 
	
	public static void setTexture(TextureRegion tr) {
		GoodBullet.tr = tr;
	}
	
	/* A bullet which goes straight up */
	public GoodBullet(GameContext contextParam, float x0, float y0) {
		super(contextParam, x0, y0, tr, Character.DIEONTOUCH, DAMAGE, SCOREVALUE);
		setScale(0.8f);
		// float cHeight = context.camera.getHeight();
		float y1 = - this.getHeight(); // slightly beyond the top boundary
		float distance = y0 - y1;
		float duration = distance / SPEED;
		
		MoveModifier mod = new MoveModifier(duration, x0, x0, y0, y1, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) { }
			
			@Override
			public void onModifierFinished(final IModifier<IEntity> pModifier, final IEntity pItem)
			{
				// safely delete bullet when out of screen
				context.activity.runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						GoodBullet.this.destroy();
					}
				});
			}
		});
		this.registerEntityModifier(mod);
		context.scene.attachChild(this);
	}
	
	// TO DO
	public GoodBullet(GameContext context, float x0, float y0, float heading) {
		super(context, x0, y0, tr, Character.DIEONTOUCH, DAMAGE, SCOREVALUE);
		setScale(0.8f);
	}
	
	
}
