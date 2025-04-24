package school.faang.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", length = 64, nullable = false, unique = true)
    private String title;

    @OneToMany(mappedBy = "country")
    private List<User> residents;

    @Override
    public String toString() {
        return "Country{" +
                "title='" + title + '\'' +
                '}';
    }
}
