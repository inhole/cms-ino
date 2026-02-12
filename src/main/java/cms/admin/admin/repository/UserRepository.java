package cms.admin.admin.repository;

import cms.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUserId(String userId);
    List<Admin> findByUserLevelOrderByIdDesc(String userLevel);
    List<Admin> findByUserLevelNotOrderByIdDesc(String userLevel);
    boolean existsByUserId(String userId);
    boolean existsByUserIdAndIdNot(String userId, Long id);
}
