package com.example.shootmenot;

import java.util.LinkedList;
import java.util.List;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class Level {
	
	// ===========================================================
	// Fields
	// ===========================================================

	private List<LevelEntry> entries = new LinkedList<LevelEntry>();
	
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void addEvent(float delay, LevelEvent e)
	{
		entries.add(new LevelEntry(delay, e));
	}
	
	/*
	 * Execute the events contained in this level.
	 */
	public void play(final GameContext context)
	{
		for (final LevelEntry entry: entries) {
			// register a one-time delay
			context.scene.registerUpdateHandler(new TimerHandler(entry.delay, false, new ITimerCallback() {
				@Override
				public void onTimePassed(final TimerHandler pTimerHandler) {
					entry.event.play(context);
				}
			}));	
		}
	}

	// ===========================================================
	// Inner Classes
	// ===========================================================

	private static class LevelEntry {

		// ===========================================================
		// Fields
		// ===========================================================

		private float delay; // seconds
		private LevelEvent event;

		// ===========================================================
		// Constructors
		// ===========================================================

		public LevelEntry(float delay, LevelEvent event)
		{
			this.delay = delay;
			this.event = event;
		}
		
	}
}
