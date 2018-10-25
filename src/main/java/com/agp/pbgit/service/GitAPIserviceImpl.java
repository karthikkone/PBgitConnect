package com.agp.pbgit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.LinkedList;

import com.agp.pbgit.model.RepoModel;
import com.agp.pbgit.model.RevisionModel;
import com.agp.pbgit.model.db.AuthData;
import com.agp.pbgit.service.db.AuthDataRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.kohsuke.github.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * GitAPIserviceImpl a REST API wrapper around GitHub API
 */
@RestController
public class GitAPIserviceImpl implements GitAPIservice {
    private String ghAuthToken;
    private AuthDataRepository authDataRepository;
    private final Logger logger = LoggerFactory.getLogger(GitAPIserviceImpl.class);

    @Autowired
    public GitAPIserviceImpl(AuthDataRepository authDataRepository) {
        this.authDataRepository = authDataRepository;
    }


    @RequestMapping(value = "/{user}/repos", method = RequestMethod.GET)
    public List<RepoModel> listRepository(@PathVariable("user") String ghUser) throws IOException {

        //get stored Auth token
        AuthData ghAuthToken = authDataRepository.findAll().get(0);

        logger.info("using token with GitHub" + ghAuthToken);

        //access github api with the token
        GitHub github = GitHub.connectUsingOAuth(ghAuthToken.getAuthToken());
        Map<String, GHRepository> reps = github.getUser(ghUser).getRepositories();
        List<RepoModel> repoData = new LinkedList<RepoModel>();

        for (GHRepository repo : reps.values()) {
            RepoModel model = new RepoModel();
            model.setRepoName(repo.getFullName());
            model.setOwnerName(repo.getOwnerName());
            model.setCreatedAt(repo.getCreatedAt());
            model.setMasterBranch(repo.getMasterBranch());
            model.setUpdatedAt(repo.getUpdatedAt());
            model.setUrl(repo.getHttpTransportUrl());

            repoData.add(model);
        }
        return repoData;
    }


    @RequestMapping(value = "/{owner}/repos/{repository}/refs/heads", method = RequestMethod.GET)
    public ResponseEntity<List<RevisionModel>> getRevisions(@PathVariable("owner") String owner, @PathVariable("repository") String repository) throws IOException {
        AuthData authData = authDataRepository.findAll().get(0);

        logger.info("fetching revisions for " + owner + "/" + repository);

        GitHub gitHub = GitHub.connectUsingOAuth(authData.getAuthToken());
        GHRef[] refs = gitHub.getUser(owner).getRepository(repository).getRefs();

        List<RevisionModel> revisions = new ArrayList<RevisionModel>();

        for (GHRef ref : refs) {
            if (ref != null && ref.getObject() != null) {
                GHRef.GHObject refObj = ref.getObject();
                revisions.add(new RevisionModel(refObj.getType(), refObj.getUrl(), refObj.getSha()));
            }
        }

        return new ResponseEntity<List<RevisionModel>>(revisions, HttpStatus.OK);
    }


    @RequestMapping(value = "/{owner}/repos/{repository}/refs/heads", method = RequestMethod.POST)
    public ResponseEntity<String> createBranch(@PathVariable("owner") String owner, @PathVariable("repository") String repository, @RequestBody Map<String, Object> payload) throws  IOException {
        AuthData authData = authDataRepository.findAll().get(0);
        String sha = (String) payload.get("sha1");
        String branch = (String) payload.get("branch");

        logger.info("creating a branch @SHA-1 : " + sha + "/" + branch + " on repository " + owner + "/" + repository);
        GitHub gitHub = GitHub.connectUsingOAuth(authData.getAuthToken());
        GHRef[] refs = gitHub.getUser(owner).getRepository(repository).getRefs();
        GHBranch ghBranch = new GHBranch();
       
        boolean ok = false;

        for (GHRef ghRef : refs) {
            if (ghRef != null && ghRef.getObject() != null && ghRef.getObject().getSha().equals(sha)) {
                ok = true; //found the ref from where to spin off a branch
                
                OkHttpClient httpClient = new OkHttpClient();
                
                //api.github.com/repos/:author/:repository/git/refs
                HttpUrl ghURL = HttpUrl.parse(gitHub.getApiUrl()).newBuilder()
                        .addPathSegment("repos")
                        .addPathSegment(owner)
                        .addPathSegment(repository)
                        .addPathSegment("git")
                        .addPathSegment("refs")
                        .build();
                
               Map<String, String> branchData = new HashMap<String, String>();
               branchData.put("ref", "refs/heads/"+branch);
               branchData.put("sha", sha); //hash to branch from
               
               Gson gson = new GsonBuilder()
            		   .setPrettyPrinting().create();
               String branchPayload = gson.toJson(branchData);
               
               okhttp3.RequestBody body = okhttp3.RequestBody.create(
            		   MediaType.parse("application/json; charset=utf-8"),branchPayload);
               
               Request request = new Request.Builder()
               		.header("Accept", "application/json")
               		.header("Authorization", authData.getAuthToken())
                       .url(ghURL)
                       .post(body)
                       .build();
               
               logger.info("posting "+branchPayload+" to "+ghURL);
               Response response = httpClient.newCall(request).execute();
               
               logger.info("POST "+ghURL+" returned status "+response.code());
               
               //github must return http Created 201
               if (response.code() != 201) ok=false;
               response.close();
               
            }
        }

        if (!ok) {
            return new ResponseEntity<String>("message: no ref with sha found :" + sha, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>("branch: " + branch, HttpStatus.OK);
    }

}