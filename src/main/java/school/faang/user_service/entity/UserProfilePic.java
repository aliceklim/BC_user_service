package school.faang.user_service.entity;

import lombok.Builder;

import java.net.URL;

@Builder
public record UserProfilePic(URL url,
                             String pic) {
}
