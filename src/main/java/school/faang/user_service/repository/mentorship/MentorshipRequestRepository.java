package school.faang.user_service.repository.mentorship;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.faang.user_service.entity.MentorshipRequest;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MentorshipRequestRepository extends CrudRepository<MentorshipRequest, UUID> {

    @Query(nativeQuery = true, value = """
            INSERT INTO mentorship_request (requester_id, receiver_id, description, status, created_at, updated_at)
            VALUES (?1, ?2, ?3, 0, NOW(), NOW())
            """)
    MentorshipRequest create(UUID requesterId, UUID receiverId, String description);

    @Query(nativeQuery = true, value = """
            SELECT * FROM mentorship_request
            WHERE requester_id = :requesterId AND receiver_id = :receiverId
            ORDER BY created_at DESC
            LIMIT 1
            """)
    Optional<MentorshipRequest> findLatestRequest(UUID requesterId, UUID receiverId);
}