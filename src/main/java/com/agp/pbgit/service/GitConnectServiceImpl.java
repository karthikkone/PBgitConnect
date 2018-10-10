package com.agp.pbgit.service;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
public class GitConnectServiceImpl {

    @Value("${github.oauth.url}")
    private String gitOAuthUrl;

    @Value("${client_id}")
    private String clientId;

    private Logger logger = LoggerFactory.getLogger(GitConnectServiceImpl.class);

    @RequestMapping("/connect")
    public RedirectView connect() throws IOException{
        OkHttpClient httpClient =  new OkHttpClient();

        HttpUrl ghURL = HttpUrl.parse(gitOAuthUrl).newBuilder()
                .addPathSegment("authorize")  //https://github.com/login/oauth/authorize
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("scope", "user repo") //ask repo and user profile permissions
                .build();

        Request request = new Request.Builder()
                .url(ghURL)
                .build();
        
        logger.info("sending GET request to "+ghURL.toString());
        
        
        RedirectView redirectView = new RedirectView();
        try {
        	Response response = httpClient.newCall(request).execute();
        	if (response.code() == 200) {
        		logger.info("redirecting to "+request.url());
        		redirectView.setUrl(request.url().toString());
        	}
        } catch(Exception e) {
        	logger.error(e.getMessage());
        }
           
        return  redirectView;

    }

}
