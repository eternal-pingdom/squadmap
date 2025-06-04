package org.squadmap.location.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.squadmap.location.dto.LocationDto;
import org.squadmap.location.entity.Location;
import org.squadmap.location.repository.LocationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LocationService {

    @Inject
    LocationRepository repository;

    public void save(LocationDto dto) {
        Location l = new Location();
        l.userId = dto.userId;
        l.latitude = dto.latitude;
        l.longitude = dto.longitude;
        l.timestamp = dto.timestamp != null ? dto.timestamp : LocalDateTime.now();
        repository.persist(l);
    }

    public List<LocationDto> getAll() {
        return repository.listAll().stream().map(l -> {
            LocationDto dto = new LocationDto();
            dto.userId = l.userId;
            dto.latitude = l.latitude;
            dto.longitude = l.longitude;
            dto.timestamp = l.timestamp;
            return dto;
        }).collect(Collectors.toList());
    }
}
