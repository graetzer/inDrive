package de.founderhack.indrive.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import de.founderhack.indrive.MainActivity;
import de.founderhack.indrive.R;
import de.founderhack.indrive.funfacts.Fact;
import de.founderhack.indrive.funfacts.FactsManager;
import de.founderhack.indrive.stuff.DesignHelper;

public class GreenscreenFragment extends Fragment {

	private static final int FUNFACT_DURATION = 13000;
	
	private TextView title;
	private ImageView image;
	private Animation in, out;
	private FactsManager mFactsManager;
	private Handler mHandler;
	private Fact oldFact;
	
	private Runnable getNewFactRunnable = new Runnable() {
		
		@Override
		public void run() {
			pushNewFact(mFactsManager.getRandomFact());
		}
		
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mFactsManager = FactsManager.getInstance();
		
		View view = inflater.inflate(R.layout.fragment_greenscreen, container, false);
		title = (TextView) view.findViewById(R.id.textViewFragmentGreenscreenText1);
		image = (ImageView) view.findViewById(R.id.imageViewFragmentGreenscreen);
		title.setTypeface(DesignHelper.getRobotoThin(getActivity()));
		
		in = AnimationUtils.loadAnimation(getActivity(), R.anim.in);
		out = AnimationUtils.loadAnimation(getActivity(), R.anim.out);
		
		title.startAnimation(in);
		
		mHandler = new Handler();
		mHandler.postDelayed(getNewFactRunnable, FUNFACT_DURATION);
		((MainActivity)getActivity()).mTts.speak("Berechne Informationsfluss...", TextToSpeech.QUEUE_FLUSH, null);
		return view;
	}
	
	private void pushNewFact(final Fact fact){
		if(oldFact != null){
			oldFact.onDestroy();
		}
		oldFact = fact;
		fact.onActive();
		
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
				mHandler.postDelayed(getNewFactRunnable, FUNFACT_DURATION);
				setFunFact(fact);
				title.startAnimation(in);
			}
		});
		
		title.startAnimation(out);
	}
	
	@Override
	public void onPause() {
		mHandler.removeCallbacks(getNewFactRunnable);
		super.onPause();
	}
	
	private void setFunFact(Fact tmp){
		title.setText(tmp.getFact());
		((MainActivity)getActivity()).mTts.speak(tmp.getFact(), TextToSpeech.QUEUE_FLUSH, null);
		
		if(tmp.getIcon() != null){
			image.setImageDrawable(tmp.getIcon());
		}else if(tmp.getIconResource() != 0){
			image.setImageResource(tmp.getIconResource());
		}else{
			image.setImageDrawable(null);
		}
	}

}
