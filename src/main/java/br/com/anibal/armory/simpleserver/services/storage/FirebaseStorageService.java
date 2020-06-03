package br.com.anibal.armory.simpleserver.services.storage;

import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class FirebaseStorageService implements StorageService {

    @Override
    public void init() {

    }

    @Override
    public void store(MultipartFile file) {
        String filename = file.getOriginalFilename();
        try {
            StorageClient.getInstance().bucket("anibal-spring-armory.appspot.com").create(filename, file.getInputStream());
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
