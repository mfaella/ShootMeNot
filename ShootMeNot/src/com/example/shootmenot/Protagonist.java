package com.example.shootmenot;

import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;

/*
 * The character controlled by the player.
 */
public class Protagonist extends Character {

	private static final int LIFE = 300;
	private static final int DAMAGE = 30; // damage inflicted on collision
	private static final int SCOREVALUE = 0; // value for suicide?
		
	private static final float KEEP_SPEED = 0.9f, CHANGE_SPEED = 2.0f, MAX_SPEED = 20.0f;
	private static Protagonist it;
	private static TextureRegion tr;

	public static void setTexture(TextureRegion tr)
	{
		Protagonist.tr = tr;
	}
	
	public static Protagonist factory(GameContext context) 
	{
		if (it==null) {
			it = new Protagonist(context);
		}
		return it;
	}
		
	private int score;
	private int maxLife;
	private float velX, velY;
	private float shootDelay; // seconds between shots
	
	public Protagonist(GameContext context)
	{
		super(context, 200, 400, tr, LIFE, DAMAGE, SCOREVALUE);
		
		maxLife = LIFE;
		shootDelay = 0.4f;
		
		setScale(0.45f);
		context.scene.attachChild(this);
		context.scene.registerTouchArea(this);
		context.scene.setTouchAreaBindingEnabled(true);
	
		context.scene.registerUpdateHandler(getProtagonistMovementHandler());
		startShooting();		
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
							     final float pTouchAreaLocalX,
							     final float pTouchAreaLocalY)
	{
		float centerX = getX() + getWidth() / 2,
			  centerY = getY() + getHeight() / 2;
		float deltaX = pSceneTouchEvent.getX() - centerX, 
			  deltaY = pSceneTouchEvent.getY() - centerY;
		velX += deltaX*CHANGE_SPEED;
		velY += deltaY*CHANGE_SPEED;
		
		if (velX>MAX_SPEED) velX = MAX_SPEED;
		else if (velX<-MAX_SPEED) velX = -MAX_SPEED;
		if (velY>MAX_SPEED) velY = MAX_SPEED;
		else if (velY<-MAX_SPEED) velY = -MAX_SPEED;
		
		// was: this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
		return true;
	}

	public IUpdateHandler getProtagonistMovementHandler()
	{
		return new IUpdateHandler() {
			@Override
			public void onUpdate(float secondsElapsed) {
				Log.d("SMN", "seconds: " + secondsElapsed);
				float posX = Protagonist.this.getX(),
					  posY = Protagonist.this.getY();
				
				Protagonist.this.setPosition(posX + velX*secondsElapsed, posY + velY*secondsElapsed);
				velX = velX*KEEP_SPEED;
				velY = velY*KEEP_SPEED;
			}

			@Override
			public void reset() {
				// NOP
			}
		};
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
