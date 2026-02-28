package com.crowdsourced.wasteplatform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.crowdsourced.wasteplatform.dto.complaint.request.CreateComplaintRequest;
import com.crowdsourced.wasteplatform.dto.complaint.response.ComplaintResponse;
import com.crowdsourced.wasteplatform.entity.Complaint;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComplaintMapper {
    ComplaintResponse toResponse(Complaint complaint);

    void updateEntityFromRequest(CreateComplaintRequest request,
                                 @MappingTarget Complaint complaint);
}
