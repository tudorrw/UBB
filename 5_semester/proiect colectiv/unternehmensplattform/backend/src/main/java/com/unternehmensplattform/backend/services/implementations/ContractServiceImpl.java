package com.unternehmensplattform.backend.services.implementations;

import com.unternehmensplattform.backend.repositories.ContractRepository;
import com.unternehmensplattform.backend.services.interfaces.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;


}
