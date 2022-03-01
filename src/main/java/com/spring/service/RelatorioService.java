package com.spring.service;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.spring.domain.Funcionario;
import com.spring.domain.OSReport;
import com.spring.service.exceptions.DataIntegratyViolationException;
import com.spring.service.exceptions.ObjectNotFoundException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class RelatorioService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String imprimirRelatorio(HttpServletRequest request, OSReport osReport) throws Exception {

		String dataInicio = osReport.getDataInicio();
		String dataFim = osReport.getDataFim();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("DATA_INICIO", dataInicio);
		params.put("DATA_FIM", dataFim);

		byte[] pdf = gerarRelatorio("relatorio-os-param", params, request.getServletContext());

		String base64pdf = "data:application/pdf;base64," + Base64.encodeBase64String(pdf);

		return base64pdf;

	}

	public String imprimirRelatorioId(HttpServletRequest request, OSReport osReport) throws Exception {

		Integer idOs = osReport.getId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idOs", idOs);

		byte[] pdf = gerarRelatorio("relatorio-os-recibo", params, request.getServletContext());

		String base64pdf = "data:application/pdf;base64," + Base64.encodeBase64String(pdf);

		return base64pdf;
	}

	public byte[] gerarRelatorio(String nomeRelatorio, Map<String, Object> params, ServletContext servletContext)
			throws Exception {

		// Obter conexão com o banco
		Connection connection = jdbcTemplate.getDataSource().getConnection();

		// Carregar o caminho do arquivo Jasper
		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		// Gerar o relatorio com os dados e conexão
		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, params, connection);

		// Exports para byte o PDF para fazer o download

		byte[] retorno = JasperExportManager.exportReportToPdf(print);

		connection.close();

		return retorno;
	}
}
