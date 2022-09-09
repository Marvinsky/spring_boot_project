package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/note"})
public class NoteController {
  private final NoteService noteService;

  public NoteController(NoteService noteService) {
    this.noteService = noteService;
  }

  @PostMapping()
  public String createOrUpdateNotes(@ModelAttribute Note note, RedirectAttributes redirectAttributes) {
    if (note.getNoteDescription().length() > 100) {
      redirectAttributes.addFlashAttribute("error", "Description notes must not exceed 100 characters!");
      return "redirect:/home";
    }
    if (note.getNoteId() == null) {
      noteService.createNotes(note);
      redirectAttributes.addFlashAttribute("success", "Create notes successful!");
    } else {
      noteService.updateNotes(note);
      return "redirect:/home/result?success=true";
    }
    return "redirect:/home";
  }


  @GetMapping("/delete/{noteId}")
  public String deleteNotes(@PathVariable("noteId") Integer noteId, RedirectAttributes redirectAttrs) {

    if (!noteService.deleteNotes(noteId)) {
      redirectAttrs.addFlashAttribute("error", "An error occurred while deleting the note!");
      return "redirect:/home";
    }

    return "redirect:/home/result?success=true";
  }
}
