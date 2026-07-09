package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExitsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountsServiceImpl implements IAccountsService {

    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public void createAccount(CustomerDto customerDto){

        Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());
        Optional<Customer>optionalCustomer= customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExitsException("Customer Already present with given mobile Number "+customerDto.getMobileNumber());
        }

       Customer savedCustomer= customerRepository.save(customer);
       accountsRepository.save(createNewAccounts(savedCustomer));
    }

    private Accounts createNewAccounts(Customer customer){
        Accounts newAccounts=new Accounts();

        newAccounts.setCustomerId(customer.getCustomerId());
        long randomAccNumber=1000000000L+   new Random().nextInt(900000000);

        newAccounts.setAccountNumber(randomAccNumber);
        newAccounts.setAccountType(AccountsConstants.SAVING);
        newAccounts.setBranchAddress(AccountsConstants.ADDRESS);


        return newAccounts;
    }

    public CustomerDto fetchAccountDetails(String mobileNumber){
       Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
               ()->new ResourceNotFoundException("Customer","MobileNumber",mobileNumber)
       );

        Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account","CustomerId",customer.getCustomerId().toString())
        );

       CustomerDto customerDto= CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        AccountsDto accountsDto= AccountsMapper.mapToAccountDto(accounts,new AccountsDto());
       customerDto.setAccountsDto(accountsDto);

        return customerDto;
    }

    public boolean updateAccount(CustomerDto customerDto){

        boolean isUpdated=false;
         AccountsDto accountsDto=customerDto.getAccountsDto();
         if(accountsDto !=null) {
             Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                     () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
             );
             AccountsMapper.mapToAccounts(accounts, accountsDto);

             accounts=accountsRepository.save(accounts);
             Long customerId=accounts.getCustomerId();

             Customer customer =customerRepository.findById(customerId).orElseThrow(()->
                     new ResourceNotFoundException("Customer","CustomerId",customerId.toString()));

             CustomerMapper.mapToCustomer(customerDto,customer);

             customerRepository.save(customer);
             isUpdated=true;

         }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
       boolean isDelete=false;
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Customer","MobileNumber",mobileNumber));

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}
