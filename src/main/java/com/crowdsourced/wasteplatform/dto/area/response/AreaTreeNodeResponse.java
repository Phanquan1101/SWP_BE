package com.crowdsourced.wasteplatform.dto.area.response;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AreaTreeNodeResponse {
    String id;
    String name;
    List<AreaTreeNodeResponse> children;
}
