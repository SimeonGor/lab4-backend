package com.simeon.lab4.dto;

import com.simeon.lab4.entities.CheckResult;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public record AreaCheckResponse(
        BigDecimal x,
        BigDecimal y,
        BigDecimal r,
        boolean hit,
        long workingTime,
        LocalDateTime createdAt
)
        implements Serializable {
    @Serial
    private static final long serialVersionUID = 2524062387050598152L;

    public static AreaCheckResponse of(CheckResult checkResult) {
        return new AreaCheckResponse(checkResult.getX(),
                checkResult.getY(),
                checkResult.getR(),
                checkResult.isHit(),
                checkResult.getWorkingTime(),
                checkResult.getCreatedAt());
    }
}
