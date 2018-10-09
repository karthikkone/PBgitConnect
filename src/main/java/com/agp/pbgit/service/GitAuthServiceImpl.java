package com.agp.pbgit.service;

import com.agp.pbgit.model.db.AuthData;
import com.agp.pbgit.service.db.AuthDataRepository;
import okhttp3.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Random;

@RestController
public class GitAuthServiceImpl {

    @Value("${client_id}")
    private String clientId;

    @Value("${github.oauth.url}")
    private String gitOAuthUrl;

    @Value("${client_secret}")
    private String clientSecret;

    private AuthDataRepository authDataRepository;
    
    private final Logger logger = LoggerFactory.getLogger(GitAuthServiceImpl.class);
    @Autowired
    public GitAuthServiceImpl(AuthDataRepository authDataRepository) {
        this.authDataRepository = authDataRepository;
    }

    @RequestMapping(value = "/auth/callback")
    public void getAuthToken(@RequestParam(value="code") String code) throws IOException {

        OkHttpClient httpClient = new OkHttpClient();

        HttpUrl ghURL = HttpUrl.parse(gitOAuthUrl).newBuilder()
                .addPathSegment("access_token")
                .build();

        FormBody formBody = new FormBody.Builder()
                .add("client_id",clientId)
                .add("client_secret", clientSecret)
                .add("code", code)
                .build();

        Request request = new Request.Builder()
                .url(ghURL)
                .post(formBody)
                .build();

        Response response = httpClient.newCall(request).execute();
        
        logger.info("POST "+ghURL.toString()+" returned resonse code "+response.code());
        
        if (response.code() == 200) {
            try {
                String oAuthToken = response.body().string();
                logger.info("Recieved GitHub OAuth token "+oAuthToken);
                authDataRepository.saveAndFlush(new AuthData(new Random().nextLong(), oAuthToken));

            } catch (Exception e){
                logger.error(e.getMessage());
            } finally {
                response.body().close();
            }
        }

    }

}
