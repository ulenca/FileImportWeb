package general.model;

import java.util.List;

public class PersonInfo {
	
	private long numberOfPeople;
	private List<Person> theOldestPeopleWithPhone;
	private List<Person> sortedListAccordingToAge;

	public PersonInfo(long numberOfPeople, List<Person> theOldestPeopleWithPhone, List<Person> sortedListAccordingToAge) {
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

	public List<Person> getSortedListAccordingToAge() {
		return sortedListAccordingToAge;
	}
}
