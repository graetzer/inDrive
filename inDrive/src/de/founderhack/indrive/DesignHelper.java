package de.founderhack.indrive;

import android.content.Context;
import android.graphics.Typeface;

public class DesignHelper {

	public static Typeface getRobotoThin(Context ctx) {
		return Typeface.createFromAsset(ctx.getAssets(), "fonts/roboto_thin.ttf");
	}
	
}
