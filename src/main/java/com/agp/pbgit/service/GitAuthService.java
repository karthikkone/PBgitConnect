package com.agp.pbgit.service;

import java.io.IOException;

public interface GitAuthService {
    void getOauthToken(String code) throws IOException;
}
