package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController implements ErrorController {

  @GetMapping("/error")
  public String error() {
    return "error";
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
