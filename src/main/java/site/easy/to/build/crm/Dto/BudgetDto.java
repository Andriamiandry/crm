package site.easy.to.build.crm.Dto;

import jakarta.validation.constraints.*;
import site.easy.to.build.crm.customValidations.customer.UniqueEmail;

import java.math.BigDecimal;

public class BudgetDto {

    @NotBlank(message = "Customer email is required")
    @Email(message = "Please provide a valid email address")
    @UniqueEmail
    private String customerEmail;

    @NotNull(message = "Budget cannot be null")
    @DecimalMin(value = "0.00", inclusive = true, message = "Budget must be greater than or equal to 0.00")
    @DecimalMax(value = "9999999.99", inclusive = true, message = "Budget must be less than or equal to 9,999,999.99")
    private BigDecimal budget;

    public BudgetDto(String customerEmail, BigDecimal budget) {
        this.customerEmail = customerEmail;
        this.budget = budget;
    }

    // Getters and Setters
    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}