package br.com.anibal.armory.simpleserver.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FBInitialize {

    @Value("classpath:anibal-spring-armory-firebase-admin.json")
    Resource firebaseAdminResource;

    @PostConstruct
    public void initialize() {
        try {

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(firebaseAdminResource.getInputStream()))
                    .setDatabaseUrl("https://anibal-spring-armory.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}