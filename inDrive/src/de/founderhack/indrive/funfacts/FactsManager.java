package de.founderhack.indrive.funfacts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class FactsManager {
	public ArrayList<Fact> registeredFact = new ArrayList<Fact>();
	private Random mRnd = new Random();
	
	private FactsManager() {
		registeredFact.add(new QuotesFact());
		registeredFact.add(new QuotesFact());
		registeredFact.add(new ZalandoFact("schuhe"));
		registeredFact.add(new ZalandoFact("elektronik"));
		registeredFact.add(new ZalandoFact("heimtextilien"));
		registeredFact.add(new ZalandoFact("schuhe"));
		registeredFact.add(new FuelConsumptionFact());
		JokeFact j = new JokeFact();
		registeredFact.add(j);
		registeredFact.add(j);
		registeredFact.add(j);
	}
	
	private static FactsManager mInstance;
	public static FactsManager getInstance() {
		if (mInstance == null) {
			mInstance = new FactsManager();
		}
		
		return mInstance;
	}
	
	public Fact getRandomFact() {
		Collections.shuffle(registeredFact, mRnd);
		
		for (Fact f : registeredFact) {
			if (f.ready()) return f;
		}
		return null;
	}
}
