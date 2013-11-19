package de.founderhack.indrive.fragments;

import de.founderhack.indrive.Achievement;
import de.founderhack.indrive.AchievementAdapter;
import de.founderhack.indrive.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AchievementFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.fragment_achievements, container, false);
		ListView list = (ListView) view.findViewById(R.id.listViewFragmentAchievements);
		list.setAdapter(new AchievementAdapter(getActivity(), 0, Achievement.achivements));
		
		return view;
	}
	
}
