package com.agp.pbgit.service;

import com.agp.pbgit.model.db.AuthData;
import com.agp.pbgit.service.db.AuthDataRepository;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import okhttp3.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@RestController
public class GitAuthServiceImpl implements GitAuthService{

    @Value("${github.client_id}")
    private String clientId;

    @Value("${github.oauth.url}")
    private String gitOAuthUrl;

    @Value("${github.client_secret}")
    private String clientSecret;

    private AuthDataRepository authDataRepository;
    
    private final Logger logger = LoggerFactory.getLogger(GitAuthServiceImpl.class);
    @Autowired
    public GitAuthServiceImpl(AuthDataRepository authDataRepository) {
        this.authDataRepository = authDataRepository;
    }

    @RequestMapping(value = "/auth/callback")
    public void getOauthToken(@RequestParam(value="code") String code) throws IOException {
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
        		.header("Accept", "application/json")
                .url(ghURL)
                .post(formBody)
                .build();

        Response response = httpClient.newCall(request).execute();
        
        logger.info("POST "+ghURL.toString()+" returned resonse code "+response.code());
        
        if (response.code() == 200) {
            try {
            	
                String resp = response.body().string();
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                logger.info("Recieved GitHub OAuth token "+resp);
                
                AuthData authData = gson.fromJson(resp, AuthData.class);
                authDataRepository.saveAndFlush(authData);

                List<AuthData> tokens = authDataRepository.findAll();
                logger.info("Currently stored tokens"+tokens);

            } catch (Exception e){
                logger.error(e.getMessage());
            } finally {
                response.body().close();
                response.close();
            }
        }

    }

}
