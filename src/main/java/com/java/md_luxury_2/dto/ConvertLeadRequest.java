package com.java.md_luxury_2.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ConvertLeadRequest(
        @NotNull(message = "Vui lòng chọn nhân viên phụ trách")
        UUID assignToUserId
) {}