package com.bside.backendapi.global.jwt.repository;

import com.bside.backendapi.global.jwt.vo.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

}
