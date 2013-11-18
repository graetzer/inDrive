package de.founderhack.indrive.funfacts;

import android.graphics.drawable.Drawable;

public interface Fact {
	
	/** Text to show*/
	public String getFact();
	
	/** Icon to show */
	public Drawable getIcon();
	
	/**  */
	public boolean ready();
	
	/** Disappeared, fetch new info */
	public void onDestroy();
	
	/** Will become active */
	public void onActive();
}
