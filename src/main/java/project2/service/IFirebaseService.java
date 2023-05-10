package project2.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface IFirebaseService {

    ResponseEntity downloadFile(String fileName);

    String uploadFile(MultipartFile multipartFile) throws IOException;
}


