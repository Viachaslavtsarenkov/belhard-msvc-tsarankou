package by.tsarankou.servicesong.data.repository;

import by.tsarankou.servicesong.data.Audio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioRepository extends CrudRepository<Audio, Integer> {
}
