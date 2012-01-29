package com.example.shootmenot;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.modifier.MoveXModifier;



public class Level1 extends Level {
	public Level1(GameContext context)
	{
		IEntityModifier leftToRight = new MoveXModifier(3, -50, context.camera.getWidth()+50);
		// note that x coordinate is irrelevant
		addEvent(5, new CharacterEvent(BasicEnemy.class, 0, 100, leftToRight.deepCopy()));
		addEvent(7, new CharacterEvent(BasicEnemy.class, 0, 100, leftToRight.deepCopy()));
		addEvent(9, new CharacterEvent(BasicEnemy.class, 0, 100, leftToRight.deepCopy()));
		
		IEntityModifier rightToLeft = new MoveXModifier(3, context.camera.getWidth()+50, -50);
		// note that x coordinate is irrelevant
		addEvent(15, new CharacterEvent(BasicEnemy.class, 0, 150, rightToLeft.deepCopy()));
		addEvent(17, new CharacterEvent(BasicEnemy.class, 0, 150, rightToLeft.deepCopy()));
		addEvent(19, new CharacterEvent(BasicEnemy.class, 0, 150, rightToLeft.deepCopy()));
		
		// note that x coordinate is irrelevant
		addEvent(27, new CharacterEvent(BasicEnemy.class, 0, 150, leftToRight.deepCopy()));
		addEvent(29, new CharacterEvent(BasicEnemy.class, 0, 150, leftToRight.deepCopy()));
		addEvent(31, new CharacterEvent(BasicEnemy.class, 0, 150, leftToRight.deepCopy()));
		
	}
}
