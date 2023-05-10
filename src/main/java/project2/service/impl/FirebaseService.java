package project2.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project2.service.IFirebaseService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseService implements IFirebaseService {

//    @Value("${firebase.pattern-url}")
//    private String patternUrl;

//    @Value("${firebase.projectId}")
//    private String projectId;
//
//    @Value("${firebase.bucketName}")
//    private String bucketName;

    private String projectId = "update-c1ca8";
    private String bucketName = "update-c1ca8.appspot.com";
//    private String patternUrl = "https://firebasestorage.googleapis.com/v0/b/project2-945cb.appspot.com/o/{0}?alt=media";
    private String patternUrl = "https://firebasestorage.googleapis.com/v0/b/update-c1ca8.appspot.com/o/{0}?alt=media";

    @PostConstruct
    public void init() throws IOException {
        ClassPathResource serviceAccount = new ClassPathResource("serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                .setProjectId(this.projectId)
                .setStorageBucket(this.bucketName)
                .build();

        FirebaseApp.initializeApp(options);
    }

    @Override
    public ResponseEntity downloadFile(String fileName) {
        return null;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Bucket bucket = StorageClient.getInstance().bucket();
            String name = generateFileName(file.getOriginalFilename());
            Blob blob = bucket.create(name, file.getBytes(), file.getContentType());
            return this.patternUrl.replace("{0}", blob.getName());
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }
    public String generateFileName(String originalFileName) {
        return UUID.randomUUID() + "." + getExtension(originalFileName);
    }
}
