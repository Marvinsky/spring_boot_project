package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {

  private final CredentialService credentialService;

  public CredentialController(CredentialService credentialService) {
    this.credentialService = credentialService;
  }

  @PostMapping()
  public String createOrUpdateCredential(@ModelAttribute Credential credential, RedirectAttributes redirectAttributes) {
    if (credential.getCredentialId() == null) {
      credentialService.createCredential(credential);
      redirectAttributes.addFlashAttribute("success", "Create credentials successful!");
    } else {
      credentialService.updateCredential(credential);
      return "redirect:/home/result?success=true";
    }
    return "redirect:/home";
  }

  @GetMapping("/delete/{credentialId}")
  public String deleteCredential(@PathVariable("credentialId") Integer credentialId, RedirectAttributes redirectAttributes) {
    if (!credentialService.deleteCredential(credentialId)) {
      redirectAttributes.addFlashAttribute("error", "An error occurred while deleting the credentials!");
      return "redirect:/home";
    }
    return "redirect:/home/result?success=true";
  }
}
