package com.thundershop.service.impl;

import com.thundershop.service.ReporteService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    DataSource datasource;

    @Override
    public ResponseEntity<Resource> generaReporte(String reporte, Map<String, Object> parametros, String tipo) throws IOException {

        try {
            //Estilo es si se muestra en pantalla o se descarga
            String estilo;
            if ("vPdf".equalsIgnoreCase(tipo)) {
                estilo = "inline; ";
            } else {
                estilo = "attachment; ";
            }

            //Se indica la ruta donde estan definidos los reportes
            String reportePath = "reportes";

            //Se define el flujo de salida donde queda el reporte ya generado
            ByteArrayOutputStream salida = new ByteArrayOutputStream();

            //Se construye el accesso al archivo del reporte 
            ClassPathResource fuente = new ClassPathResource(
                    reportePath + File.separator + reporte + ".jasper");

            //Se define un objeto para leer el archivo del reporte .jasper
            InputStream elReporte = fuente.getInputStream();

            //Se crea el reporte "Generado" a partir del archivo. jasper + los parametros+ la conexión a la BD
            var reporteJasper = JasperFillManager.fillReport(elReporte,
                    parametros,
                    datasource.getConnection());

            ///Aqui se define el tipo de archivo a responder
            MediaType mediaType = null;
            String archivoSalida = "";

            //Para recupera la info del reporte generado
            byte[] data;
            if (tipo != null) {
                switch (tipo) {
                    case "Pdf", "vPdf" -> {
                        JasperExportManager.exportReportToPdfStream(
                                reporteJasper, salida);
                        mediaType = MediaType.APPLICATION_PDF;
                        archivoSalida = reporte + ".pdf";
                        break;
                    }
                    case "Xls" -> {
                        JRXlsxExporter exportador = new JRXlsxExporter();
                        exportador.setExporterInput(
                                new SimpleExporterInput(
                                        reporteJasper));
                        exportador.setExporterOutput(
                                new SimpleOutputStreamExporterOutput(
                                        salida));
                        SimpleXlsxReportConfiguration configuracion
                                = new SimpleXlsxReportConfiguration();
                        configuracion.setDetectCellType(true);
                        configuracion.setCollapseRowSpan(true);
                        exportador.setConfiguration(configuracion);
                        exportador.exportReport();
                        mediaType = MediaType.APPLICATION_OCTET_STREAM;
                        archivoSalida = reporte + ".xlsx";
                    }
                    case "Csv" -> {
                        JRCsvExporter exportador = new JRCsvExporter();
                        exportador.setExporterInput(
                                new SimpleExporterInput(
                                        reporteJasper));
                        exportador.setExporterOutput(
                                new SimpleWriterExporterOutput(
                                        salida));
                        exportador.exportReport();
                        mediaType = MediaType.TEXT_PLAIN;
                        archivoSalida = reporte + ".csv";
                    }

                }
            }

            //Se pasa el reporte generado en memoria hacia un arreglo de bytes para enviarlo al usuario
            data = salida.toByteArray();

            //Se definenen los encabecados de la pagina del reporte
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Disposition", estilo + "filename=\"" + archivoSalida + "\"");

            return ResponseEntity.
                    ok().
                    headers(headers).
                    contentType(mediaType).
                    contentLength(data.length).
                    body(new InputStreamResource(new ByteArrayInputStream(data)));

        } catch (SQLException | JRException e) {
            e.printStackTrace();
        }

        return null;
    }

}
