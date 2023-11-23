package SpringBoot.Backend.Controller;

import SpringBoot.Backend.DTO.IconDTO;
import SpringBoot.Backend.Model.ResponseObject;
import SpringBoot.Backend.Service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/weather.api/v1/fileUpload")
public class FileUploadController {
    @Autowired
    private ImageStorageService storageService;

    @PostMapping("/{id}")
    ResponseEntity<ResponseObject> upLoadFile(@PathVariable String id, @RequestParam("file")MultipartFile file) {
        try {
            IconDTO iconDTO = storageService.storeFile(file, id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upload file successfully", new Object[] {iconDTO})
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", e.getMessage(), new Object[] {})
            );
        }
    }


    @GetMapping("/files/{fileName}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] image = storageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        }catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }





}
