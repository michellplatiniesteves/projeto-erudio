package br.com.erudio.file.exporter.factory;

import br.com.erudio.exception.BadRequestException;
import br.com.erudio.file.exporter.MediaTypes;
import br.com.erudio.file.exporter.contract.FileExporter;
import br.com.erudio.file.exporter.impl.CsvExporter;
import br.com.erudio.file.exporter.impl.PdfExporter;
import br.com.erudio.file.exporter.impl.XlsxExporter;
import br.com.erudio.file.importer.contract.FileImporter;
import br.com.erudio.file.importer.impl.CsvImporter;
import br.com.erudio.file.importer.impl.Xlsxlmporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {
    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;
    public FileExporter getExporter(String acceptHeader) throws  Exception{
        if(acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)){
          return context.getBean(XlsxExporter.class);
        }else if(acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)){
            return context.getBean(CsvExporter.class);
        }else if(acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_PDF_VALUE)){
            return context.getBean(PdfExporter.class);
        }else{
         throw  new BadRequestException("Extensão invalida");
        }
    }
}
