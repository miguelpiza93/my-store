package com.mapiz.mystore;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class PingController {

  @GetMapping("ping")
  public @ResponseBody ResponseEntity<String> ping() {
    return ResponseEntity.ok("pong");
  }
}
