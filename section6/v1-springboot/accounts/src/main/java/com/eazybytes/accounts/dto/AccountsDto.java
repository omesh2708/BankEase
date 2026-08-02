package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Schema(
        name = "Account",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @Schema(
            description = "Account number of the OmeshBank "
    )
    @NotEmpty(message = "Account number should not empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account type of the OmeshBank ",example = "Saving"
    )
    @NotEmpty(message = "Account type should not be empty")
    private String accountType;


    @Schema(
            description = "OmeshBank Branch address "
    )
    @NotEmpty(message = "Branch address should not be null or empty")
    private String branchAddress;
}
