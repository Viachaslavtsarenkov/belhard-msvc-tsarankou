package by.tsarankou.servicesong.data.repository;

import by.tsarankou.servicesong.data.Audio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AudioRepository extends CrudRepository<Audio, Integer> {
    void deleteAllByIdIn(Collection<Integer> ids);
    List<Audio> findAllByResourceIdInOrIdIn(Collection<Integer> ids);
}
