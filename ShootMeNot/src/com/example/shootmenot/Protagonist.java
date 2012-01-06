package com.example.shootmenot;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.app.Activity;

/*
 * The character controlled by the player.
 * This is a singleton class.
 */
public class Protagonist extends Sprite {

	private static Protagonist it;
	private static TextureRegion tr;
	private GameContext context;
	
	private Protagonist(GameContext context, TextureRegion tr)
	{
		super(200, 400, tr);
		this.context = context;
		
		setScale(0.5f);
		context.scene.attachChild(this);
		context.scene.registerTouchArea(this);
		context.scene.setTouchAreaBindingEnabled(true);
	
		startShooting();		
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
							     final float pTouchAreaLocalX,
							     final float pTouchAreaLocalY)
	{
		this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
		return true;
	}

	public static void setTexture(TextureRegion tr)
	{
		Protagonist.tr = tr;
	}
	
	public static Protagonist factory(GameContext context) 
	{
		if (it==null) {
			it = new Protagonist(context, tr);
		}
		return it;
	}
	
	private void startShooting() {
		TimerHandler spriteTimerHandler;
		float delay = 0.5f; // seconds

		// a Time Handler for spawning bullets, periodically
		spriteTimerHandler = new TimerHandler(delay, true,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						new GoodBullet(context, Protagonist.this.getX()+Protagonist.this.getWidth()/2, Protagonist.this.getY());						
					}
				});
		context.engine.registerUpdateHandler(spriteTimerHandler);
	}
}
