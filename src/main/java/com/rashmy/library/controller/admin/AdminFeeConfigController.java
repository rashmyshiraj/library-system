package com.rashmy.library.controller.admin;

import com.rashmy.library.entity.FeeConfig;
import com.rashmy.library.service.FeeConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/fee-config")
@PreAuthorize("hasRole('ADMIN')")
public class AdminFeeConfigController {

    private final FeeConfigService feeConfigService;

    public AdminFeeConfigController(FeeConfigService feeConfigService) {
        this.feeConfigService = feeConfigService;
    }

    // Get current fee config
    @GetMapping
    public ResponseEntity<FeeConfig> getFeeConfig() {
        return ResponseEntity.ok(feeConfigService.getFeeConfig());
    }

    // Create or update fee config
    @PutMapping
    public ResponseEntity<FeeConfig> updateFeeConfig(
            @RequestBody FeeConfig feeConfig
    ) {
        return ResponseEntity.ok(feeConfigService.createOrUpdate(feeConfig));
    }
}
