package com.dsam.assignment01.services;

import com.dsam.assignment01.models.Beverage;
import com.dsam.assignment01.repositories.BeverageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BeverageServiceTest {

	@InjectMocks
	private BeverageServiceImpl beverageService;

	@Mock
	private BeverageRepository mockBeverageRepository;

	@Test
	public void givenId_whenFindById_thenReturnItem() {
		long beverageId = 1;
		Beverage expectedBeverage = new Beverage(beverageId, "New beverage", "http//example.com/name.png", 5.0, 10.0);
		Mockito.when(mockBeverageRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedBeverage));

		Beverage actualBeverage = beverageService.findById(beverageId);
		Assertions.assertNotNull(actualBeverage);
		Assertions.assertEquals(expectedBeverage.getBeverageId(), actualBeverage.getBeverageId());
		Assertions.assertEquals(expectedBeverage.getBeveragePic(), actualBeverage.getBeveragePic());
		Assertions.assertEquals(expectedBeverage.getBeveragePrice(), actualBeverage.getBeveragePrice());
		Assertions.assertEquals(expectedBeverage.getAlcoholPercent(), actualBeverage.getAlcoholPercent());
		Assertions.assertEquals(expectedBeverage.getName(), actualBeverage.getName());
	}
}
