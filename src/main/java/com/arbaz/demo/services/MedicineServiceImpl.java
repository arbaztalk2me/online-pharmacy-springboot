package com.arbaz.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arbaz.demo.models.Medicine;
import com.arbaz.demo.repositories.MedicineRepository;

@Service
public class MedicineServiceImpl implements MedicineService {
	
	@Autowired
	MedicineRepository  medicineRepository;

	@Override
	public Medicine addMedicine(Medicine md) {
		Medicine med=medicineRepository.save(md);
		return med;
	}

	@Override
	public List<Medicine> getAllMedicine() {
		List<Medicine> md=medicineRepository.findAll();
		return md;
	}

	@Override
	public void deleteMedicineById(int id) {
		Medicine med=medicineRepository.findById(id).get();
		medicineRepository.delete(med);
		
	}

	@Override
	public Medicine findMedicineById(int id) {
		Medicine med=medicineRepository.findById(id).get();
		return med;
	}
	
	
	
}
