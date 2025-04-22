package school.faang.user_service.client;

import io.minio.MinioClient;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RequiredArgsConstructor
public class MinIOClient {
    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.user}")
    private String user;
    @Value("${minio.password}")
    private String password;

    @Bean
    public MinioClient getClient(){
        return MinioClient.builder()
                        .endpoint(endpoint)
                        .credentials(user, password)
                        .build();
    }
}