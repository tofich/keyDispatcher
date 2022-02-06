package aa.trusov.keyDispatcher.repositories;

import aa.trusov.keyDispatcher.entities.Pairkeys;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PairkeysRepository extends PagingAndSortingRepository<Pairkeys, Long> {

    public List<Pairkeys> findAllByOrderByIdDesc();

    public List<Pairkeys> findAllByValidityIsNull();
}
