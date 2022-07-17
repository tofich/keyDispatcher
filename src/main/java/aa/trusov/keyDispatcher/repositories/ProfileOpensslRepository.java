package aa.trusov.keyDispatcher.repositories;

import aa.trusov.keyDispatcher.entities.Pairkeys;
import aa.trusov.keyDispatcher.entities.ProfileOpenssl;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProfileOpensslRepository extends PagingAndSortingRepository<ProfileOpenssl, Long> {
}
