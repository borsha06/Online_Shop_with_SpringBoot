package com.dsam.assignment01.mappers;

import com.dsam.assignment01.forms.AddCrateForm;
import com.dsam.assignment01.models.Bottle;
import com.dsam.assignment01.models.Crate;


public class CrateModelMapper {
	public static Crate fromAddCrateForm(AddCrateForm addCrateForm, Bottle bottle) {
		Crate crate = new Crate();
		crate.setBottle(bottle);
		crate.setNoOfBottles(addCrateForm.getNoOfBottles());
		crate.setCratesInStock(addCrateForm.getCratesInStock());
		crate.setCrateBeverage(BeverageModelMapper.fromAddCrateForm(addCrateForm));
		return crate;
	}
}
