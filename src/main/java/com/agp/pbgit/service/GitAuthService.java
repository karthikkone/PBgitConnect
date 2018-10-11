package com.agp.pbgit.service;

import com.agp.pbgit.model.ResponseModel;

import java.io.IOException;

public interface GitAuthService {
    ResponseModel getOauthToken(String code) throws IOException;
}
