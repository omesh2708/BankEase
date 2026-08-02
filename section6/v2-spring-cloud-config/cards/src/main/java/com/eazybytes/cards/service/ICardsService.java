package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.CardsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

public interface ICardsService {
    void createCard(@Valid @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);

    CardsDto fetchCard(@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);

    boolean updateCard(@Valid CardsDto cardsDto);

    boolean deleteCard(@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);
}
