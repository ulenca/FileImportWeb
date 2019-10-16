package general.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Person")
public class Person implements Comparable<Person>{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="date_of_birth")
	private LocalDate dateOfBirth;
	
	@Transient
	private int age;
	
	@Column(name="phone")
	private Integer phoneNumber;
	
	public Person() {
		
	}
	
	public Person(String firstName, String lastName, LocalDate dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.age = calculateAge();
	}
	
	public Person(String firstName, String lastName, LocalDate dateOfBirth, int phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber=phoneNumber;
		this.age = calculateAge();
	}
	
	private int calculateAge() {
		if (LocalDate.now().isBefore(dateOfBirth)) {
			throw new IllegalArgumentException("Wrong date! Person " + this.toString() + "has not been born yet!");
			}
		return LocalDate.now().getYear()-dateOfBirth.getYear();
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public int getAge() {
		return age;
	}
	
	@Override
	public int compareTo(Person otherPerson) {
			return Integer.compare(this.age, otherPerson.age);
	}
	
	@Override
	public String toString() {
		return "Imię: " + firstName + ", Nazwisko: " + lastName + ", Wiek: " + age + ", Telefon: " + phoneNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + phoneNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (age != other.age)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phoneNumber != other.phoneNumber)
			return false;
		return true;
	}

	
	
	
	
}
