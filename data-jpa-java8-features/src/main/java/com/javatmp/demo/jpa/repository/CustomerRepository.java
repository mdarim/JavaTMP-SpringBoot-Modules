package com.javatmp.demo.jpa.repository;

import com.javatmp.demo.jpa.entity.Customer;
import jakarta.persistence.LockModeType;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernateHints;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import jakarta.persistence.QueryHint;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findById(Long id);

    Stream<Customer> findAllByIdNotNull();

    @Async
    CompletableFuture<Customer> findOneById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = {
            @QueryHint(name = HibernateHints.HINT_FETCH_SIZE, value = "1"),
            @QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "false"),
            @QueryHint(name = AvailableSettings.JAKARTA_LOCK_TIMEOUT, value = "-2")
    })
    Stream<Customer> findByStatus(Integer status);

    @QueryHints(value = {
            @QueryHint(name = HibernateHints.HINT_FETCH_SIZE, value = "100"),
            @QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "false"),
            @QueryHint(name = AvailableSettings.JAKARTA_LOCK_TIMEOUT, value = "-2")
    })
    @Query(value = "SELECT * FROM Customer where status = ?1 for update skip locked", nativeQuery = true)
    Stream<Customer> selectForUpdateNative(Integer status);

    @QueryHints(value = {
            @QueryHint(name = HibernateHints.HINT_FETCH_SIZE, value = "100"),
            @QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "false"),
            @QueryHint(name = AvailableSettings.JAKARTA_LOCK_TIMEOUT, value = "-2")
    })
//    @Query(value = "SELECT c FROM Customer where status = ?1")
    Stream<Customer> findAllByStatus(Integer status);

    @Modifying
    @Query("update Customer set status = :status where id = :id")
    void updateCustomerStatus(@Param("id") Long customerId,
                                   @Param("status") Integer newStatus);
}
