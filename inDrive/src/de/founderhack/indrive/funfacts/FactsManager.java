package de.founderhack.indrive.funfacts;

import java.util.ArrayList;
import java.util.Random;


public class FactsManager {
	public ArrayList<Fact> registeredFact = new ArrayList<Fact>();
	private Random mRnd = new Random();
	
	private FactsManager() {
		registeredFact.add(new QuotesFact());
		registeredFact.add(new ZalandoFact("schuhe"));
		registeredFact.add(new ZalandoFact("elektronik"));
		registeredFact.add(new ZalandoFact("heimtextilien"));
		registeredFact.add(new ZalandoFact("schuhe"));
		registeredFact.add(new FuelConsumptionFact());
		registeredFact.add(new JokeFact());
	}
	
	private static FactsManager mInstance;
	public static FactsManager getInstance() {
		if (mInstance == null) {
			mInstance = new FactsManager();
		}
		
		return mInstance;
	}
	
	public Fact getRandomFact() {
		
		ArrayList<Fact> buff = new ArrayList<Fact>(registeredFact.size());
		for (Fact f : registeredFact) {
			if (f.ready()) buff.add(f);
		}
		if (buff.size() == 0) return null;
		
		return buff.get(mRnd.nextInt(buff.size()));
	}
}
