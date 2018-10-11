package com.agp.pbgit.service;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.LinkedList;

import com.agp.pbgit.model.RepoModel;
import com.agp.pbgit.model.db.AuthData;
import com.agp.pbgit.service.db.AuthDataRepository;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * GitAPIserviceImpl a REST API wrapper around GitHub API
 */
@RestController
public class GitAPIserviceImpl implements GitAPIservice{
    private String ghAuthToken;
    private AuthDataRepository authDataRepository;
    private final Logger logger = LoggerFactory.getLogger(GitAPIserviceImpl.class);

    @Autowired
    public GitAPIserviceImpl(AuthDataRepository authDataRepository) {
        this.authDataRepository = authDataRepository;
    }
    
    
    @RequestMapping(value="/{user}/repos", method=RequestMethod.GET)
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


    @RequestMapping(value={"/repos/create"}, method = RequestMethod.POST)
    public void createRepository(@RequestBody Map<String, Object> payload) throws IOException{
        if (payload != null) {

            String repoName = (String)payload.get("repo_name");
            AuthData ghAuthToken = authDataRepository.findAll().get(0);
            GitHub github = GitHub.connectUsingOAuth(ghAuthToken.getAuthToken());
            github.createRepository(repoName);
        }
    }
}