package de.founderhack.indrive.stuff;

import android.content.Context;
import android.graphics.Typeface;

public class DesignHelper {

	public static Typeface getRobotoThin(Context ctx) {
		return Typeface.createFromAsset(ctx.getAssets(), "fonts/roboto_thin.ttf");
	}
	
	public static Typeface getRobotoRegular(Context ctx) {
		return Typeface.createFromAsset(ctx.getAssets(), "fonts/roboto_regular.ttf");
	}
	
}
