package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/note"})
public class NoteController {

  private final NoteService noteService;

  public NoteController(NoteService noteService) {
    this.noteService = noteService;
  }

  @PostMapping()
  public String createOrUpdateNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes) {
    if (note.getNoteDescription().length() > 100) {
      redirectAttributes.addFlashAttribute("error", "Description notes must not exceed 100 characters!");
      return "redirect:/home";
    }

    if (note.getNoteId() == null) {
      noteService.createNote(note);
      redirectAttributes.addFlashAttribute("success", "Create notes successful!");
    } else {
      noteService.updateNote(note);
      return "redirect:/home/result?success=true";
    }
    return "redirect:/home";
  }

  @GetMapping("/delete/{noteId}")
  public String deleteNote(@PathVariable("noteId") Integer noteId, RedirectAttributes redirectAttributes) {
    if (!noteService.deleteNote(noteId)) {
      redirectAttributes.addFlashAttribute("error", "An error occurred while deleting the note!");
      return "redirect:/home";
    }

    return "redirect:/home/result?success=true";
  }
}
