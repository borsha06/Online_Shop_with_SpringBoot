package com.dsam.assignment01.mappers;

import com.dsam.assignment01.forms.AddBottleForm;
import com.dsam.assignment01.models.Bottle;

public class BottleModelMapper {
	public static Bottle fromAddBottleForm(AddBottleForm addbottleForm) {
		Bottle bottle = new Bottle();
		bottle.setBottleInStock(addbottleForm.getBottleInStock());
		bottle.setVolume(addbottleForm.getVolume());
		bottle.setSupplier(addbottleForm.getSupplier());
		bottle.setBottleBeverage(BeverageModelMapper.fromAddBottleForm(addbottleForm));
		return bottle;
	}
}
