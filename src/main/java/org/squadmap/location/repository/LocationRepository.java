package org.squadmap.location.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.squadmap.location.entity.Location;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class LocationRepository implements PanacheRepository<Location> {}
