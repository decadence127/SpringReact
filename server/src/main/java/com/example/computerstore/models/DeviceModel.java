package com.example.computerstore.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
@DynamicUpdate
@NoArgsConstructor
@Table(name = "devices")
public class DeviceModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deviceId;

    @NotNull
    private String deviceTitle;

    @NotNull
    private BigDecimal devicePrice;

    @NotNull
    @Min(0)
    private Integer deviceStock;

    private String deviceDescription;

    private String deviceImg;

    @ColumnDefault("0")
    private Integer deviceStatus;


    @ColumnDefault("0")
    private Integer categoryType;

    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date updateTime;

    @OneToOne(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RatingModel rating;

}
