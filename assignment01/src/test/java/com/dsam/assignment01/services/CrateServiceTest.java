package com.dsam.assignment01.services;

import com.dsam.assignment01.forms.AddCrateForm;
import com.dsam.assignment01.models.*;
import com.dsam.assignment01.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CrateServiceTest {

	@InjectMocks
	private CrateServiceImpl crateService;

	@Mock
	private CrateRepository mockCrateRepository;

	@Mock
	private BeverageRepository mockBeverageRepository;

	@Mock
	private BottleServiceImpl mockBottleService;

	@Mock
	private CloudStorageServiceImpl mockCloudStorageService;

	@Test
	public void givenId_whenFindById_thenReturnItem() {
		long crateId = 1;
		Beverage beverage = new Beverage();
		Bottle bottle = new Bottle();
		Crate expectedCrate = new Crate(crateId, 20, 10, bottle, beverage);
		Mockito.when(mockCrateRepository.findById(crateId)).thenReturn(Optional.of(expectedCrate));

		Crate actualCrate = crateService.findById(crateId);
		Assertions.assertEquals(expectedCrate, actualCrate);
	}

	@Test
	public void givenCrateInfo_whenCreateCrate_thenReturnCrate() throws IOException {
		String name = "Mountain Dew";
		String beveragePic = "https://example.com/bottle.png";
		Double beveragePrice = 10.0;
		Double alcoholPercent = 0.0;
		Double volume = 0.0;
		Integer bottleInStock = 12;
		Integer noOfBottles = 10;
		Integer crateInStock = 10;
		long id = 1;
		MockMultipartFile file = new MockMultipartFile("New file", new byte[0]);
		Beverage beverage = new Beverage(id, name, beveragePic, beveragePrice, alcoholPercent);
		Bottle bottle = new Bottle(id, volume, name, bottleInStock, beverage);
		Crate expectedCrate = new Crate(id, noOfBottles, crateInStock, bottle, beverage);
		Mockito.when(mockBeverageRepository.save(any(Beverage.class))).thenReturn((beverage));
		Mockito.when(mockBottleService.findById(any(Long.class))).thenReturn(bottle);
		Mockito.when(mockCrateRepository.save(any(Crate.class))).thenReturn((expectedCrate));
		Mockito.when(mockCloudStorageService.uploadImage(any(MultipartFile.class))).thenReturn(beveragePic);

		AddCrateForm addCrateForm = new AddCrateForm(id, name, file, beveragePic, beveragePrice, alcoholPercent, noOfBottles, crateInStock);
		Crate actualCrate = crateService.createCrate(addCrateForm);
		Assertions.assertNotNull(actualCrate);
		Assertions.assertEquals(expectedCrate.getCrateId(), actualCrate.getCrateId());
		Assertions.assertEquals(expectedCrate.getNoOfBottles(), actualCrate.getNoOfBottles());
		Assertions.assertEquals(expectedCrate.getCratesInStock(), actualCrate.getCratesInStock());
		Assertions.assertEquals(expectedCrate.getBottle().getBottleId(), actualCrate.getBottle().getBottleId());
		Assertions.assertEquals(expectedCrate.getCrateBeverage().getName(), actualCrate.getCrateBeverage().getName());
		Assertions.assertEquals(expectedCrate.getCrateBeverage().getBeveragePic(), actualCrate.getCrateBeverage().getBeveragePic());
		Assertions.assertEquals(expectedCrate.getCrateBeverage().getBeveragePrice(), actualCrate.getCrateBeverage().getBeveragePrice());
		Assertions.assertEquals(expectedCrate.getCrateBeverage().getAlcoholPercent(), actualCrate.getCrateBeverage().getAlcoholPercent());
		Assertions.assertEquals(expectedCrate.getCrateBeverage().getBeverageId(), actualCrate.getCrateBeverage().getBeverageId());
	}
}


