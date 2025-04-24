package school.faang.user_service.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.faang.user_service.entity.User;

import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface SubscriptionRepository extends CrudRepository<User, UUID> {

    @Query(nativeQuery = true, value = "insert into subscription (follower_id, followee_id) values (:followerId, :followeeId)")
    @Modifying
    void followUser(UUID followerId, UUID followeeId);

    @Query(nativeQuery = true, value = "delete from subscription where follower_id = :followerId and followee_id = :followeeId")
    @Modifying
    void unfollowUser(UUID followerId, UUID followeeId);

    @Query(nativeQuery = true, value = "select exists(select 1 from subscription where follower_id = :followerId and followee_id = :followeeId)")
    boolean existsByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId);

    @Query(nativeQuery = true, value = """
            select u.* from users as u
            join subscription as subs on u.id = subs.follower_id
            where subs.followee_id = :followeeId
            """)
    Stream<User> findByFolloweeId(UUID followeeId);

    @Query(nativeQuery = true, value = "select count(id) from subscription where followee_id = :followeeId")
    int findFollowersAmountByFolloweeId(UUID followeeId);

    @Query(nativeQuery = true, value = """
            select u.* from users as u
            join subscription as subs on u.id = subs.followee_id
            where subs.follower_id = :followerId
            """)
    Stream<User> findByFollowerId(UUID followerId);

    @Query(nativeQuery = true, value = "select count(id) from subscription where follower_id = :followerId")
    int findFolloweesAmountByFollowerId(UUID followerId);
}