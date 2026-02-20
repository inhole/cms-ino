package cms.admin.menu.repository;

import cms.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByLangTypeOrderByMenuGrpAscMenuOrdAscMenuIdAsc(String langType);

    boolean existsByUpperMenuId(String upperMenuId);

    @Query("select coalesce(max(m.menuOrd), 0) from Menu m where m.langType = :langType and m.upperMenuId = :upperMenuId")
    BigDecimal findMaxOrderByLangTypeAndUpperMenuId(String langType, String upperMenuId);
}
