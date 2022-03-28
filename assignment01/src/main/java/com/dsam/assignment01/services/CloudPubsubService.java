package com.dsam.assignment01.services;

import com.dsam.assignment01.models.Order;

import java.io.IOException;

public interface CloudPubsubService {
	void publishMessage(Order order) throws IOException, InterruptedException;
}
