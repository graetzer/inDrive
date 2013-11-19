package de.founderhack.indrive;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.founderhack.indrive.stuff.DesignHelper;

public class AchievementAdapter extends ArrayAdapter<Achievement> {
	
	private ArrayList<Achievement> items;
	private LayoutInflater inflater;
	
	class ViewHolder{
		TextView title, description;
		ImageView icon;
	}

	public AchievementAdapter(Context context, int resource, ArrayList<Achievement> achievements) {
		super(context, resource, achievements);
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.items = achievements;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		
		Achievement current = items.get(position);
		
		if(convertView == null){
			vh = new ViewHolder();
			
			convertView = inflater.inflate(R.layout.row_achievement, null);
			vh.title = (TextView) convertView.findViewById(R.id.textViewAchievementTitle);
			vh.title.setTypeface(DesignHelper.getRobotoThin(getContext()));
			vh.description = (TextView) convertView.findViewById(R.id.textViewAchievementDescription);
			vh.description.setTypeface(DesignHelper.getRobotoRegular(getContext()));
			vh.icon = (ImageView) convertView.findViewById(R.id.imageViewAchievement);
			
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		
		vh.title.setText(current.title);
		vh.description.setText(current.description);
		
		switch(current.badgeType){
		case Achievement.BADGE_POLICE:
			vh.icon.setImageResource(R.drawable.badge_police); break;
		case Achievement.BADGE_TRAFFIC:
			vh.icon.setImageResource(R.drawable.badge_traffic); break;
		case Achievement.BADGE_HUMAN:
			vh.icon.setImageResource(R.drawable.badge_man); break;
		case Achievement.BADGE_CASH:
			vh.icon.setImageResource(R.drawable.badge_cash); break;
		}
		
		return convertView;
	}

}
