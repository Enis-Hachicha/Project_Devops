package org.example.erepairshop.repositores;

import org.example.erepairshop.entities.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Page<Device> findBySerialNumber(String serialNumber, Pageable p);
    Page<Device> findByOwner_Id(Long ownerId, Pageable p);
}
