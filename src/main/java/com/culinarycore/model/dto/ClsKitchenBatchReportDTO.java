package com.culinarycore.model.dto;
public class ClsKitchenBatchReportDTO {
    private String kitchenName;
    private String batchName;
    private int consumedQuantity;

    public ClsKitchenBatchReportDTO(String kitchenName, String batchName, int quantity) {
        this.kitchenName = kitchenName;
        this.batchName = batchName;
        this.consumedQuantity = quantity;
    }

    public String getKitchenName() { return kitchenName; }
    public String getBatchName() { return batchName; }
    public int getConsumedQuantity() { return consumedQuantity; }
}