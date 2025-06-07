package org.squadmap.location.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMockBean; // Sicherstellen, dass dieser Import korrekt ist
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.squadmap.location.dto.LocationDto;
import org.squadmap.location.service.LocationService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
public class LocationResourceTest {

    @InjectMockBean // Diese Annotation hinzufügen
    LocationService locationService;

    @Test
    public void testGetAll_empty() {
        when(locationService.getAll()).thenReturn(Collections.emptyList());

        given()
          .when().get("/locations")
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON)
             .body("$", empty()); // Überprüft, ob das JSON-Array leer ist

        verify(locationService, times(1)).getAll();
    }

    @Test
    public void testGetAll_withData() {
        LocationDto dto1 = new LocationDto();
        dto1.userId = "user1";
        dto1.latitude = 10.0;
        dto1.longitude = 20.0;
        dto1.timestamp = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

        LocationDto dto2 = new LocationDto();
        dto2.userId = "user2";
        dto2.latitude = 30.0;
        dto2.longitude = 40.0;
        dto2.timestamp = LocalDateTime.of(2024, 1, 2, 12, 0, 0);

        when(locationService.getAll()).thenReturn(List.of(dto1, dto2));

        given()
          .when().get("/locations")
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON)
             .body("$", hasSize(2))
             .body("[0].userId", is("user1"))
             .body("[0].latitude", is(10.0F)) // JSON-Zahlen werden oft als Float interpretiert
             .body("[0].longitude", is(20.0F))
             .body("[0].timestamp", is("2024-01-01T12:00:00")) // Standard ISO LocalDateTime Format
             .body("[1].userId", is("user2"));

        verify(locationService, times(1)).getAll();
    }

    @Test
    public void testSave() {
        LocationDto dtoToSave = new LocationDto();
        dtoToSave.userId = "testUserPost";
        dtoToSave.latitude = 50.5;
        dtoToSave.longitude = -0.5;
        // Der Zeitstempel wird im Service gesetzt, wenn er null ist,
        // oder der übergebene Wert wird verwendet.
        // Für diesen Test setzen wir ihn nicht, um das Service-Verhalten zu testen,
        // oder wir setzen ihn, um zu prüfen, ob er korrekt durchgereicht wird.
        // Hier testen wir den Fall, dass er mitgesendet wird.
        dtoToSave.timestamp = LocalDateTime.of(2024, 6, 5, 10, 30, 0);


        given()
          .contentType(ContentType.JSON)
          .body(dtoToSave)
          .when().post("/locations")
          .then()
             .statusCode(204); // POST gibt oft 204 No Content zurück, wenn nichts im Body zurückgegeben wird

        ArgumentCaptor<LocationDto> dtoCaptor = ArgumentCaptor.forClass(LocationDto.class);
        verify(locationService, times(1)).save(dtoCaptor.capture());

        LocationDto capturedDto = dtoCaptor.getValue();
        assertNotNull(capturedDto);
        assertEquals(dtoToSave.userId, capturedDto.userId);
        assertEquals(dtoToSave.latitude, capturedDto.latitude);
        assertEquals(dtoToSave.longitude, capturedDto.longitude);
        assertEquals(dtoToSave.timestamp, capturedDto.timestamp);
    }
}