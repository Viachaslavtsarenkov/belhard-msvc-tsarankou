package by.tsarankou.serviceresource.data.repository;

import by.tsarankou.serviceresource.data.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    List<Resource> findAllByIdIn(Collection<Integer> ids);
    @Query(value = "SELECT e.id FROM Resource e WHERE e.id IN :ids", nativeQuery = true)
    List<Integer> findExistingIds(@Param("ids") List<Integer> ids);
    void deleteAllByIdIn(Collection<Integer> ids);
}
