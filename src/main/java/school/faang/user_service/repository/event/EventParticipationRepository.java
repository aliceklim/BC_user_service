package school.faang.user_service.repository.event;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.faang.user_service.entity.User;

import java.util.List;

@Repository
public interface EventParticipationRepository extends JpaRepository<User, UUID> {

    @Query(nativeQuery = true, value = "INSERT INTO user_event (event_id, user_id) VALUES (:eventId, :userId")
    void register(UUID eventId, UUID userId);

    @Query(nativeQuery = true, value = "DELETE FROM user_event WHERE event_id = :eventId and user_id = :userId")
    void unregister(UUID eventId, UUID userId);

    @Query(nativeQuery = true, value = """
            SELECT u.* FROM user u
            JOIN user_event ue ON u.id = ue.user_id
            WHERE ue.event_id = :eventId
            """)
    List<User> findAllParticipantsByEventId(UUID eventId);

    @Query(nativeQuery = true, value = """
            SELECT COUNT(ue.id) FROM user_event ue
            WHERE ue.event_id = :eventId
            """)
    int countParticipants(UUID eventId);
}