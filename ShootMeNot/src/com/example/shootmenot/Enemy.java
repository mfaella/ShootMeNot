package com.example.shootmenot;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

/*
 * A character that kills the protagonist if colliding with it.
 */
public abstract class Enemy extends Character {
	private int life;
	private TimerHandler shootingHandler;
	
	
	public Enemy(GameContext context, float x, float y, TextureRegion tr, int initialLife, ShootEnum shootingPolicy, float shootDelay) 
	{
		super(context, x, y, tr);
		life = initialLife;
		startShooting();
	}
	
	@Override
	public void destroy()
	{
		context.engine.unregisterUpdateHandler(shootingHandler);
	}
	
	protected void startShooting()
	{
		float delay = 0.5f; // seconds

		// a Time Handler for spawning bullets, periodically
		shootingHandler = new TimerHandler(delay, true,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						new BadBullet(context, Enemy.this.getX()+Enemy.this.getWidth()/2, Enemy.this.getY());						
					}
				});

		context.engine.registerUpdateHandler(shootingHandler);
	}
	
	/*
	 * Possible shooting policies:
	 * STRAIGHT: shoots straight down
	 * AIM: shoots towards the current protagonist direction
	 * NONE: does not shoot
	 */
	public enum ShootEnum {
		STRAIGHT, AIM, NONE;
	}
}
