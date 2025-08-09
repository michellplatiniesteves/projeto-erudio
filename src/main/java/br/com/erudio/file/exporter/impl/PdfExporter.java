package br.com.erudio.file.exporter.impl;

import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.file.exporter.contract.FileExporter;
import br.com.erudio.model.Livro;
import br.com.erudio.service.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PdfExporter implements FileExporter {
    @Autowired
    private QrCodeService qrCodeService;
    @Override
    public Resource exportFile(List<PersonDTO> personDTOS) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/templates/pessoas.jrxml");
        if(inputStream == null){
            throw new Exception("Relario nao foi encontrado na pasta");
        }
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(personDTOS);
        Map<String, Object> parametros = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parametros,jrBeanCollectionDataSource);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }

    }

    @Override
    public Resource exportPerson(PersonDTO person) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/templates/pessoas_subrelatorio.jrxml");
        InputStream subinputStream = getClass().getResourceAsStream("/templates/sub_relatorio.jrxml");

        if(inputStream == null){
            throw new Exception("Relario nao foi encontrado na pasta");
        }
        if(subinputStream == null){
            throw new Exception("Relario nao foi encontrado na pasta");
        }
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperReport subjasperReport = JasperCompileManager.compileReport(subinputStream);

        JRBeanCollectionDataSource subjrBeanCollectionDataSource = new JRBeanCollectionDataSource(person.getLivros());

        InputStream qrcode = qrCodeService.gerarQrcode(person.getProfileUrl(),200,200);

        String path_sub_relatorio = getClass().getResource("/templates/sub_relatorio.jasper").getPath();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("pessoas_subrelatorio",qrcode);
        parametros.put("sub_relatorio",subjasperReport);
        parametros.put("sub_relatorio_data_source",subjrBeanCollectionDataSource);
        parametros.put("path_sub_relatorio",path_sub_relatorio);
        parametros.put("qr_code",qrcode);

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singletonList(person));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parametros,jrBeanCollectionDataSource);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }

    }


}
