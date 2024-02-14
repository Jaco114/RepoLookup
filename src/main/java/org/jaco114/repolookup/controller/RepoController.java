package org.jaco114.repolookup.controller;

import jakarta.validation.Valid;
import org.jaco114.repolookup.dto.LookupRequest;
import org.jaco114.repolookup.dto.LookupResponse;
import org.jaco114.repolookup.service.LookupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class RepoController {

    private final LookupService lookupService;

    public RepoController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @PostMapping("/lookup")
    public ResponseEntity<List<LookupResponse>> lookup(@Valid @RequestBody LookupRequest lookupRequest) {
        return new ResponseEntity<>(lookupService.lookupUser(lookupRequest), HttpStatus.OK);
    }

}
