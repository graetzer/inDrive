package de.founderhack.indrive.fragments;

import java.util.ArrayList;

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
	private ArrayList<Achievement> ac = new ArrayList<Achievement>(); 
	private AchievementAdapter mAchievementAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.fragment_achievements, container, false);
		ListView list = (ListView) view.findViewById(R.id.listViewFragmentAchievements);
		mAchievementAdapter = new AchievementAdapter(getActivity(), 0, ac);
		list.setAdapter(mAchievementAdapter);
		
		return view;
	}
	
	public void add(Achievement aa) {
		ac.add(aa);
		if (mAchievementAdapter != null)
			mAchievementAdapter.notifyDataSetChanged();
	}
	
}
