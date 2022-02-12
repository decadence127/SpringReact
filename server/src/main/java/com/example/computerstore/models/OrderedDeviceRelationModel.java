package com.example.computerstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "DeviceOrderRelation")
public class OrderedDeviceRelationModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long deviceId;

    @NotEmpty
    private String deviceName;

    @NotNull
    private String deviceDescription;

    private String deviceImg;

    @NotNull
    private Integer categoryType;

    @NotNull
    private BigDecimal devicePrice;

    @Min(0)
    private Integer deviceStock;

    @Min(1)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private CartModel cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderModel order;

    public OrderedDeviceRelationModel(DeviceModel device, Integer quantity) {
        this.deviceId = device.getDeviceId();
        this.deviceName = device.getDeviceTitle();
        this.deviceDescription = device.getDeviceDescription();
        this.deviceImg = device.getDeviceImg();
        this.categoryType = device.getCategoryType();
        this.devicePrice = device.getDevicePrice();
        this.deviceStock = device.getDeviceStock();
        this.quantity = quantity;
    }

}
