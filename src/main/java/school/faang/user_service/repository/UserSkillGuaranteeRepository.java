package school.faang.user_service.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.faang.user_service.entity.UserSkillGuarantee;


@Repository
public interface UserSkillGuaranteeRepository extends JpaRepository<UserSkillGuarantee, UUID> {

    boolean existsByUserIdAndSkillIdAndGuarantorId(UUID userId, UUID skillId, UUID guarantorId);
}