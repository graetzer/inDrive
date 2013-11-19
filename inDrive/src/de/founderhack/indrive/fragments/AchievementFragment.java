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
		achievements = new ArrayList<Achievement>();
		
		Achievement tmp = new Achievement();
		tmp.title = "Autobahnraser";
		tmp.description = "Du bist länger als 5 Minuten über 190 km/h gefahren!";
		tmp.badgeType = Achievement.BADGE_POLICE;
		if (Math.random()<=0.9) achievements.add(tmp);
		
		Achievement tmp2 = new Achievement();
		tmp2.title = "Camper";
		tmp2.description = "Du hast 5 Stunden in einer Woche im Stau gestanden.";
		tmp2.badgeType = Achievement.BADGE_TRAFFIC;
		if (Math.random()<=0.6) achievements.add(tmp2);
		
		Achievement tmp3 = new Achievement();
		tmp3.title = "Sportler";
		tmp3.description = "Du hast Sonntags das Auto stehen lassen.";
		tmp3.badgeType = Achievement.BADGE_HUMAN;
		if (Math.random()<=0.4) achievements.add(tmp3);
		
		Achievement tmp4 = new Achievement();
		tmp4.title = "Sparschwein";
		tmp4.description = "Du hast Dir durch sparsame Farweise ein paar Sneakers erspart.";
		tmp4.badgeType = Achievement.BADGE_CASH;
		if (Math.random()<=0.2) achievements.add(tmp4);
		
		Achievement tmp5 = new Achievement();
		tmp5.title = "Noob";
		tmp5.description = "Du hast erfolgreich diese App gestartet. Glückwunsch! Held!";
		tmp5.badgeType = Achievement.BADGE_POLICE;
		achievements.add(tmp5);
		
		Achievement tmp6 = new Achievement();
		tmp6.title = "Frühaufsteher";
		tmp6.description = "Wer fährt denn bitte um 5 Uhr Auto?!";
		tmp6.badgeType = Achievement.BADGE_HUMAN;
		if (Math.random()<=0.4) achievements.add(tmp6);
		
		Achievement tmp7 = new Achievement();
		tmp7.title = "Tankpausen";
		tmp7.description = "Was machst du dauernd an der Tanke, Alkohol kaufen?";
		tmp7.badgeType = Achievement.BADGE_POLICE;
		if (Math.random()<=0.2) achievements.add(tmp7);
	}
}
