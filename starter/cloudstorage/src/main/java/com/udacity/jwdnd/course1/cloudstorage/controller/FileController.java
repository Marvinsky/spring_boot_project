package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    if (fileUpload.isEmpty()) {
      attributes.addFlashAttribute("info", "Please select a file to upload");
      return "redirect:/home";
    }

    if (fileUpload.getSize() > 5 * 1024 * 1024) {
      attributes.addFlashAttribute("warning", "Your file cannot exceed 5MB!");
      return "redirect:/home";
    }

    String fileName = StringUtils.cleanPath(fileUpload.getOriginalFilename());
    if (fileService.existsFileName(fileName)) {
      attributes.addFlashAttribute("warning",  String.format("The file {} already exists", fileName));
      return "redirect:/home";
    }

    Integer userId = userService.getUserId();
    File file = File.builder()
        .fileName(fileName)
        .contentType(fileUpload.getContentType())
        .fileSize(String.valueOf(fileUpload.getSize()))
        .userId(userId)
        .fileData(fileUpload.getBytes())
        .build();

    fileService.createFile(file);
    attributes.addFlashAttribute("success", String.format("Your {} file has been successfully uploaded!"));

    return "redirect:/home";
  }

  @GetMapping("/delete/{fileId}")
  public String deleteFile(@PathVariable("fileId") Integer fileId, RedirectAttributes redirectAttributes) {
    if (!fileService.deleteFile(fileId)) {
      redirectAttributes.addFlashAttribute("error", "An error occurred while deleting the file!");
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
  public ModelAndView resolveException(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object o, Exception e) {
    ModelAndView modelAndView = new ModelAndView("redirect:/home/errorUpload");
    if (e instanceof MaxUploadSizeExceededException) {
      modelAndView.getModel().put("warning", "Your file size exceeds the 5MB limit!");
    }
    return modelAndView;
  }
}
