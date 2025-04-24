package school.faang.user_service.messaging.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import school.faang.user_service.entity.contact.PreferredContact;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProfileViewEvent {
    private UUID idVisitor;
    private UUID idVisited;
    private PreferredContact preferredContact;

    @Override
    public String toString() {
        return idVisitor + "\n" + idVisited;
    }
}
