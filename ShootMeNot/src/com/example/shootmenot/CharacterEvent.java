package com.example.shootmenot;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.anddev.andengine.entity.modifier.EntityModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class CharacterEvent implements LevelEvent {
	
	private Class<? extends Character> characterClass; 
	private float x0, y0; // initial coordinates
	private IEntityModifier story;      // path  
	
	public CharacterEvent(Class<? extends Character> characterClass, float x0, float y0, IEntityModifier story)
	{
		this.characterClass = characterClass;
		this.x0 = x0;
		this.y0 = y0;
		this.story = story;
	}
	
	public void play(GameContext context)
	{
		Character c = null;
		Constructor<? extends Character> constr;
		
		try {
			constr = characterClass.getConstructor(GameContext.class, float.class, float.class);
		} catch (NoSuchMethodException e) {
			throw new MissingConstructorException(characterClass);
		}
		try {
			c = constr.newInstance(context, x0, y0);
		} catch (Exception e) {
			throw new WrongConstructorException(characterClass, e);
		};
		story.addModifierListener(Utils.detacherListenerFactory(context.activity, context.scene, c));
		c.registerEntityModifier(story);
		context.scene.attachChild(c);
	}
	
	public static class MissingConstructorException extends RuntimeException {
		public MissingConstructorException(Class<?> c) 
		{
			super("Class " + c.getName() + " should have a constructor accepting two floats (initial coordinates).");
		}
	}
	public static class WrongConstructorException extends RuntimeException {
		public WrongConstructorException(Class<?> c, Exception e) 
		{
			super("Error while reflectively calling the constructor of class " + c.getName() + ". Actual exception was:" + e);
		}
	}
}
