package com.social.aggregator.controller;


import com.social.aggregator.dto.ErrorResponseDTO;
import com.social.aggregator.dto.InfluencerRequestDTO;
import com.social.aggregator.dto.InfluencerResponseDTO;
import com.social.aggregator.exception.UserAlreadyExistsException;
import com.social.aggregator.service.InfluencerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/influencers")
@Tag(name = "Influencer APIs")
public class InfluencerController {

    @Autowired
    private InfluencerService influencerService;

    @PostMapping("/create")
    @Operation(summary = "Create Influencer")
    public ResponseEntity<?> createInfluencer(@RequestBody InfluencerRequestDTO influencerRequest) {
        try {
            InfluencerResponseDTO newInfluencer = influencerService.createInfluencer(influencerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(newInfluencer);
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDTO(userAlreadyExistsException.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fetch Influencer by ID")
    public ResponseEntity<?> getInfluencer(@PathVariable Long id) {
        Optional<InfluencerResponseDTO> influencerOpt = influencerService.getInfluencer(id);
        if (influencerOpt.isPresent()) {
            return ResponseEntity.ok(influencerOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("Influencer not found for ID: " + id));
        }
    }
}

