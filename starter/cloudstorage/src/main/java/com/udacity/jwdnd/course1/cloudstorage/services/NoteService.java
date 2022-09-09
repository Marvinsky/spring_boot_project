package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  private final NoteMapper noteMapper;
  private final UserService userService;

  public NoteService(NoteMapper noteMapper,
      UserService userService) {
    this.noteMapper = noteMapper;
    this.userService = userService;
  }

  public List<Note> getNoteListByUserId() {
    return noteMapper.getNoteByUserId(userService.getUserId());
  }

  public void createNote(Note note) {
    note.setUserId(userService.getUserId());
    noteMapper.insert(note);
  }

  public void updateNote(Note note) {
    note.setUserId(userService.getUserId());
    noteMapper.update(note);
  }

  public boolean deleteNote(Integer noteId) {
    return  noteMapper.delete(noteId);
  }
}
