package com.dsam.assignment01.mappers;

import com.dsam.assignment01.forms.AddBottleForm;
import com.dsam.assignment01.forms.AddCrateForm;
import com.dsam.assignment01.models.Beverage;

public class BeverageModelMapper {
	public static Beverage fromAddCrateForm(AddCrateForm addCrateForm) {
		Beverage beverage = new Beverage();
		beverage.setName(addCrateForm.getName());
		beverage.setBeveragePic(addCrateForm.getBeveragePic());
		beverage.setBeveragePrice(addCrateForm.getBeveragePrice());
		beverage.setAlcoholPercent(addCrateForm.getAlcoholPercent());
		return beverage;
	}

	public static Beverage fromAddBottleForm(AddBottleForm addbottleForm) {
		Beverage beverage = new Beverage();
		beverage.setName(addbottleForm.getName());
		beverage.setBeveragePic(addbottleForm.getBeveragePic());
		beverage.setBeveragePrice(addbottleForm.getBeveragePrice());
		beverage.setAlcoholPercent(addbottleForm.getAlcoholPercent());
		return beverage;
	}
}
