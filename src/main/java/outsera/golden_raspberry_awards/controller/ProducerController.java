package outsera.golden_raspberry_awards.controller;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import outsera.golden_raspberry_awards.dtos.ProducerDTO;
import outsera.golden_raspberry_awards.model.Producer;
import outsera.golden_raspberry_awards.service.ProducerService;












@RestController
@RequestMapping("/producers")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/add")
    public void addProducers(@RequestBody List<ProducerDTO> producerDTOs) {
        producerService.saveProducers(producerDTOs);
    }

    @GetMapping("/all")
    public List<Producer> getAllProducers() {
        return producerService.getAllProducers();
    }

    @GetMapping("/intervals")
    public Map<String, List<Map<String, Object>>> getAwardIntervals() {
        return producerService.getAwardIntervals();
    }
}
