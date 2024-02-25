package by.tsarankou.serviceresource.data.repository;

import by.tsarankou.serviceresource.data.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, Integer> {
}
