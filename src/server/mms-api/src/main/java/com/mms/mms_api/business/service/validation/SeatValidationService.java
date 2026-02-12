package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.SeatRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SeatValidationService {
    private final SeatRepository seatRepository;

    public boolean existsById(@NonNull UUID id) {
        return seatRepository.existsById(id);
    }
}
