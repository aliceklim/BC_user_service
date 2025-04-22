package school.faang.user_service.dto.user;

import lombok.Builder;

@Builder
public record CreatedUserDto(String username,
                             String email){
}
