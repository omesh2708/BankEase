package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer",example = "Omesh Padampalle"
    )
    @NotEmpty(message="Name can not be empty")
    @Size(min = 5,max = 30,message = "The length of customer name should be 5 to 30")
    private String name;

    @Schema(
            description = "Email of the customer",example = "padampalleomesh@gmail.com"
    )
    @NotEmpty(message = "Email address should not empty")
    @Email(message = "Email address should be valid value")
    private String email;

    @Schema(
            description = "Mobile number of the customer",example = "7083265941"
    )
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account Details of the customer"
    )
    private AccountsDto accountsDto;
}
