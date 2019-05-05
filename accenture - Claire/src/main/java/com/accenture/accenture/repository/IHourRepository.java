package com.accenture.accenture.repository;

import com.accenture.accenture.model.Hour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IHourRepository extends JpaRepository<Hour, Integer> {

    @Query(value = "select * from Hours where month= :month and year = :year and referencepk= :employeeId", nativeQuery = true)
    List<Hour> findByMonthAndYear(@Param("month") int month, @Param("year") int year,@Param("employeeId") int employeeId);
}
