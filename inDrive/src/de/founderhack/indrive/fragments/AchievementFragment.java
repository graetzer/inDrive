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

	private ArrayList<Achievement> achievements = new ArrayList<Achievement>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fillAchievements();
		
		View view = inflater.inflate(R.layout.fragment_achievements, container, false);
		ListView list = (ListView) view.findViewById(R.id.listViewFragmentAchievements);
		list.setAdapter(new AchievementAdapter(getActivity(), 0, achievements));
		
		return view;
	}
	
	private void fillAchievements(){
		Achievement tmp = new Achievement();
		tmp.title = "Autobahnraser";
		tmp.description = "Du bist länger als 5 Minuten über 190 km/h gefahren!";
		tmp.badgeType = Achievement.BADGE_POLICE;
		achievements.add(tmp);
		
		Achievement tmp2 = new Achievement();
		tmp2.title = "Camper";
		tmp2.description = "Du hast 5 Stunden in einer Woche im Stau gestanden.";
		tmp2.badgeType = Achievement.BADGE_TRAFFIC;
		achievements.add(tmp2);
	}
}
