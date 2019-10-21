package general.database;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import general.model.Person;

public interface PersonRepositoryPageable extends PagingAndSortingRepository<Person,Long> {

	List<Person> findByLastName(String lastName);
	
}
