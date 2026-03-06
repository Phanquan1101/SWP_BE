package com.crowdsourced.wasteplatform.service.complaint;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.complaint.request.ComplaintNotification;
import com.crowdsourced.wasteplatform.dto.complaint.request.CreateComplaintRequest;
import com.crowdsourced.wasteplatform.dto.complaint.request.ResolveComplaintRequest;
import com.crowdsourced.wasteplatform.dto.complaint.response.ComplaintResponse;
import com.crowdsourced.wasteplatform.entity.Complaint;
import com.crowdsourced.wasteplatform.entity.ComplaintCategory;
import com.crowdsourced.wasteplatform.entity.ComplaintStatus;
import com.crowdsourced.wasteplatform.entity.Notification;
import com.crowdsourced.wasteplatform.entity.NotificationChannel;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserNotification;
import com.crowdsourced.wasteplatform.entity.UserType;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.ComplaintMapper;
import com.crowdsourced.wasteplatform.repository.ComplaintRepository;
import com.crowdsourced.wasteplatform.repository.NotificationRepository;
import com.crowdsourced.wasteplatform.repository.UserNotificationRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.service.email.EmailService;
import com.crowdsourced.wasteplatform.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final SecurityUtil securityUtil;
    private final ComplaintMapper complaintMapper;
    private final EmailService emailService;
    private final SimpMessagingTemplate messagingTemplate;

    public ComplaintResponse createdComplaint(Principal principal, CreateComplaintRequest request){
        User user = securityUtil.getLoginUser(principal);
        Complaint complaint = Complaint.builder()
            .complainantId(user.getId())
            .complainant(user)
            .reportId(null)
            .report(null)
            .category(ComplaintCategory.OTHER)
            .description(request.getDescription())
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .status(ComplaintStatus.IN_REVIEW)
            .build();
        Complaint saved = complaintRepository.save(complaint);

        messagingTemplate.convertAndSend(
        "/topic/admin-notifications",
        new ComplaintNotification(
                "NEW_COMPLAINT",
                "New complaint created",
                saved.getId()
        )
    );

        return complaintMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public PageResponse<ComplaintResponse> getComplaints(Principal principal, Pageable pageable, 
        ComplaintCategory category, ComplaintStatus status){
            User user = securityUtil.getLoginUser(principal);
            Page<ComplaintResponse> page =  complaintRepository.searchComplaints("admin@example.com".equals(user.getEmail()) && UserType.ADMIN.equals(user.getUserType()) ? null : user.getId(), pageable, category, status)
                    .map(complaintMapper::toResponse);
            return PageResponse.from(page);
    }

    @Transactional
    public ComplaintResponse processingComplaint(UUID complaintId, ResolveComplaintRequest request, ComplaintStatus status){
        Optional<Complaint> complaintOptional = complaintRepository.findById(complaintId);
        User user = userRepository.findByEmail("admin@example.com").orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Email is not found."));
        if(complaintOptional.isPresent()){
            Complaint complaint = complaintOptional.get();
            complaint.setStatus(status);
            complaint.setResolvedAt(Instant.now());
            complaint.setResolvedBy(user.getId());
            complaint.setResolutionNote(request.getResolutionNote());
            Complaint saved = complaintRepository.save(complaint);

            Notification notification = Notification.builder()
            .eventType("COMPLAINT_RESOLVED")
            .reportId(null)
            .report(null)
            .complaintId(saved.getId())
            .complaint(saved)
            .title("Your Complaint Has Been Resolved")
            .body("Your complaint has been reviewed and successfully resolved by the administration.")
            .build();
            Notification notificationSaved = notificationRepository.save(notification);

            UserNotification userNotification = UserNotification.builder()
            .notification(notificationSaved)
            .notificationId(notificationSaved.getId())
            .user(complaint.getComplainant())
            .userId(complaint.getComplainant().getId())
            .read(false)
            .deliveryChannel(NotificationChannel.IN_APP)
            .build();
            userNotificationRepository.save(userNotification);

            String subject;
            String content;
            switch (status) {
        case RESOLVED:
            subject = "Your Complaint Has Been Resolved";
            content = "Your complaint has been successfully resolved.";
            break;

        case REJECTED:
            subject = "Your Complaint Has Been Reviewed";
            content = "After review, your complaint has been rejected.";
            break;

        case IN_REVIEW:
            subject = "Your Complaint Is Under Review";
            content = "Your complaint is currently being reviewed by our administration team.";
            break;

        default:
            subject = "Complaint Status Update";
            content = "Your complaint status has been updated.";
    }

    String emailContent =
        "Dear User,\n\n" +

        "We would like to inform you that there has been an update regarding your submitted complaint.\n\n" +

        content + "\n\n" +

        "Resolution Details:\n" +
        request.getResolutionNote() + "\n\n" +

        "If you require further clarification or believe additional action is needed, " +
        "please feel free to contact our support team through the platform.\n\n" +

        "We sincerely appreciate your effort in helping us maintain environmental quality " +
        "and improve our waste management services.\n\n" +

        "Best regards,\n" +
        "Waste Management Support Team\n" +
        "Crowdsourced Waste Platform";

            emailService.sendComplaintResolvedEmail(
            complaint.getComplainant().getEmail(),
            subject,
            emailContent
        );

        return complaintMapper.toResponse(saved);
        }else{
            throw new AppException(ErrorCode.NOT_FOUND, "Complaint not found.");
        }
    }

    public ComplaintResponse updatedComplaint(Principal principal, UUID complaintId, CreateComplaintRequest request){
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Complaint is not found."));
        complaintMapper.updateEntityFromRequest(request, complaint);
        return complaintMapper.toResponse(complaintRepository.save(complaint));
    }
}


