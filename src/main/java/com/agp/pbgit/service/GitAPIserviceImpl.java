package com.agp.pbgit.service;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.LinkedList;

import com.agp.pbgit.model.RepoModel;
import com.agp.pbgit.service.db.AuthDataRepository;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * GitAPIserviceImpl a REST API wrapper around GitHub API
 */
@RestController
public class GitAPIserviceImpl implements GitAPIservice{
    private String ghAuthToken;
    private AuthDataRepository authDataRepository;
    
    @Autowired
    public GitAPIserviceImpl(AuthDataRepository authDataRepository) {
        
    }
    
    @RequestMapping(value="/repos", method=RequestMethod.GET)
    public List<RepoModel> requestMethodName(@RequestParam String param) throws IOException {
    	
    	//get stored Auth token
    	this.ghAuthToken = authDataRepository.findAll().get(0).getOauthToken();
        GitHub github = GitHub.connectUsingOAuth(ghAuthToken);
        Map<String, GHRepository> reps = github.getMyself().getAllRepositories();
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
    
}