package com.harshita.RideShare.controller;

import jakarta.validation.Valid;
import com.harshita.RideShare.dto.CreateRideRequest;
import com.harshita.RideShare.model.Ride;
import com.harshita.RideShare.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping("/rides")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Ride> createRide(@Valid @RequestBody CreateRideRequest request,
                                           Authentication authentication) {
        return ResponseEntity.ok(rideService.createRide(request, authentication.getName()));
    }

    @GetMapping("/user/rides")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Ride>> getUserRides(Authentication authentication) {
        return ResponseEntity.ok(rideService.getUserRides(authentication.getName()));
    }

    @GetMapping("/driver/rides/requests")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<List<Ride>> getPendingRides() {
        return ResponseEntity.ok(rideService.getPendingRides());
    }

    @PostMapping("/driver/rides/{rideId}/accept")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<Ride> acceptRide(@PathVariable String rideId,
                                           Authentication authentication) {
        return ResponseEntity.ok(rideService.acceptRide(rideId, authentication.getName()));
    }

    @PostMapping("/rides/{rideId}/complete")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_DRIVER')")
    public ResponseEntity<Ride> completeRide(@PathVariable String rideId) {
        return ResponseEntity.ok(rideService.completeRide(rideId));
    }
}