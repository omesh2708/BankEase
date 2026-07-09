package com.eazybytes.accounts.controller;


import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;

import com.eazybytes.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
@Tag(
        name = "CRUD REST APIs for the Accounts",
        description = "CRUD REST APIs in Accounts to CREATE,UPDATE,FETCH and DELETE accounts details"
)
public class AccountsController {


    private IAccountsService iAccountsService;
    public AccountsController(IAccountsService iAccountsService) {
        this.iAccountsService = iAccountsService;
    }

    @Operation(
            summary = "Create Account REST API",
            description = "Rest api to create new Customer and Account in OmeshBank"
    )
    @ApiResponses({@ApiResponse(
            responseCode = "201",
            description = "HTTP Status Created"),

            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
            }
    )
    @PostMapping("/create")
public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
}

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "Rest api to fetch  Customer and Account details based on mobile number"
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
            }
    )

@GetMapping("/fetch")
public ResponseEntity<CustomerDto> fetchAccountsDetails(@RequestParam
                                                        @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                        String mobileNumber){
CustomerDto customerDto=iAccountsService.fetchAccountDetails(mobileNumber);

return ResponseEntity.status(HttpStatus.OK).body(customerDto);

}

    @Operation(
            summary = "Update Account Details REST API",
            description = "Rest api to Update  Customer and Account details based on account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Exception failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    })

@PutMapping("/update")
    public ResponseEntity<ResponseDto>updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated=iAccountsService.updateAccount(customerDto);

        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
        }
}

    @Operation(
            summary = "Delete Account and Customer Details REST API",
            description = "Rest api to Delete  Customer and Account details based on Mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Exception failed"
            ),

            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema =@Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
@DeleteMapping("/delete")
    public ResponseEntity<ResponseDto>deleteAccountDetails(@RequestParam
                                                           @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                           String mobileNumber){
        boolean isDelete=iAccountsService.deleteAccount(mobileNumber);
        if(isDelete){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
        }
}

}
