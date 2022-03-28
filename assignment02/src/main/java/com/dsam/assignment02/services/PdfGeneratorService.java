package com.dsam.assignment02.services;

import com.dsam.assignment02.models.Order;
import com.dsam.assignment02.models.OrderItem;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class PdfGeneratorService {
	private final Logger logger;

	public PdfGeneratorService(Logger logger) {
		this.logger = logger;
	}

	public ByteArrayInputStream generate(Order order) {
		String name = order.getUser().getFullName();
		String email = order.getUser().getEmail();
		Double price = order.getOrderPrice();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfWriter writer = new PdfWriter(outputStream);
		PdfDocument pdfDocument = new PdfDocument(writer);
		Document document = new Document(pdfDocument);

		Paragraph paragraph;
		paragraph = getParagraph(String.format("Name: %s", name));
		document.add(paragraph);
		paragraph = getParagraph(String.format("Email: %s", email));
		document.add(paragraph);

		Table table = getTable(order.getOrderItems());
		document.add(table);

		paragraph = getParagraph(String.format("Total Price: %s", price.toString()));
		paragraph.setPaddingLeft(300F);
		document.add(paragraph);

		String shippingAddress = "Shipping Address:\n" + order.getUser().getAddress().getStandardAddress();
		paragraph = getParagraph(shippingAddress);
		document.add(paragraph);

		document.close();
		logger.info(String.format("Pdf generation complete for the order id = %s", order.getOrderId()));

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	private Table getTable(List<OrderItem> orderItems) {
		float[] tableColumnWidths = {150F, 150F, 150F};
		Table table = new Table(tableColumnWidths);
		java.util.List<String> tableHeaders = Arrays.asList("Item Name", "Quantity", "Price");

		for (String header : tableHeaders) {
			Cell cell = getCell(header);
			table.addCell(cell);
		}

		for (OrderItem orderItem : orderItems) {
			Cell name = getCell(orderItem.getOrderItemBeverage().getName());
			Cell quantity = getCell(orderItem.getQuantity().toString());
			Cell price = getCell(orderItem.getOrderItemPrice().toString());

			table.addCell(name);
			table.addCell(quantity);
			table.addCell(price);
		}
		return table;
	}

	private Cell getCell(String text) {
		Cell cell = new Cell();
		Paragraph paragraph = getParagraph(text);
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.add(paragraph);
		return cell;
	}

	private Paragraph getParagraph(String text) {
		Paragraph paragraph = new Paragraph();
		paragraph.add(new Text(text));
		return paragraph;
	}
}
