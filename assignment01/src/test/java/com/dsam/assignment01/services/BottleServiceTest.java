package com.dsam.assignment01.services;

import com.dsam.assignment01.forms.AddBottleForm;
import com.dsam.assignment01.models.Beverage;
import com.dsam.assignment01.models.Bottle;
import com.dsam.assignment01.repositories.BeverageRepository;
import com.dsam.assignment01.repositories.BottleRepository;
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
public class BottleServiceTest {

	@InjectMocks
	private BottleServiceImpl bottleService;

	@Mock
	private CloudStorageServiceImpl mockCloudStorageService;

	@Mock
	private BottleRepository mockBottleRepository;

	@Mock
	private BeverageRepository mockBeverageRepository;

	@Test
	public void givenId_whenFindById_thenReturnItem() {
		long bottleId = 1;
		Beverage beverage = new Beverage();
		Bottle expectedBottle = new Bottle(bottleId, 0.0, "Mountain Dew", 20, beverage);
		Mockito.when(mockBottleRepository.findById(bottleId)).thenReturn(Optional.of(expectedBottle));

		Bottle actualBottle = bottleService.findById(bottleId);
		Assertions.assertNotNull(actualBottle);
		Assertions.assertEquals(expectedBottle.getBottleId(), actualBottle.getBottleId());
		Assertions.assertEquals(expectedBottle.getBottleBeverage().getBeverageId(), actualBottle.getBottleBeverage().getBeverageId());
		Assertions.assertEquals(expectedBottle.getBottleBeverage().getName(), actualBottle.getBottleBeverage().getName());
		Assertions.assertEquals(expectedBottle.getBottleBeverage().getBeveragePic(), actualBottle.getBottleBeverage().getBeveragePic());
		Assertions.assertEquals(expectedBottle.getBottleBeverage().getBeveragePrice(), actualBottle.getBottleBeverage().getBeveragePrice());
		Assertions.assertEquals(expectedBottle.getVolume(), actualBottle.getVolume());
		Assertions.assertEquals(expectedBottle.getBottleInStock(), actualBottle.getBottleInStock());
		Assertions.assertEquals(expectedBottle.getSupplier(), actualBottle.getSupplier());
		Assertions.assertEquals(expectedBottle.getBottleBeverage().getAlcoholPercent(), actualBottle.getBottleBeverage().getAlcoholPercent());
	}

	@Test
	public void givenBottleInfo_whenCreateBottle_thenReturnBottle() throws IOException {
		String name = "Mountain Dew";
		String beveragePic = "https://example.com/bottle.png";
		Double beveragePrice = 10.0;
		Double alcoholPercent = 0.0;
		Double volume = 0.0;
		Integer bottleInStock = 12;
		MockMultipartFile file = new MockMultipartFile("New file", new byte[0]);
		Beverage beverage = new Beverage(null, name, beveragePic, beveragePrice, alcoholPercent);
		Bottle expectedBottle = new Bottle(null, volume, name, bottleInStock, beverage);
		Mockito.when(mockBeverageRepository.save(any(Beverage.class))).thenReturn((beverage));
		Mockito.when(mockBottleRepository.save(any(Bottle.class))).thenReturn((expectedBottle));
		Mockito.when(mockCloudStorageService.uploadImage(any(MultipartFile.class))).thenReturn(beveragePic);

		AddBottleForm addBottleForm = new AddBottleForm(name, file, beveragePic, beveragePrice, alcoholPercent, volume, name, bottleInStock);
		Bottle actualBottle = bottleService.createBottle(addBottleForm);
		Assertions.assertNotNull(actualBottle);
		Assertions.assertEquals(expectedBottle.getBottleId(), actualBottle.getBottleId());
		Assertions.assertEquals(expectedBottle.getBottleBeverage().getBeverageId(), actualBottle.getBottleBeverage().getBeverageId());
		Assertions.assertEquals(expectedBottle.getBottleBeverage().getName(), actualBottle.getBottleBeverage().getName());
		Assertions.assertEquals(expectedBottle.getBottleBeverage().getBeveragePic(), actualBottle.getBottleBeverage().getBeveragePic());
		Assertions.assertEquals(expectedBottle.getBottleBeverage().getBeveragePrice(), actualBottle.getBottleBeverage().getBeveragePrice());
		Assertions.assertEquals(expectedBottle.getVolume(), actualBottle.getVolume());
		Assertions.assertEquals(expectedBottle.getBottleBeverage().getAlcoholPercent(), actualBottle.getBottleBeverage().getAlcoholPercent());
		Assertions.assertEquals(expectedBottle.getBottleInStock(), actualBottle.getBottleInStock());
		Assertions.assertEquals(expectedBottle.getSupplier(), actualBottle.getSupplier());
		Assertions.assertEquals(expectedBottle.getBottleBeverage().getAlcoholPercent(), actualBottle.getBottleBeverage().getAlcoholPercent());
	}
}
