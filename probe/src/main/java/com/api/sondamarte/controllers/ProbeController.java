package com.api.sondamarte.controllers;


import com.api.sondamarte.dtos.ProbeDto;
import com.api.sondamarte.services.ProbeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/probe")
public class ProbeController {

    final ProbeService probeService;

    public ProbeController(ProbeService probeService) {
        this.probeService = probeService;
    }

    @PostMapping
    public ResponseEntity<Object> createProbe(@RequestBody @Valid ProbeDto probeDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(probeService.save(probeService.createProbe(probeDto)));
    }
}
