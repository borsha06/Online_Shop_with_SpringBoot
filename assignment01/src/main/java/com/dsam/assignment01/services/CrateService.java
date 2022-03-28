package com.dsam.assignment01.services;

import com.dsam.assignment01.forms.AddCrateForm;
import com.dsam.assignment01.models.Crate;

import java.io.IOException;
import java.util.List;

public interface CrateService {
	List<Crate> findAll();

	Crate findById(Long crateId);

	Crate createCrate(AddCrateForm addCrateForm) throws IOException;
}
