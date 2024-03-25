package com.mechamate.common;

import com.mechamate.dto.ErrorDTO;
import com.mechamate.service.LanguageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExpHandler {
    @Autowired
    LanguageManager lang;
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> runtimeExpHandler(Exception ignore) {
        return new ResponseEntity<>
                (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                        lang.get("error.api.notfound", "default"),
                        lang.get("error.api.notfound.help", "default")),
                        HttpStatus.NOT_IMPLEMENTED);
    }
}
