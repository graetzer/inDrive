package de.founderhack.indrive.fragments;

import java.util.ArrayList;

import de.founderhack.indrive.R;
import de.founderhack.indrive.achivements.Achievement;
import de.founderhack.indrive.stuff.DesignHelper;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
		if (ac.contains(aa)) return;
		
		ac.add(aa);
		if (mAchievementAdapter != null) {
			mAchievementAdapter.notifyDataSetChanged();
		}
	}
	
	private static class AchievementAdapter extends ArrayAdapter<Achievement> {
		
		private ArrayList<Achievement> items;
		private LayoutInflater inflater;

		public AchievementAdapter(Context context, int resource, ArrayList<Achievement> achievements) {
			super(context, resource, achievements);
			
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.items = achievements;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			Achievement current = items.get(position);
			if(convertView == null){
				convertView = inflater.inflate(R.layout.row_achievement, null);
			}
			
			TextView title = (TextView) convertView.findViewById(R.id.textViewAchievementTitle);
			title.setTypeface(DesignHelper.getRobotoThin(getContext()));
			TextView description = (TextView) convertView.findViewById(R.id.textViewAchievementDescription);
			description.setTypeface(DesignHelper.getRobotoRegular(getContext()));
			ImageView icon = (ImageView) convertView.findViewById(R.id.imageViewAchievement);
			
			title.setText(current.title);
			description.setText(current.description);
			
			switch(current.badgeType){
			case Achievement.BADGE_POLICE:
				icon.setImageResource(R.drawable.badge_police); break;
			case Achievement.BADGE_TRAFFIC:
				icon.setImageResource(R.drawable.badge_traffic); break;
			case Achievement.BADGE_HUMAN:
				icon.setImageResource(R.drawable.badge_man); break;
			case Achievement.BADGE_CASH:
				icon.setImageResource(R.drawable.badge_cash); break;
			}
			
			return convertView;
		}

	}
}
