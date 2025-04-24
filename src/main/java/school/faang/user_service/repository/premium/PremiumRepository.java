package school.faang.user_service.repository.premium;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.faang.user_service.entity.premium.Premium;

import java.util.UUID;

@Repository
public interface PremiumRepository extends CrudRepository<Premium, UUID> {

    boolean existsByUserId(UUID userId);
}
