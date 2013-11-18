package de.founderhack.indrive.stuff;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerTitleStrip;
import android.util.AttributeSet;
import android.widget.TextView;

public class NiceTitleStrip extends PagerTitleStrip {

	public NiceTitleStrip(Context context) {
		super(context);
	}
	
	public NiceTitleStrip(Context context, AttributeSet set){
		super(context, set);
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		super.onLayout(arg0, arg1, arg2, arg3, arg4);
		
		Typeface roboto = DesignHelper.getRobotoThin(getContext());
        for (int i=0; i< this.getChildCount(); i++) {
            if (this.getChildAt(i) instanceof TextView) {
                ( (TextView) this.getChildAt(i)).setTypeface(roboto);
                ( (TextView) this.getChildAt(i)).setTextSize(45);
            }
        }
	}
	
	

}
