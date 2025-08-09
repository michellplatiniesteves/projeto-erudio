package br.com.erudio.file.exporter.impl;

import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.file.exporter.contract.FileExporter;
import br.com.erudio.file.importer.contract.FileImporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxExporter implements FileExporter {

    @Override
    public Resource exportFile(List<PersonDTO> personDTOS) throws Exception {
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Pessoas");
            Row headerRow =sheet.createRow(0);
            String [] headers ={"Id","Nome","sobreNome","email","endereco","genero","ativo"};
            for (int i = 0; i<headers.length  ; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(criarFormatacaoCell(workbook));
            }
            int rowIndex=1;
            for (PersonDTO dto : personDTOS){
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(dto.getId());
                row.createCell(1).setCellValue(dto.getNome());
                row.createCell(2).setCellValue(dto.getSobreNome());
                row.createCell(3).setCellValue(dto.getEmail());
                row.createCell(4).setCellValue(dto.getEndereco());
                row.createCell(5).setCellValue(dto.getGenero());
                row.createCell(6).setCellValue(dto.getAtivo()!=null && dto.getAtivo() ?"Ativo":"Inativo");

            }
            for (int i = 0; i <headers.length ; i++) {
                sheet.autoSizeColumn(i);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    @Override
    public Resource exportPerson(PersonDTO person) throws Exception {
        return null;
    }

    private CellStyle criarFormatacaoCell(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font =workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }
}
