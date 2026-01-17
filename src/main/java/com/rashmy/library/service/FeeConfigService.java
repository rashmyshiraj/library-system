package com.rashmy.library.service;

import com.rashmy.library.entity.FeeConfig;
import com.rashmy.library.exception.NotFoundException;
import com.rashmy.library.repository.FeeConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeConfigService {

    private final FeeConfigRepository feeConfigRepository;

    public FeeConfigService(FeeConfigRepository feeConfigRepository) {
        this.feeConfigRepository = feeConfigRepository;
    }

    public FeeConfig getFeeConfig() {
        List<FeeConfig> configs = feeConfigRepository.findAll();
        if (configs.isEmpty()) {
            throw new NotFoundException("Fee configuration not set");
        }
        return configs.get(0);
    }

    public FeeConfig createOrUpdate(FeeConfig feeConfig) {
        // If config exists, update it
        List<FeeConfig> configs = feeConfigRepository.findAll();
        if (!configs.isEmpty()) {
            FeeConfig existing = configs.get(0);
            existing.setNormalFeePerDay(feeConfig.getNormalFeePerDay());
            existing.setOverdueMultiplier(feeConfig.getOverdueMultiplier());
            return feeConfigRepository.save(existing);
        }
        return feeConfigRepository.save(feeConfig);
    }
}
