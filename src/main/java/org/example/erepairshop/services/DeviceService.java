package org.example.erepairshop.services;

import lombok.RequiredArgsConstructor;
import org.example.erepairshop.entities.Device;
import org.example.erepairshop.repositores.DeviceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional
public class DeviceService {
    private final DeviceRepository dr;

    public Device createDevice(Device device) {
        return dr.save(device);
    }

    public Device getDeviceById(Long id) {
        return dr.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + id));
    }

    public Page<Device> getAllDevices(Pageable p) {
        return dr.findAll(p);
    }

    public Page<Device> getDevicesByOwnerId(Long ownerId, Pageable p) {
        return dr.findByOwner_Id(ownerId, p);
    }

    public Device updateDevice(Long id, Device deviceDetails) {
        Device device = getDeviceById(id);
        device.setType(deviceDetails.getType());
        device.setBrand(deviceDetails.getBrand());
        device.setModel(deviceDetails.getModel());
        device.setSerialNumber(deviceDetails.getSerialNumber());
        return dr.save(device);
    }

    public void deleteDevice(Long id) {
        dr.deleteById(id);
    }
}

