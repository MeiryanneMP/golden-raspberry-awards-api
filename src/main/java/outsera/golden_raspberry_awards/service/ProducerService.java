package outsera.golden_raspberry_awards.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    public Map<String, List<Map<String, Object>>> getAwardIntervals() {
        List<Producer> producers = getAllProducers();
        Map<String, List<Integer>> groupedProducers = groupProducersByName(producers);

        List<Map<String, Object>> intervals = generateIntervals(groupedProducers);

        if (intervals.isEmpty()) {
            return Map.of("min", List.of(), "max", List.of());
        }

        int minInterval = findMinInterval(intervals);
        int maxInterval = findMaxInterval(intervals);

        List<Map<String, Object>> minList = filterByInterval(intervals, minInterval);
        List<Map<String, Object>> maxList = filterByInterval(intervals, maxInterval);

        return Map.of("min", minList, "max", maxList);
    }

    private Map<String, List<Integer>> groupProducersByName(List<Producer> producers) {
        return producers.stream()
                .collect(Collectors.groupingBy(
                        Producer::getProducer,
                        Collectors.mapping(Producer::getYear, Collectors.toList())
                ));
    }

    private List<Map<String, Object>> generateIntervals(Map<String, List<Integer>> groupedProducers) {
        List<Map<String, Object>> intervals = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : groupedProducers.entrySet()) {
            String producerName = entry.getKey();
            List<Integer> years = entry.getValue();
            Collections.sort(years);

            IntStream.range(1, years.size())
                     .mapToObj(i -> createIntervalData(producerName, years.get(i - 1), years.get(i), years.get(i) - years.get(i - 1)))
                     .forEach(intervals::add);

            if (years.size() > 1) {
                int previous = years.get(0);
                int following = years.get(0);
                int interval = 0;
                intervals.add(createIntervalData(producerName, previous, following, interval));
            }
        }

        return intervals;
    }

    private Map<String, Object> createIntervalData(String producerName, int previous, int following, int interval) {
        Map<String, Object> data = new HashMap<>();
        data.put("producer", producerName);
        data.put("interval", interval);
        data.put("previousWin", previous);
        data.put("followingWin", following);
        return data;
    }

    private int findMinInterval(List<Map<String, Object>> intervals) {
        return intervals.stream()
                .mapToInt(i -> (int) i.get("interval"))
                .min()
                .orElse(0);
    }

    private int findMaxInterval(List<Map<String, Object>> intervals) {
        return intervals.stream()
                .mapToInt(i -> (int) i.get("interval"))
                .max()
                .orElse(0);
    }

    private List<Map<String, Object>> filterByInterval(List<Map<String, Object>> intervals, int interval) {
        return intervals.stream()
                .filter(i -> (int) i.get("interval") == interval)
                .collect(Collectors.toList());
    }
}
