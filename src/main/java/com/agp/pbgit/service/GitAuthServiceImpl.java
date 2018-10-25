package com.agp.pbgit.service;

import com.agp.pbgit.model.ResponseModel;
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

    @RequestMapping(value = "/auth/callback", produces="application/json")
    public ResponseModel getOauthToken(@RequestParam(value="code") String code) throws IOException {
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

        ResponseModel apiResponse = new ResponseModel();

        logger.info("POST "+ghURL.toString()+" returned resonse code "+response.code());


        if (response.code() == 200) {
            try {
            	
                String resp = response.body().string();
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                logger.info("Recieved GitHub OAuth token "+resp);
                
                AuthData authData = gson.fromJson(resp, AuthData.class);
                authDataRepository.save(authData);

                //indeed received auth token success
                if (authData != null && authData.getAuthToken() != null) {
                    apiResponse.setHttpStatus(200);
                    apiResponse.setMessage("Authorized by GitHub");
                }

            } catch (Exception e){
                logger.error(e.getMessage());
                apiResponse.setHttpStatus(401);
                apiResponse.setMessage("Not Authorized by GitHUb");
            } finally {
                response.body().close();
                response.close();
            }
        } else {
            apiResponse.setHttpStatus(404);
            apiResponse.setMessage("GitHub not reachable");
        }


        return apiResponse;
    }

}
