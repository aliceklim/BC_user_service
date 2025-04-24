package school.faang.user_service.repository.goal;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.faang.user_service.entity.goal.GoalInvitation;

@Repository
public interface GoalInvitationRepository extends CrudRepository<GoalInvitation, UUID> {
    @Override
    boolean existsById(UUID id);
}