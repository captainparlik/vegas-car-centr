package com.captainparlik.endpoints;

import java.util.List;

import com.captainparlik.model.entity.Service;
import com.captainparlik.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class ServiceEndpoint {

    private final ServiceService serviceService;
    @GetMapping
    public List<Service> findAll() {
        return serviceService.findAll();
    }

    @PostMapping
    public Service addService(@RequestBody Service service) {
        return serviceService.addService(service);
    }

    @PutMapping("/{serviceId}")
    public Service updateService(@PathVariable final Long serviceId, @RequestBody Service service) {
        return serviceService.updateService(serviceId, service);
    }
}
