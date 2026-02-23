package cms.admin.main.repository;

import cms.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainDomainRepository extends JpaRepository<Domain, Long> {
}
