package br.com.erudio.file.exporter.contract;

import br.com.erudio.data.dto.v1.PersonDTO;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.List;

public interface FileExporter {
    Resource exportFile(List<PersonDTO> personDTOS) throws  Exception;
    Resource exportPerson(PersonDTO person) throws  Exception;
}
