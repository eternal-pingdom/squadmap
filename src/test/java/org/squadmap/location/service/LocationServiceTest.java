package org.squadmap.location.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.squadmap.location.dto.LocationDto;
import org.squadmap.location.entity.Location;
import org.squadmap.location.repository.LocationRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class LocationServiceTest {

    @Inject
    LocationService locationService;

    @InjectMock
    LocationRepository locationRepository;

    @Test
    void testSave_withTimestamp() {
        LocationDto dto = new LocationDto();
        dto.userId = "testUser1";
        dto.latitude = 10.0;
        dto.longitude = 20.0;
        dto.timestamp = LocalDateTime.of(2024, 1, 1, 10, 0, 0);

        locationService.save(dto);

        ArgumentCaptor<Location> locationCaptor = ArgumentCaptor.forClass(Location.class);
        verify(locationRepository, times(1)).persist(locationCaptor.capture());

        Location persistedLocation = locationCaptor.getValue();
        assertNotNull(persistedLocation);
        assertEquals(dto.userId, persistedLocation.getUserId());
        assertEquals(dto.latitude, persistedLocation.getLatitude());
        assertEquals(dto.longitude, persistedLocation.getLongitude());
        assertEquals(dto.timestamp, persistedLocation.getTimestamp());
    }

    @Test
    void testSave_withoutTimestamp() {
        LocationDto dto = new LocationDto();
        dto.userId = "testUser2";
        dto.latitude = 15.0;
        dto.longitude = 25.0;
        // dto.timestamp ist null

        // Um den Zeitstempel zu kontrollieren, könnten wir LocalDateTime.now() mocken,
        // aber für diesen Test reicht es zu prüfen, dass es nicht null ist.
        // Für präzisere Tests von LocalDateTime.now() siehe Mockito.mockStatic(LocalDateTime.class)
        // ab Mockito 3.4.0 oder PowerMock für ältere Versionen.

        locationService.save(dto);

        ArgumentCaptor<Location> locationCaptor = ArgumentCaptor.forClass(Location.class);
        verify(locationRepository, times(1)).persist(locationCaptor.capture());

        Location persistedLocation = locationCaptor.getValue();
        assertNotNull(persistedLocation);
        assertEquals(dto.userId, persistedLocation.getUserId());
        assertEquals(dto.latitude, persistedLocation.getLatitude());
        assertEquals(dto.longitude, persistedLocation.getLongitude());
        assertNotNull(persistedLocation.getTimestamp()); // Wichtigster Check hier
    }

    @Test
    void testGetAll_emptyList() {
        when(locationRepository.listAll()).thenReturn(Collections.emptyList());

        List<LocationDto> result = locationService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(locationRepository, times(1)).listAll();
    }

    @Test
    void testGetAll_withData() {
        Location location1 = new Location("userA", 1.0, 2.0, LocalDateTime.now().minusHours(1));
        // JPA setzt die ID, für den Test hier nicht relevant, da wir das Repository mocken
        // location1.setId(1L);
        Location location2 = new Location("userB", 3.0, 4.0, LocalDateTime.now());
        // location2.setId(2L);

        when(locationRepository.listAll()).thenReturn(List.of(location1, location2));

        List<LocationDto> resultDtos = locationService.getAll();

        assertNotNull(resultDtos);
        assertEquals(2, resultDtos.size());
        verify(locationRepository, times(1)).listAll();

        LocationDto dto1 = resultDtos.stream().filter(d -> "userA".equals(d.userId)).findFirst().orElse(null);
        assertNotNull(dto1);
        assertEquals(location1.getLatitude(), dto1.latitude);
        assertEquals(location1.getLongitude(), dto1.longitude);
        assertEquals(location1.getTimestamp(), dto1.timestamp);

        LocationDto dto2 = resultDtos.stream().filter(d -> "userB".equals(d.userId)).findFirst().orElse(null);
        assertNotNull(dto2);
        assertEquals(location2.getLatitude(), dto2.latitude);
        assertEquals(location2.getLongitude(), dto2.longitude);
        assertEquals(location2.getTimestamp(), dto2.timestamp);
    }
}