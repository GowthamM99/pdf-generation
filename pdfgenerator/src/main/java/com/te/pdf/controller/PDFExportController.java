package com.te.pdf.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.te.pdf.dto.EmployeeDTO;
import com.te.pdf.service.EmployeeService;
import com.te.pdf.service.PDFGeneratorService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PDFExportController {
	
	private final PDFGeneratorService pdfGeneratorService;
	private final EmployeeService employeeService;
	
	@GetMapping(path = "/pdf/generate")
	public void generatePDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
		String currentDateTime=dateFormatter.format(new Date());
		String headerKey="Content-Disposition";
		String headerValue="inline; filename=pdf_"+currentDateTime+".pdf";
		response.setHeader(headerKey, headerValue);
		pdfGeneratorService.export(response);
	}
	
	@PostMapping(path = "/save")
	public String saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
		String empId= employeeService.saveEmployee(employeeDTO);
		return empId;
	}
}
