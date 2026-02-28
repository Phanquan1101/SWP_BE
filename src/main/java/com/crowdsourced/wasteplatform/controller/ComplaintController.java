package com.crowdsourced.wasteplatform.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crowdsourced.wasteplatform.dto.complaint.request.CreateComplaintRequest;
import com.crowdsourced.wasteplatform.dto.complaint.request.ResolveComplaintRequest;
import com.crowdsourced.wasteplatform.dto.complaint.response.ComplaintResponse;
import com.crowdsourced.wasteplatform.entity.ComplaintCategory;
import com.crowdsourced.wasteplatform.entity.ComplaintStatus;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.complaint.ComplaintService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintService complaintService;

    @Operation(summary = "List active complaints")
    @GetMapping("/complaints")
    public ResponseEntity<ApiResponse<List<ComplaintResponse>>> getComplaints(
            Principal principal,
            @RequestParam(required = false) ComplaintCategory category,
            @RequestParam(required = false) ComplaintStatus status) {
        List<ComplaintResponse> data = complaintService.getComplaints(principal, category, status);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @Operation(summary = "Update complaint")
    @PutMapping("/citizen/complaint/{id}")
    public ResponseEntity<ApiResponse<ComplaintResponse>> updateComplaint(
        @PathVariable("id") UUID id,
        Principal principal,
        @Valid @RequestBody CreateComplaintRequest request
    ) {
        ComplaintResponse response = complaintService.updatedComplaint(principal, id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Create complaint")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Created",
            content = @Content(schema = @Schema(implementation = ComplaintResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PostMapping("/citizen/complaint")
    public ResponseEntity<ApiResponse<ComplaintResponse>> createComplaint(@RequestBody CreateComplaintRequest request, Principal principal) {
        ComplaintResponse response = complaintService.createdComplaint(principal ,request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Processing complaint")
    @PatchMapping("/admin/complaint/{id}/processing")
    public ResponseEntity<ApiResponse<ComplaintResponse>> processing(
        @PathVariable("id") UUID id,
        @Valid @RequestBody ResolveComplaintRequest request, 
        @RequestParam(required = false) ComplaintStatus status) {
        ComplaintResponse response = complaintService.processingComplaint(id, request, status);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
