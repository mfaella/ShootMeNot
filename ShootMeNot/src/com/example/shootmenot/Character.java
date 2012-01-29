package com.example.shootmenot;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;

/*
 * Any character in the game, including bullets and the protagonist.
 */
public abstract class Character extends Sprite {
	private static Set<Character> characters = new HashSet<Character>();
	public static final int INDESTRUCTIBLE = Integer.MAX_VALUE;
	public static final int DIEONTOUCH = 0;
	
	protected GameContext context;
	private int damage, life, scoreValue;
	
	public Character(GameContext context, float x, float y, TextureRegion tr, int initialLife, int damage, int scoreValue) 
	{
		super(x, y, tr);
		this.context    = context;
		this.life       = initialLife;
		this.damage     = damage;
		this.scoreValue = scoreValue;
		
		characters.add(this);
	}
	
	
	/* Life points subtracted from another character, in case of collision.
	 * 
	 */
	public int getDamage() 
	{
		return damage;
	}
	
	/* Life points.
	 * Character dies when less than zero.
	 */
	public int getLife() 
	{
		return life;
	}

	/* Returns the amount of score points that the protagonist receives
	 * if it kills this character. It can be negative.
	 */
	public int getScoreValue()
	{
		return scoreValue;
	}
	
	/* Applies damage to this character.
	 * Returns true if the character dies as a consequence.
	 */
	public boolean applyDamage(int damage)
	{
		if (life != INDESTRUCTIBLE) 
			life -= damage;
		if (life<0)
			return true;
		return false;
	}
	
	public void destroy() 
	{ 
		//Log.d("SMN", "Destroying " + toString());
		characters.remove(this);
		detachSelf();
	}
	
	public String toString()
	{
		String temp = super.toString();
		return getClass().getSimpleName() + ":" + temp.substring(temp.length()-9);
	}
	
	public static Iterable<Character[]> allPairs()
	{
		final Object[] characterArray = characters.toArray();
		final int max_index = characterArray.length -1;
		
		// if there are fewer than two characters, return the empty iterable.
		if (max_index<1) return new HashSet<Character[]>();
						
		return new Iterable<Character[]>() {
			public Iterator<Character[]> iterator() {
				return new Iterator<Character[]>() {
					private int i = 0, j = 1;
					private boolean hasNextFlag = true;
					
					public boolean hasNext() {
						return hasNextFlag;
					}
					public Character[] next() {
						Character[] result = new Character[] {(Character) characterArray[i], (Character) characterArray[j]};
						if (j<max_index) {
							j++;
						} else if (i<max_index-1) { // increment i and reset j
							i++;
							j=i+1;
						} else { // finished
							hasNextFlag = false;
						}
						return result;
					}
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}
