package outsera.golden_raspberry_awards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import outsera.golden_raspberry_awards.model.Producer;


@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {}