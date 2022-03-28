package com.dsam.assignment01.services;

import com.dsam.assignment01.forms.AddCrateForm;
import com.dsam.assignment01.mappers.CrateModelMapper;
import com.dsam.assignment01.models.Bottle;
import com.dsam.assignment01.models.Crate;
import com.dsam.assignment01.repositories.BeverageRepository;
import com.dsam.assignment01.repositories.CrateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class CrateServiceImpl implements CrateService {
	private final BeverageRepository beverageRepository;
	private final CrateRepository crateRepository;
	private final BottleService bottleService;
	private final CloudStorageService cloudStorageService;

	@Autowired
	public CrateServiceImpl(CrateRepository crateRepository, BeverageRepository beverageRepository, BottleService bottleService, CloudStorageService cloudStorageService) {
		this.crateRepository = crateRepository;
		this.beverageRepository = beverageRepository;
		this.bottleService = bottleService;
		this.cloudStorageService = cloudStorageService;
	}

	public List<Crate> findAll() {
		return this.crateRepository.findAll();
	}

	public Crate findById(Long crateId) {
		if (crateId == null) {
			return null;
		}

		return this.crateRepository.findById(crateId).orElse(null);
	}

	public Crate createCrate(AddCrateForm addCrateForm) throws IllegalArgumentException, IOException {
		try {
			String beveragePic = this.cloudStorageService.uploadImage(addCrateForm.getImage());
			addCrateForm.setBeveragePic(beveragePic);
			Bottle bottle = this.bottleService.findById(addCrateForm.getBottleId());
			Crate crate = CrateModelMapper.fromAddCrateForm(addCrateForm, bottle);
			this.beverageRepository.save(crate.getCrateBeverage());
			return crateRepository.save(crate);
		} catch (IllegalArgumentException | IOException exception) {
			log.error(exception.getMessage());
			throw exception;
		}
	}

}
