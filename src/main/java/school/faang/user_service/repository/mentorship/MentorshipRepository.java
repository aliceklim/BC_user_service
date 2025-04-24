package school.faang.user_service.repository.mentorship;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.faang.user_service.entity.User;

import java.util.UUID;

@Repository
public interface MentorshipRepository extends CrudRepository<User, UUID> {
}
