package org.squadmap.location.dto;

import java.time.LocalDateTime;

public class LocationDto {
    public String userId;
    public Double latitude;
    public Double longitude;
    public LocalDateTime timestamp;
}
/*
Die LocationDto-Klasse passt zu einer typischen Location-Entität, wenn diese die Felder userId, latitude, longitude und timestamp enthält. 
Falls die Location-Klasse zusätzliche Felder oder andere Typen verwendet, müsste LocationDto entsprechend angepasst werden.
*/