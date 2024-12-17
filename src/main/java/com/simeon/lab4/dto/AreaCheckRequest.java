package com.simeon.lab4.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.QueryParam;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class AreaCheckRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1116664443145287831L;

    @NotNull(message = "x cannot be null")
    @Range(min=-5, max=3, message="x must be between -5 and 3")
    private BigDecimal x;

    @NotNull(message = "y cannot be null")
    @Range(min=-3, max=5, message="y must be between -3 and 5")
    private BigDecimal y;

    @NotNull(message = "r cannot be null")
    @Range(min=-5, max=3, message="r must be between -5 and 3")
    private BigDecimal r;

    public AreaCheckRequest() {}

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }
}
