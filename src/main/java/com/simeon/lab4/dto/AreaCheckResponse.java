package com.simeon.lab4.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.simeon.lab4.entities.CheckResult;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AreaCheckResponse(
        BigDecimal x,
        BigDecimal y,
        BigDecimal r,
        boolean hit,
        long workingTime,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
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

    public static CheckResult toCheckResult(AreaCheckResponse areaCheckResponse) {
        return new CheckResult(areaCheckResponse.x(),
                areaCheckResponse.y(),
                areaCheckResponse.r(),
                areaCheckResponse.hit(),
                areaCheckResponse.workingTime(),
                areaCheckResponse.createdAt());
    }
}
