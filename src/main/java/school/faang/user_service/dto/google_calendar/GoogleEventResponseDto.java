package school.faang.user_service.dto.google_calendar;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GoogleEventResponseDto {
    private String message;
    private String link;
}
