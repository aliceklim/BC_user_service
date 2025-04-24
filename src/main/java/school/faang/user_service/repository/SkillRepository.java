package school.faang.user_service.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.faang.user_service.entity.Skill;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends CrudRepository<Skill, UUID> {

    boolean existsByTitle(String title);

    @Query(nativeQuery = true, value = "SELECT COUNT(id) FROM skill WHERE id IN (?1)")
    int countExisting(List<UUID> ids);

    @Query(nativeQuery = true, value = """
            SELECT s.* FROM skill s
            JOIN user_skill us ON us.skill_id = s.id
            WHERE us.user_id = ?1
            """)
    List<Skill> findAllByUserId(UUID userId);

    @Query(nativeQuery = true, value = """
            SELECT s.* FROM skill s
            JOIN skill_offer so ON so.skill_id = s.id
            JOIN recommendation r ON r.id = so.recommendation_id
            WHERE r.receiver_id = :userId
            """)
    List<Skill> findSkillsOfferedToUser(UUID userId);

    @Query(nativeQuery = true, value = """
            SELECT s.* FROM skill s
            JOIN user_skill us ON us.skill_id = :skillId AND us.user_id = :userId
            """)
    Optional<Skill> findUserSkill(UUID skillId, UUID userId);

    Optional<Skill> findByTitle(String title);

    @Query(nativeQuery = true, value = "INSERT INTO user_skill (skill_id, user_id) VALUES (:skillId, :userId)")
    @Modifying
    void assignSkillToUser(UUID skillId, UUID userId);

    @Query(nativeQuery = true, value = """
            SELECT s.* FROM skill s
            WHERE s.id IN (SELECT gs.skill_id FROM goal_skill gs
            WHERE gs.goal_id = ?1)
            """)
    List<Skill> findSkillsByGoalId(UUID goalId);
}