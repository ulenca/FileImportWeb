package general.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import general.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

}
