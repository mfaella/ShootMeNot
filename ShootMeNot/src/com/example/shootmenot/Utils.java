package com.example.shootmenot;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.modifier.IModifier;

/*
 * You guessed, utility functions.
 */
public class Utils {
	// ===========================================================
	// Static
	// ===========================================================

	/*
	 * Returns an EntityModifierListener that destroys the character [cha] at the end of a modifier.  
	 */
	public static IEntityModifierListener detacherListenerFactory(final BaseGameActivity activity, final Scene scene, final Character cha)
	{
		return new IEntityModifierListener() {
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) { }
			
			@Override
			public void onModifierFinished(final IModifier<IEntity> pModifier, final IEntity pItem)
			{
				// safely delete bullet when out of screen
				activity.runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						cha.destroy();
					}
				});	
			}
		};
	}
}
