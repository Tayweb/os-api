package com.spring.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.domain.OSReport;
import com.spring.service.RelatorioService;

@RestController
@RequestMapping(value = "/relatorios")
public class RelatorioController {

	@Autowired
	private RelatorioService relatorioService;

	@GetMapping(value = "/relatorio", produces = "application/text")
	public ResponseEntity<String> downloadRelatorio(HttpServletRequest request) throws Exception {
		byte[] pdf = relatorioService.gerarRelatorio("relatorio-os", new HashMap(), request.getServletContext());

		String base64pdf = "data:application/pdf;base64," + Base64.encodeBase64String(pdf);

		return new ResponseEntity<String>(base64pdf, HttpStatus.OK);
	}

	// Busca Por parametro
	@PostMapping(value = "/relatorioporparam", produces = "application/text")
	public ResponseEntity<String> downloadRelatorioParam(HttpServletRequest request, @RequestBody OSReport osReport)
			throws Exception {

		String base64pdf = relatorioService.imprimirRelatorio(request, osReport);
		return new ResponseEntity<String>(base64pdf, HttpStatus.OK);
	}

	@PostMapping(value = "/relatorioporid", produces = "application/text")
	public ResponseEntity<String> downloadRelatorioParamId(HttpServletRequest request, @RequestBody OSReport osReport)
			throws Exception {

		String base64pdf = relatorioService.imprimirRelatorioId(request, osReport);
		return new ResponseEntity<String>(base64pdf, HttpStatus.OK);
	}

}
