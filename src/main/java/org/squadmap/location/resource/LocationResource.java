package org.squadmap.location.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.squadmap.location.dto.LocationDto;
import org.squadmap.location.service.LocationService;

import java.util.List;

@Path("/locations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationResource {

    @Inject
    LocationService service;

    @GET
    public List<LocationDto> getAll() {
        return service.getAll();
    }

    @POST
    public void save(LocationDto dto) {
        service.save(dto);
    }
}
