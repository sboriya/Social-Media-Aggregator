package com.social.aggregator.controller;

import com.social.aggregator.dto.ErrorResponseDTO;
import com.social.aggregator.dto.UserPreferenceRequestDTO;
import com.social.aggregator.dto.UserPreferenceResponseDTO;
import com.social.aggregator.exception.UserAlreadyExistsException;
import com.social.aggregator.exception.UserNotPresentException;
import com.social.aggregator.service.UserPreferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/preferences")
@Tag(name = "User Preference APIs")
public class UserPreferenceController {

    @Autowired
    private UserPreferenceService updateUserPreferenceService;

    @PatchMapping("/update")
    @Operation(summary = "Update User Preferences")
    public ResponseEntity<?> updateUserPreference(@RequestBody UserPreferenceRequestDTO preference) {
        try {
            UserPreferenceResponseDTO updatedPreference = updateUserPreferenceService.updateUserPreference(preference);
            return ResponseEntity.ok(updatedPreference);
        }  catch (UserAlreadyExistsException userAlreadyExistsException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDTO(userAlreadyExistsException.getMessage()));
        } catch (UserNotPresentException userNotPresentException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(userNotPresentException.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Create User With Preference")
    public ResponseEntity<?> createUserPreference(@RequestBody UserPreferenceRequestDTO preference) {
        try {
            UserPreferenceResponseDTO newPreference = updateUserPreferenceService.createUserPreference(preference);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPreference);
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDTO(userAlreadyExistsException.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    @GetMapping("/{username}")
    @Operation(summary = "Fetch User Preference")
    public ResponseEntity<?> getUserPreference(@PathVariable String username) {
        Optional<UserPreferenceResponseDTO> preferenceOpt = updateUserPreferenceService.getUserPreference(username);
        if (preferenceOpt.isPresent()) {
            return ResponseEntity.ok(preferenceOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("User preference not found for username: " + username));
        }
    }
}
