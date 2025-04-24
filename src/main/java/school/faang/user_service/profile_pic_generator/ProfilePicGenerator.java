package school.faang.user_service.profile_pic_generator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import school.faang.user_service.entity.UserProfilePic;
import school.faang.user_service.entity.User;
import school.faang.user_service.service.MinIOService;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ProfilePicGenerator {
    @Value("${minio.bucketName}")
    private String bucket;
    @Value("${minio.picGeneratorUrl}")
    private String picGeneratorUrl;
    @Value("${minio.picType}")
    private String picType;
    private final MinIOService minIOService;

    public UserProfilePic generateProfilePic(User user){
        String seed = user.getName().toUpperCase();
        String profilePicName = seed + user.getId();
        String generatedPicUrl = picGeneratorUrl + seed + picType;

        String pic = getPicFromUrl(generatedPicUrl);
        MultipartFile profilePicFile = convertToMultipartFile(pic, profilePicName);

        minIOService.uploadToMinioBucket(profilePicFile, bucket);

        URL storedPicUrl = minIOService.getUrl(profilePicFile, bucket);

        return  UserProfilePic.builder()
                .url(storedPicUrl)
                .pic(generatedPicUrl)
                .build();
    }

    private String getPicFromUrl(String picUrl){
        StringBuilder picBuilder = new StringBuilder();
        try (InputStream inputStream = new URL(picUrl).openStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(inputStreamReader)){
            String line;
            while ((line = reader.readLine()) != null){
                picBuilder.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return picBuilder.toString();
    }

    private MultipartFile convertToMultipartFile(String pic, String fileName){
        byte[] bytes = pic.getBytes(StandardCharsets.UTF_8);
        return new ByteArrayMultipartFile(bytes, fileName + ".svg", fileName, "image/svg+xml");
    }
}