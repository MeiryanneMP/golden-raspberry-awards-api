package outsera.golden_raspberry_awards.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outsera.golden_raspberry_awards.dtos.ProducerDTO;
import outsera.golden_raspberry_awards.model.Producer;
import outsera.golden_raspberry_awards.repository.ProducerRepository;





@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    @Transactional
    public void saveProducers(List<ProducerDTO> producerDTOs) {
        for (ProducerDTO dto : producerDTOs) {
            Producer producer = new Producer();
            producer.setProducer(dto.producer());
            producer.setYear(dto.year());
            producerRepository.save(producer);
        }
    }

    public List<Producer> getAllProducers() {
        return producerRepository.findAll();
    }
}
