package de.founderhack.indrive.fragments;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import de.founderhack.indrive.MainActivity;
import de.founderhack.indrive.R;
import de.founderhack.indrive.stuff.DesignHelper;

public class GreenscreenFragment extends Fragment {

	private TextView title;
	private Animation in, out;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_greenscreen, container, false);
		title = (TextView) view.findViewById(R.id.textViewFragmentGreenscreenText1);
		title.setTypeface(DesignHelper.getRobotoThin(getActivity()));
		
		in = AnimationUtils.loadAnimation(getActivity(), R.anim.in);
		out = AnimationUtils.loadAnimation(getActivity(), R.anim.out);
		
		title.startAnimation(in);
		
		Button btn = (Button) view.findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pushNewFact("Für die gefahrene Strecke hättest Du auch 850 Gramm abnehmen können!");
			}
		});
		return view;
	}
	
	public void pushNewFact(final String fact){
		
		out.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				title.setText(fact);
				title.startAnimation(in);
				((MainActivity)getActivity()).mTts.speak(fact, TextToSpeech.QUEUE_FLUSH, null);
			}
		});
		
		title.startAnimation(out);
	}

}
