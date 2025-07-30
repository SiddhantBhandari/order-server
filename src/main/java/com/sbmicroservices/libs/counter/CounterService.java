package com.sbmicroservices.libs.counter;


import org.springframework.stereotype.Service;

@Service
public class CounterService {


    private final CounterRepository counterRepository;

    public CounterService(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public Long counter(String name) {
        Counter byName = counterRepository.findByName(name);
        if (byName == null) {
            byName = new Counter();
            byName.setName(name);
            byName.setSequence(1L);
            counterRepository.save(byName);
        } else {
            byName.setSequence(byName.getSequence() + 1);
            counterRepository.saveAndFlush(byName);
        }
        return byName.getSequence();
    }
}
