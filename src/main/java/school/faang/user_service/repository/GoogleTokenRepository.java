package school.faang.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.faang.user_service.entity.GoogleToken;
import school.faang.user_service.entity.User;

import java.util.UUID;

@Repository
public interface GoogleTokenRepository extends JpaRepository<GoogleToken, UUID> {

    GoogleToken findByUser(User user);

}
