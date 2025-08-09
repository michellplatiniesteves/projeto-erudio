package br.com.erudio.file.exporter.impl;

import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.file.exporter.contract.FileExporter;
import br.com.erudio.file.importer.contract.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
@Component
public class CsvExporter implements FileExporter {

    @Override
    public Resource exportFile(List<PersonDTO> personDTOS) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        CSVFormat csvFormat = CSVFormat.Builder
                 .create()
                .setHeader("Id","Nome","sobreNome","email","endereco","genero","ativo")
                .setSkipHeaderRecord(true)
                .build();

        try (CSVPrinter csvPrinter  = new CSVPrinter(streamWriter,csvFormat)){
          for (PersonDTO dto : personDTOS){
              csvPrinter.printRecord(
                      dto.getId(), dto.getNome(), dto.getSobreNome(),
                      dto.getEmail(),dto.getEndereco(),dto.getGenero(),dto.getAtivo()
              );
          }
        }
        return new ByteArrayResource(outputStream.toByteArray());
    }

    @Override
    public Resource exportPerson(PersonDTO person) throws Exception {
        return null;
    }
}
