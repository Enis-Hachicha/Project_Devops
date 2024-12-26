//package org.example.erepairshop.entities;
//
//import jakarta.persistence.*;
//import jdk.jfr.Enabled;
//import lombok.Data;
//
////@Data
////@Entity
////@Table(name = "repair_order_parts")
//public class RepairOrderPart {
//    @EmbeddedId
//    private RepairOrderPartId id;
//
//    @ManyToOne
//    @MapsId("repairOrderId")
//    @JoinColumn(name = "repair_order_id")
//    private RepairOrder repairOrder;
//
//    @ManyToOne
//    @MapsId("partId")
//    @JoinColumn(name = "part_id")
//    private Part part;
//
//    private Integer quantityUsed;
//}