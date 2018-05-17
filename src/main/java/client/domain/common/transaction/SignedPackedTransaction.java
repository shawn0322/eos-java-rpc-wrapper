package client.domain.common.transaction;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SignedPackedTransaction extends PackedTransaction {

    private List<String> signatures;

    private String maxCpuUsageMs;

    private String[] transactionExtensions;

    public String[] getTransactionExtensions() {
        return transactionExtensions;
    }

    @JsonProperty("transaction_extensions")
    public void setTransactionExtensions(String[] transactionExtensions) {
        this.transactionExtensions = transactionExtensions;
    }

    public String getMaxCpuUsageMs() {
        return maxCpuUsageMs;
    }

    @JsonProperty("max_cpu_usage_ms")
    public void setMaxCpuUsageMs(String maxCpuUsageMs) {
        this.maxCpuUsageMs = maxCpuUsageMs;
    }

    public SignedPackedTransaction(){

    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }
}
