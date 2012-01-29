package com.example.shootmenot;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

/*
 * A character that may emit other characters (i.e., shoot bullets).
 */
public abstract class Enemy extends Character {
	private TimerHandler shootingHandler;
	private float shootDelay; // seconds
		
	public Enemy(GameContext context, float x, float y, TextureRegion tr, int initialLife, int damage, int scoreValue, ShootEnum shootingPolicy, float shootDelay) 
	{
		super(context, x, y, tr, initialLife, damage, scoreValue);
		this.shootDelay = shootDelay;
		startShooting();
	}
	
	@Override
	public void destroy()
	{
		super.destroy();
		context.engine.unregisterUpdateHandler(shootingHandler);
	}
	
	protected void startShooting()
	{
		// a Time Handler for spawning bullets, periodically
		shootingHandler = new TimerHandler(shootDelay, true,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						new BadBullet(context, Enemy.this.getX()+Enemy.this.getWidth()/2, Enemy.this.getY()+Enemy.this.getHeight());						
					}
				});

		context.engine.registerUpdateHandler(shootingHandler);
	}
	
	/*
	 * Possible shooting policies:
	 * STRAIGHT: shoots straight down
	 * AIM: shoots towards the current protagonist position
	 * NONE: does not shoot
	 */
	public enum ShootEnum {
		STRAIGHT, AIM, NONE;
	}
}
