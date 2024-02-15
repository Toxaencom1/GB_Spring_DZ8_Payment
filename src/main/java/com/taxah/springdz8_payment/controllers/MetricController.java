package com.taxah.springdz8_payment.controllers;

import com.taxah.springdz8_payment.model.SuccessTransferMetric;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MetricController {

    private final SuccessTransferMetric successTransferMetric;

    @GetMapping("/custom-metrics")
    public String getCustomMetrics() {
        return successTransferMetric.getMetrics();
    }

}
