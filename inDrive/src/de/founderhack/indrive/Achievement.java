package de.founderhack.indrive;

import java.util.ArrayList;

public class Achievement {

	public static final int BADGE_POLICE = 10,
							BADGE_TRAFFIC = 20,
							BADGE_HUMAN = 30,
							BADGE_CASH = 40;
	
	public String title, description;
	public int badgeType;
	
	
	@SuppressWarnings("serial")
	public static ArrayList<Achievement> achivements = new ArrayList<Achievement>(){{
		
		Achievement tmp = new Achievement();
		tmp.title = "Autobahnraser";
		tmp.description = "Du bist länger als 5 Minuten über 190 km/h gefahren!";
		tmp.badgeType = Achievement.BADGE_POLICE;
		add(tmp);
		
		Achievement tmp2 = new Achievement();
		tmp2.title = "Camper";
		tmp2.description = "Du hast 5 Stunden in einer Woche im Stau gestanden.";
		tmp2.badgeType = Achievement.BADGE_TRAFFIC;
		add(tmp2);
		
		Achievement tmp3 = new Achievement();
		tmp3.title = "Sportler";
		tmp3.description = "Du hast Sonntags das Auto stehen lassen.";
		tmp3.badgeType = Achievement.BADGE_HUMAN;
		add(tmp3);
		
		Achievement tmp4 = new Achievement();
		tmp4.title = "Sparschwein";
		tmp4.description = "Du hast Dir durch sparsame Fahrweise ein paar schicke Sneakers erspart.";
		tmp4.badgeType = Achievement.BADGE_CASH;
		add(tmp4);
		
		Achievement tmp5 = new Achievement();
		tmp5.title = "Noob";
		tmp5.description = "Du hast erfolgreich diese App gestartet. Glückwunsch! Held!";
		tmp5.badgeType = Achievement.BADGE_POLICE;
		add(tmp5);
		
		Achievement tmp6 = new Achievement();
		tmp6.title = "Frühaufsteher";
		tmp6.description = "Wer fährt denn bitte um 5 Uhr Auto?!";
		tmp6.badgeType = Achievement.BADGE_HUMAN;
		add(tmp6);
		
		Achievement tmp7 = new Achievement();
		tmp7.title = "Tankpausen";
		tmp7.description = "Was machst du dauernd an der Tanke, Alkohol kaufen?";
		tmp7.badgeType = Achievement.BADGE_POLICE;
		add(tmp7);
	}};
}
