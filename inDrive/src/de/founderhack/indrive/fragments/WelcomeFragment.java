package de.founderhack.indrive.fragments;

import de.founderhack.indrive.R;
import de.founderhack.indrive.stuff.DesignHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WelcomeFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myBeautifulView = inflater.inflate(R.layout.fragment_welcome, container, false);
		TextView title = (TextView) myBeautifulView.findViewById(R.id.textViewFragmentWelcome);
		title.setTypeface(DesignHelper.getRobotoThin(getActivity()));
		
		return myBeautifulView;
	}

}
