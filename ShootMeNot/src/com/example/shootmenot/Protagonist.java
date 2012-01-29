package com.example.shootmenot;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

/*
 * The character controlled by the player.
 * This is a singleton class.
 */
public class Protagonist extends Character {

	private static final int LIFE = 300;
	private static final int DAMAGE = 30; // damage inflicted on collision
	private static final int SCOREVALUE = 0; // value for suicide?
		
	private static Protagonist it;
	private static TextureRegion tr;

	private int score;
	private int maxLife;
	private float shootDelay; // seconds between shots
	
	private Protagonist(GameContext context, TextureRegion tr)
	{
		super(context, 200, 400, tr, LIFE, DAMAGE, SCOREVALUE);
		
		maxLife = LIFE;
		shootDelay = 0.4f;
		
		setScale(0.45f);
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
		
		// a Time Handler for spawning bullets, periodically
		spriteTimerHandler = new TimerHandler(shootDelay, true,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						new GoodBullet(context, Protagonist.this.getX()+Protagonist.this.getWidth()/2, Protagonist.this.getY());						
					}
				});
		context.engine.registerUpdateHandler(spriteTimerHandler);
	}
	
	public long getScore() { return score; }
	
	public void incrScore(long incr) {
		score += incr;
		if (score<0) score = 0;
	}
	
	public int getMaxLife() { return maxLife; }
	
	public void setMaxLife(int maxLife) { this.maxLife = maxLife; }
}
