package com.akazimour.orderservice.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

public class ShipmentIdWithStatusDto {

    private UUID shipmentId = UUID.randomUUID();
    private String status;

    public ShipmentIdWithStatusDto() {
    }

    public ShipmentIdWithStatusDto(UUID shipmentId, String status) {
        this.shipmentId = shipmentId;
        this.status = status;
    }

    public UUID getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(UUID shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
