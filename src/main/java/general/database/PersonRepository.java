package general.database;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import general.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
	
	List<Person> findByLastName(String lastName);

}
