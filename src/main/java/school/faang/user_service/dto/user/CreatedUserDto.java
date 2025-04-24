package school.faang.user_service.dto.user;

import lombok.Builder;
import java.util.UUID;

@Builder
public record CreatedUserDto(String username,
                             String name,
                             String surname,
                             UUID id,
                             String email,
                             String profilePic){
}
