package com.agp.pbgit.service;

import com.agp.pbgit.model.db.AuthData;
import com.agp.pbgit.service.db.AuthDataRepository;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Random;

@Controller
public class GitConnectServiceImpl {

    @Value("${github.oauth.url}")
    private String gitOAuthUrl;

    @Value("${client_id}")
    private String clientId;



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

        Response response = httpClient.newCall(request).execute();


        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(request.url().toString());
        return  redirectView;

    }

    @RequestMapping("/callback")
    public void callback(@RequestParam(value="code") String authCode) {

    }
}
