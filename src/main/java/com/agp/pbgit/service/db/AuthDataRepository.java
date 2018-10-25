package com.agp.pbgit.service.db;


import com.agp.pbgit.model.db.AuthData;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthDataRepository extends CrudRepository<AuthData, Long>{
	List<AuthData> findAll();
}
