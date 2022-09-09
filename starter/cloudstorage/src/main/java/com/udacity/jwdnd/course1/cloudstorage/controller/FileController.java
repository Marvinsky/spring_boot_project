package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping({"/file"})
public class FileController implements HandlerExceptionResolver {
  private final UserService userService;
  private final FileService fileService;

  public FileController(UserService userService, FileService fileService) {
    this.userService = userService;
    this.fileService = fileService;
  }

  @PostMapping("/upload")
  public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, RedirectAttributes attributes) throws IOException {

    // check if file is empty
    if (fileUpload.isEmpty()) {
      attributes.addFlashAttribute("info", "Please select a file to upload.");
      return "redirect:/home";
    }

    if (fileUpload.getSize() > 5 * 1024 * 1024) {
      attributes.addFlashAttribute("warning", "Your file cannot exceed 5MB!");
      return "redirect:/home";
    }

    // Get file name
    String fileName = StringUtils.cleanPath(fileUpload.getOriginalFilename());

    if (fileService.existsFilename(fileName)) {
      attributes.addFlashAttribute("warning", "The file \"" + fileName + "\" already exists!");
      return "redirect:/home";
    }

    Integer userId = userService.getUserId();
    File file = new File(fileName, fileUpload.getContentType(), String.valueOf(fileUpload.getSize()), userId, fileUpload.getBytes());

    fileService.createFiles(file);

    attributes.addFlashAttribute("success", "Your \"" + fileName + "\" file has been successfully uploaded!");

    return "redirect:/home";
  }

  @GetMapping("/delete/{fileId}")
  public String deleteFile(@PathVariable("fileId") Integer fileId, RedirectAttributes redirectAttrs) {

    if (!fileService.deleteFiles(fileId)) {
      redirectAttrs.addFlashAttribute("error", "An error occurred while deleting the file!");
      return "redirect:/home";
    }

    return "redirect:/home/result?success=true";
  }

  @GetMapping("/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
    File file = fileService.getFileByFileId(id);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"").body(file.getFileData());
  }

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception exc) {

    ModelAndView modelAndView = new ModelAndView("redirect:/home/errorUpload");
    if (exc instanceof MaxUploadSizeExceededException) {
      modelAndView.getModel().put("warning", "Your file size exceeds the 5MB limit!");
    }
    return modelAndView;
  }
}
