package com.dsam.assignment01.services;

import com.dsam.assignment01.forms.AddBottleForm;
import com.dsam.assignment01.models.Bottle;

import java.io.IOException;
import java.util.List;

public interface BottleService {
	List<Bottle> findAll();

	Bottle findById(Long bottleId);

	Bottle createBottle(AddBottleForm addBottleForm) throws IOException;
}
