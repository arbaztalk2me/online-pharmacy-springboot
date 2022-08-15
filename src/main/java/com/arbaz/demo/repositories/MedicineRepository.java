package com.arbaz.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arbaz.demo.models.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine,Integer> {

}
