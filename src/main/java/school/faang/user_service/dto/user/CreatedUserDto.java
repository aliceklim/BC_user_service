package school.faang.user_service.dto.user;

import lombok.Builder;

@Builder
public record CreatedUserDto(String username,
                             String name,
                             String surname,
                             Long id,
                             String email,
                             String profilePic){
}
