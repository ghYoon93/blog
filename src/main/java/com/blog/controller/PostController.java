package com.blog.controller;

import com.blog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

    @GetMapping("/posts")
    public String get() {
        return "Hello World";
    }

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindResult ) {

        if ( bindResult.hasErrors() ) {
            List<FieldError> fieldErrors = bindResult.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField(); // title
            String errorMessage = firstFieldError.getDefaultMessage(); // 에러 메세지
            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);

            return error;
        }
        log.info("postCrete = {}", postCreate);
        return new HashMap<>();

    }
}
