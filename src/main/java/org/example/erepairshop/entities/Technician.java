package org.example.erepairshop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Technician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false)
    private String name;

    @Column(unique = false, nullable = true)
    private String specialization;

    @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL)
    @JsonManagedReference("techician-ro")
    private List<RepairOrder> repairOrders;
}
