package com.eazybytes.loans.service;

import com.eazybytes.loans.dto.LoansDto;
import jakarta.validation.constraints.Pattern;

public interface ILoansService {

    public void createLoan(String mobileNumber);

    LoansDto fetchLoan(@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoan(@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);
}
