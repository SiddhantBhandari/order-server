package com.sbmicroservices.libs.counter;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {

    Counter findByName(String name);
}
