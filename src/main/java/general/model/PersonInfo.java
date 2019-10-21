package general.model;

import java.util.List;

public class PersonInfo {
	
	private long numberOfPeople;
	private List<Person> theOldestPeopleWithPhone;
	private Iterable<Person> sortedListAccordingToAge;

	public PersonInfo(long numberOfPeople, List<Person> theOldestPeopleWithPhone, Iterable<Person> sortedListAccordingToAge) {
		this.numberOfPeople = numberOfPeople;
		this.theOldestPeopleWithPhone = theOldestPeopleWithPhone;
		this.sortedListAccordingToAge = sortedListAccordingToAge;
	}
	
	public long getNumberOfPeople() {
		return numberOfPeople;
	}

	public List<Person> getTheOldestPeopleWithPhone() {
		return theOldestPeopleWithPhone;
	}

	public Iterable<Person> getSortedListAccordingToAge() {
		return sortedListAccordingToAge;
	}
}
