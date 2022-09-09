package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
  private final NoteMapper noteMapper;
  private final UserService userService;

  public NoteService(NoteMapper noteMapper, UserService userService) {
    this.noteMapper = noteMapper;
    this.userService = userService;
  }

  public List<Note> getNoteListByUserId() {
    return noteMapper.getNoteListByUserId(userService.getUserId());
  }

  public void createNotes(Note note) {
    note.setUserid(userService.getUserId());
    noteMapper.insert(note);
  }

  public void updateNotes(Note note) {
    note.setUserid(userService.getUserId());
    noteMapper.update(note);
  }

  public boolean deleteNotes(Integer noteId) {
    return noteMapper.delete(noteId);
  }
}
