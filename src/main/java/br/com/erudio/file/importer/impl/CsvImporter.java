package br.com.erudio.file.importer.impl;

import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.file.importer.contract.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
@Component
public class CsvImporter implements FileImporter {
    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();
       Iterable<CSVRecord> csvRecords =csvFormat.parse(new InputStreamReader(inputStream));
        return parseRecordToPersonDtos(csvRecords);
    }

    private List<PersonDTO> parseRecordToPersonDtos(Iterable<CSVRecord> csvRecords) {
        List<PersonDTO> people = new ArrayList<>();
        for (CSVRecord record : csvRecords){
            PersonDTO dto = new PersonDTO();
            dto.setNome(record.get("nome"));
            dto.setSobreNome(record.get("sobreNome"));
            dto.setEmail(record.get("email"));
            dto.setGenero(record.get("genero"));
            dto.setAtivo(true);
            dto.setEndereco(record.get("endereco"));
            people.add(dto);
        }
        return people;
    }
}
