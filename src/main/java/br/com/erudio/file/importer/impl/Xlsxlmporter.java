package br.com.erudio.file.importer.impl;

import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.file.importer.contract.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class Xlsxlmporter implements FileImporter {
    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
        try(XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet xssfSheet =   workbook.getSheetAt(0);
            Iterator<Row> rowIterator = xssfSheet.iterator();
            if(rowIterator.hasNext()) rowIterator.next();
               return parseRowsToPersonDtos(rowIterator);
        }
    }

    private List<PersonDTO> parseRowsToPersonDtos(Iterator<Row> rowIterator) {
        List<PersonDTO> people = new ArrayList<>();
        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            if(isRowValid(row)){
                people.add(parsoRowToDto(row));
            }

        }
        return people;
    }

    private PersonDTO parsoRowToDto(Row row) {
        PersonDTO dto = new PersonDTO();
        dto.setNome(row.getCell(0).getStringCellValue());
        dto.setSobreNome(row.getCell(1).getStringCellValue());
        dto.setEmail(row.getCell(2).getStringCellValue());
        dto.setEndereco(row.getCell(5).getStringCellValue());
        dto.setGenero(row.getCell(3).getStringCellValue());
        dto.setAtivo(true);
        return dto;
    }

    private boolean isRowValid(Row row) {
        return  row.getCell(0)!=null && row.getCell(0).getCellType() != CellType.BLANK;
    }
}
