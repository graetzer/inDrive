package de.founderhack.indrive.fragments;

import de.founderhack.indrive.R;
import de.founderhack.indrive.stuff.DesignHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GreenscreenFragment extends Fragment {

	private TextView title;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_greenscreen, container, false);
		title = (TextView) view.findViewById(R.id.textViewFragmentGreenscreenText1);
		title.setTypeface(DesignHelper.getRobotoThin(getActivity()));
		
		return view;
	}
	
	public void pushNewFact(String fact){
		
	}

}
