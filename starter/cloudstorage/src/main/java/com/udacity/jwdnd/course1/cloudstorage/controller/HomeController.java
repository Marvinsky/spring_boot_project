package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

  private final FileService fileService;
  private final NoteService noteService;
  private final CredentialService credentialService;

  public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService) {
    this.fileService = fileService;
    this.noteService = noteService;
    this.credentialService = credentialService;
  }

  @GetMapping()
  public String getHomePage(Model model) {

    List<File> fileList = fileService.getFileListByUserId();
    boolean fileListEmpty = fileList.isEmpty();
    model.addAttribute("fileListEmpty", fileListEmpty);
    model.addAttribute(fileList);

    List<Note> noteList = noteService.getNoteListByUserId();
    boolean noteListEmpty = noteList.isEmpty();
    model.addAttribute("noteListEmpty", noteListEmpty);
    model.addAttribute(noteList);

    List<CredentialDTO> credentialDTOList = credentialService.getCredentialListByUserId();
    boolean credentialListEmpty = credentialDTOList.isEmpty();
    model.addAttribute("credentialListEmpty", credentialListEmpty);
    model.addAttribute(credentialDTOList);

    return "home";
  }

  @GetMapping("/result")
  public String loginView(@RequestParam("success") String success, Model model) {
    model.addAttribute("success", success);
    return "result";
  }

  @GetMapping("/errorUpload")
  public String errorUpload(@RequestParam("warning") String warning, Model model) {
    model.addAttribute("warning", warning);
    return "result";
  }


}
