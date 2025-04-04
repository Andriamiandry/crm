package site.easy.to.build.crm.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Nullable;
import site.easy.to.build.crm.entity.OAuthUser;
import site.easy.to.build.crm.service.data.DataService;
import site.easy.to.build.crm.util.AuthenticationUtils;

@Controller
@RequestMapping("/employee/data")
public class DataController {
    private final AuthenticationUtils authenticationUtils;
    private final DataService dataService;

    public DataController( AuthenticationUtils authenticationUtils , DataService dataService) {
        this.authenticationUtils = authenticationUtils;
        this.dataService = dataService;
    }

    @GetMapping("/view")
    public String showProfileInfo(Model model, Authentication authentication) {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            model.addAttribute("oAuthUser", null);
            return "data-view";
        }

        OAuthUser oAuthUser = authenticationUtils.getOAuthUserFromAuthentication(authentication);


        model.addAttribute("oAuthUser", oAuthUser);
        return "data-view";
    }

    

    @GetMapping("/reset")
    public String showMyTickets(Model model, Authentication authentication){
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            model.addAttribute("oAuthUser", null);
            return "data-view";
        }

        OAuthUser oAuthUser = authenticationUtils.getOAuthUserFromAuthentication(authentication);
        String message_reset = this.dataService.resetData();

        model.addAttribute("oAuthUser", oAuthUser);
        model.addAttribute("messageReset" , message_reset);
        return "data-view";
    }
    
    @PostMapping("/import_csv")
    public String importCSV(@RequestParam("file_upload") String file_upload , Model model, Authentication authentication){
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            model.addAttribute("oAuthUser", null);
            return "data-view";
        }
        String messageUpload = this.dataService.importCSV(file_upload);
        OAuthUser oAuthUser = authenticationUtils.getOAuthUserFromAuthentication(authentication);

        model.addAttribute("oAuthUser", oAuthUser);
        model.addAttribute("messageUpload" , messageUpload);
        return "data-view";
    }

}
