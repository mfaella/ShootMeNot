package com.example.shootmenot;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

/**
 * (c) 2011 Marco Faella
 * 
 * @author Marco Faella
 * @since 30.12.2011
 */

// Per stampare:
// a2ps -1 -l 105 -T 2 -o prova.ps PathModifierExample.java

public class ShootMeNot extends GameActivity {
	
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 720;
	
	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;
	private Scene scene;
	
	private BitmapTextureAtlas mainAtlas;
	private BitmapTextureAtlas backgroundAtlas;
	private BitmapTextureAtlas mFontAtlas, mStrokeFontAtlas;

	private Font mFont;
	
	private TextureRegion mParallaxLayerBack;
	private TextureRegion mParallaxLayerMid;
	private TextureRegion mParallaxLayerFront;

	private Protagonist prot;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// public Scene getScene() { return scene; } 
	// public Engine getEngine() { return mEngine; } 
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public Engine onLoadEngine() {
		Log.d("SMN", "onLoadEngine");
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		Log.d("SMN", "onLoadResources");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		this.mainAtlas = new BitmapTextureAtlas(512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.backgroundAtlas = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		
		//this.mParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundAtlas, this, "parallax_background_layer_front.png", 0, 0);
		this.mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundAtlas, this, "ground.png", 0, 0);
		//this.mParallaxLayerMid = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundAtlas, this, "parallax_background_layer_mid.png", 0, 669);

		this.mEngine.getTextureManager().loadTextures(mainAtlas, backgroundAtlas);
		
		this.mFontAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mStrokeFontAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA); // currently unused
		this.mFont = new Font(mFontAtlas, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 20, true, Color.BLACK);
		this.mEngine.getTextureManager().loadTexture(mFontAtlas);
		this.mEngine.getFontManager().loadFont(mFont);
		
		// Pass textures to the appropriate classes
		Protagonist.setTexture(BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainAtlas, this, "tank.png", 0, 0));
		GoodBullet.setTexture(BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainAtlas, this, "particle_fire.png", 100, 0));
		BasicEnemy.setTexture(BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainAtlas, this, "onscreen_control_knob.png", 200, 0));
		BadBullet.setTexture(BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainAtlas, this, "radarpoint.png", 300, 0));
	}

	@Override
	public Scene onLoadScene() {
		Log.d("SMN", "onLoadScene");
		this.mEngine.registerUpdateHandler(new FPSLogger());

		scene = new Scene();
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 8);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0, 10.0f, new Sprite(0, 0, this.mParallaxLayerBack)));
		// autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, 80, this.mParallaxLayerMid)));
		// autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0, -10.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(), this.mParallaxLayerFront)));
		scene.setBackground(autoParallaxBackground);
	
		// start the game
		GameContext context = new GameContext(this, mCamera, mEngine, scene);
		prot = new Protagonist(context);
		LifeIndicator li = new LifeIndicator(context, prot);
		ScoreIndicator si = new ScoreIndicator(context, prot, mFont);
		scene.registerUpdateHandler(new CollisionHandler(prot));
		scene.registerUpdateHandler(new IndicatorsHandler(li, si));
		LevelDirectory dir = new LevelDirectory(context);
		Level l = dir.get(0);
		l.play(context);
		
		return scene;
	}

	@Override
	public void onLoadComplete() {
		Log.d("SMN", "onLoadComplete");
		
		// NOP
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("SMN", "Resuming!");
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
