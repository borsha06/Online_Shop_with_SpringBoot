package com.dsam.assignment01.services;

import com.dsam.assignment01.models.Beverage;
import com.dsam.assignment01.repositories.BeverageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BeverageServiceImpl implements BeverageService {

	private final BeverageRepository beverageRepository;

	@Autowired
	public BeverageServiceImpl(BeverageRepository beverageRepository) {
		this.beverageRepository = beverageRepository;
	}

	public Beverage findById(Long beverageId) {
		if (beverageId == null) {
			return null;
		}

		Optional<Beverage> beverage = this.beverageRepository.findById(beverageId);
		return beverage.orElse(null);
	}
}
