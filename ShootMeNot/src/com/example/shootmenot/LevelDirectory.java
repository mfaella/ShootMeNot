package com.example.shootmenot;

public class LevelDirectory {
	
	private final int nLevels;
	private final Level[] levels;
	
	public LevelDirectory(GameContext context)
	{
		nLevels = 1;
		levels = new Level[nLevels];
		levels[0] = new Level1(context);
	}

	public int getNumberOfLevels() 
	{
		return nLevels;
	}
	
	public Level get(int i)
	{
		return levels[i];
	}
}
