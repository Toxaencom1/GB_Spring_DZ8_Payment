package com.taxah.springdz8_payment.model;

import io.prometheus.client.Counter;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SuccessTransferMetric {
    private final Counter successTransferCounter = Counter.build()
            .name("success_transfer_metric_counter")
            .help("Success Transfer Metric")
            .register();

    public void incrementCounter() {
        successTransferCounter.inc();
    }

    public String getMetrics() {
        return "# TYPE success_transfer_metric_counter counter\n" +
                "# HELP success_transfer_metric_counter Success Transfer Metric\n" +
                "success_transfer_metric_counter " + successTransferCounter.get() + "\n" +
                "# EOF\n";
    }
}
