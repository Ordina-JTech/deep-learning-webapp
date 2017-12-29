package nl.ordina.jtech.deeplearning.imagerecognitionwebapp;

import java.io.IOException;

import nl.ordina.jtech.deeplearning.imagerecognitionwebapp.storage.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {

    private final StorageService storageService;
    private ImageClassifier imageClassifier;

    @Autowired
    public FileUploadController(StorageService storageService, ImageClassifier imageClassifier) {
        this.storageService = storageService;
        this.imageClassifier = imageClassifier;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);

        return ResponseEntity.ok().body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        storageService.store(file);

        String predictions = "Error";
        try {
            predictions = imageClassifier.classify(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("image", "/files/" + file.getOriginalFilename());
        redirectAttributes.addFlashAttribute("predictions", predictions);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleStorageFileNotFound(Exception exc) {
        return ResponseEntity.notFound().build();
    }

}
