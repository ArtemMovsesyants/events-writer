package com.amovsesyants.events.writer.controller;

import com.amovsesyants.events.writer.dto.UserActionRequest;
import com.amovsesyants.events.writer.service.UserActionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/actions")
@RequiredArgsConstructor
public class UserActionController {

    private final UserActionService userActionService;

    @PostMapping("/batch")
    public ResponseEntity<Void> saveBatch(@RequestBody @Valid List<UserActionRequest> requests) {
        userActionService.save(requests);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}


