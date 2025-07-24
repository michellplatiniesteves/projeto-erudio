package br.com.erudio.controller.Docs;

import br.com.erudio.data.dto.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@Tag(name = "File EndPoint")
public interface FileControllerDocs {
    UploadFileResponseDTO uploadFile(MultipartFile file);
    List<UploadFileResponseDTO> uploadMultipartFiles(MultipartFile[] files);
    ResponseEntity<Resource>downloadFile(String fileName, HttpServletRequest request);
}
