package svl.challenge.usersapirestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import svl.challenge.usersapirestful.domain.entity.Phones;

public interface PhonesRepository extends JpaRepository<Phones, Long> {

}
