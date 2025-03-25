package site.easy.to.build.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.service.customer.CustomerImportService;
import site.easy.to.build.crm.service.data.ConfigDataService;

import java.util.Objects;

@Controller
@RequestMapping("/data/config")
public class ConfigDataController {
    private final ConfigDataService configDataService;
    private final CustomerImportService importService;


    @Autowired
    public ConfigDataController(ConfigDataService configDataService, CustomerImportService importService){
        this.configDataService = configDataService;
        this.importService = importService;
    }

    @GetMapping("/resetData")
    public String showResetDataPage() {
        return "configData/reset";
    }

    @PostMapping("/resetData")
    public String resetData(Model model) {
        try {
            String message = configDataService.resetData(); // Logique de réinitialisation
            model.addAttribute("message", message);
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred while resetting the data.");
        }
        return "configData/reset";
    }

    @GetMapping("/import-csv")
    public String importPage() {
        return "configData/import-csv";
    }

    @PostMapping("/customers/import")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   Model model) {

        if (file.isEmpty()) {
            model.addAttribute("error", "Le fichier est vide");
            return "configData/import-csv";
        }

        if (!Objects.equals(file.getContentType(), "text/csv")) {
            model.addAttribute("error", "Seuls les fichiers CSV sont acceptés");
            return "configData/import-csv";
        }

        try {
            CustomerImportService.ImportResult result = importService.importCustomersFromCsv(file);
            model.addAttribute("success",
                    String.format("Importation réussie: %d clients importés", result.getSavedCount()));
        } catch (CustomerImportService.ImportException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("errors", e.getErrors());
            model.addAttribute("fileName", file.getOriginalFilename());
        }

        return "configData/import-csv";
    }
}
