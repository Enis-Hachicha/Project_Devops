package org.example.erepairshop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("device-owner")
    @JoinColumn(name = "customer_id")
    private Customer owner;

    @OneToMany(mappedBy = "device")
    @JsonManagedReference("device-order")
    private List<RepairOrder> repairOrders;
}
