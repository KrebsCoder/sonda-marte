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
        return ResponseEntity.status(HttpStatus.CREATED).body(probeService.createProbe(probeDto));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> getProbeByName(@PathVariable(value = "name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(probeService.findProbeByName(name));
    }

    @GetMapping
    public ResponseEntity<Object> getAllProbes(){
        return ResponseEntity.status(HttpStatus.OK).body(probeService.findAll());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Object> deleteProbeByName(@PathVariable(value = "name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(probeService.deleteByName(name));
    }

    @PutMapping("/{name}")
    public ResponseEntity<Object> changeProbeName(@PathVariable(value = "name") String name,
                                                  @RequestBody @Valid ProbeDto probeDto){
        return ResponseEntity.status(HttpStatus.OK).body(probeService.changeProbeName(name, probeDto));
    }

    @PutMapping("/move")
    public ResponseEntity<Object> moveProbe(@RequestBody @Valid ProbeDto probeDto){
        return ResponseEntity.status(HttpStatus.OK).body(probeService.changeProbeDirection(probeDto));
    }
}
