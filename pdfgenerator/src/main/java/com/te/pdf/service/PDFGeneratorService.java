package com.te.pdf.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.te.pdf.entity.Employee;
import com.te.pdf.repository.EmployeeRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PDFGeneratorService {

	private final EmployeeRepository employeeRepository;

//	@Value("${table_noOfColumns}")
//	private int noOfColumns;

//	@Value("${table.columnNames}")
//	private List<String> columnNames;
	
	int noOfColumns=5;
	List<String> columnNames =List.<String>of("Emp Id","Emp Name","Emp DOB","Emp ContactNo","Emp Email");

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(18);
		Paragraph paragraph = new Paragraph("TechnoElevate", fontTitle);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
		fontParagraph.setSize(12);
		Paragraph paragraph2 = new Paragraph("Employee Data:", fontParagraph);
		paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
		leaveEmptyLine(paragraph, 2);
		document.add(paragraph);
		document.add(paragraph2);
		createTable(document, noOfColumns);
		document.close();
	}

	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private void createTable(Document document, int noOfColumns) throws DocumentException {
		Paragraph paragraph = new Paragraph();
		leaveEmptyLine(paragraph, 1);
		document.add(paragraph);
		PdfPTable table = new PdfPTable(noOfColumns);
		for (int i = 0; i < noOfColumns; i++) {
			PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		table.setHeaderRows(1);
		getDbData(table);
		document.add(table);
	}

	private void getDbData(PdfPTable table) {
		List<Employee> list = employeeRepository.findAll();
		for (Employee employee : list) {
			table.setWidthPercentage(100);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(employee.getEmployeeId());
			table.addCell(employee.getEmployeeName());
			table.addCell(employee.getDateOfBirth().toString());
			table.addCell(employee.getContactNumber() + "");
			table.addCell(employee.getEmail());
		}
	}
}
