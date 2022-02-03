package aa.trusov.keyDispatcher.repositories;

import aa.trusov.keyDispatcher.entities.Pairkeys;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PairkeysRepository extends PagingAndSortingRepository<Pairkeys, Long> {

}
