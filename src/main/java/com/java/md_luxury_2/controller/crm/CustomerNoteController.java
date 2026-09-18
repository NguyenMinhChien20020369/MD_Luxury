package com.java.md_luxury_2.controller.crm;

import com.java.md_luxury_2.dto.NoteRequest;
import com.java.md_luxury_2.security.CurrentUser;
import com.java.md_luxury_2.security.CurrentUserProvider;
import com.java.md_luxury_2.service.CustomerNoteService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/crm/customers/{customerId}/notes")
@PreAuthorize("hasAnyRole('SALE', 'MANAGER', 'ADMIN')")
public class CustomerNoteController {

    private final CustomerNoteService noteService;
    private final CurrentUserProvider currentUserProvider;

    public CustomerNoteController(CustomerNoteService noteService, CurrentUserProvider currentUserProvider) {
        this.noteService = noteService;
        this.currentUserProvider = currentUserProvider;
    }

    @PostMapping
    public void addNote(@PathVariable UUID customerId, @Valid @RequestBody NoteRequest request) {
        CurrentUser currentUser = currentUserProvider.get();
        noteService.add(currentUser, customerId, request.content());
    }
}