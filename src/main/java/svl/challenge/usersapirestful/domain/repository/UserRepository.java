package svl.challenge.usersapirestful.domain.repository;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import svl.challenge.usersapirestful.domain.entity.User;

import java.util.Date;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    @Transactional
    void deleteById(String id);

    @Modifying
    @Transactional
    @Query(value="update User set last_login = :updatedDate where id = :id")
    void updateLastLoginById(@Param("updatedDate") Date date, @Param("id") String id);

    @Modifying
    @Transactional
    @Query(value="update User set token = :updatedToken where id = :id")
    void updateTokenById(@Param("updatedToken") String updatedToken, @Param("id") String id);

    @Modifying
    @Transactional
    @Query(value="update User set token = NULL where id = :id")
    void deleteTokenById(@Param("id") String id);
}
