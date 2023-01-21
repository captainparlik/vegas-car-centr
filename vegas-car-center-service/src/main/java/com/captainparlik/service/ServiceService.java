package com.captainparlik.service;

import static com.captainparlik.exceptions.ErrorRegistry.BOOKING_NOT_FOUND;

import java.util.List;

import com.captainparlik.exceptions.IllegalBookingException;
import com.captainparlik.model.entity.Service;
import com.captainparlik.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public List<Service> findAll() {
        return serviceRepository.findAllByActive(true);
    }

    public Service addService(Service service) {
        if (!service.isActive()) {
            throw new IllegalBookingException(BOOKING_NOT_FOUND);
        }
        return serviceRepository.save(service);
    }

    public Service updateService(Long id, Service service) {
        Service service1 = serviceRepository.findById(id)
                                            .orElseThrow(() -> new IllegalBookingException(BOOKING_NOT_FOUND));
        service1.setActive(service.isActive());
        return serviceRepository.save(service1);
    }
}
