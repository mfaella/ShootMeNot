package com.example.shootmenot;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class GameContext {
	public final BaseGameActivity activity;
	public final Camera camera;
	public final Engine engine;
	public final Scene scene;
	
	public GameContext(BaseGameActivity activity, Camera camera, Engine engine, Scene scene)
	{
		this.activity = activity;
		this.camera = camera;
		this.engine = engine;
		this.scene = scene;
	}
}
