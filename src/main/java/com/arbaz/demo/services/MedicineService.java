package com.arbaz.demo.services;

import java.util.List;

import com.arbaz.demo.models.Medicine;

public interface MedicineService {
	Medicine addMedicine(Medicine md);
	List<Medicine> getAllMedicine();
	void deleteMedicineById(int id);
	Medicine findMedicineById(int id);
}
