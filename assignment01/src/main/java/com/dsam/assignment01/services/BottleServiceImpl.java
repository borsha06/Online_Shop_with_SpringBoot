package com.dsam.assignment01.services;

import com.dsam.assignment01.forms.AddBottleForm;
import com.dsam.assignment01.mappers.BottleModelMapper;
import com.dsam.assignment01.models.Bottle;
import com.dsam.assignment01.repositories.BeverageRepository;
import com.dsam.assignment01.repositories.BottleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class BottleServiceImpl implements BottleService {
	private final BottleRepository bottleRepository;
	private final BeverageRepository beverageRepository;
	private final CloudStorageService cloudStorageService;

	@Autowired
	public BottleServiceImpl(BottleRepository bottleRepository, BeverageRepository beverageRepository, CloudStorageService cloudStorageService) {
		this.bottleRepository = bottleRepository;
		this.beverageRepository = beverageRepository;
		this.cloudStorageService = cloudStorageService;
	}

	public List<Bottle> findAll() {
		return this.bottleRepository.findAll();
	}

	public Bottle createBottle(AddBottleForm addBottleForm) throws IllegalArgumentException, IOException {
		try {
			String beveragePic = this.cloudStorageService.uploadImage(addBottleForm.getImage());
			addBottleForm.setBeveragePic(beveragePic);
			Bottle bottle = BottleModelMapper.fromAddBottleForm(addBottleForm);
			this.beverageRepository.save(bottle.getBottleBeverage());
			return bottleRepository.save(bottle);
		} catch (IllegalArgumentException | IOException exception) {
			log.error(exception.getMessage());
			throw exception;
		}
	}

	public Bottle findById(Long bottleId) {
		if (bottleId == null) {
			return null;
		}
		return this.bottleRepository.findById(bottleId).orElse(null);
	}
}
