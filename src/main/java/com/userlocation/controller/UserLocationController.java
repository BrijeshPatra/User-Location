package com.userlocation.controller;

import com.userlocation.entity.UserLocation;
import com.userlocation.repository.UserLocationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserLocationController {
    @Autowired
    private UserLocationRepository userLocationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/create_data")
    public ResponseEntity<String>createTable(){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS user_location (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255)," +
                "latitude DOUBLE," +
                "longitude DOUBLE," +
                "excluded BOOLEAN)";

        // Execute the SQL statement to create the table
        entityManager.createNativeQuery(createTableSQL).executeUpdate();

        return ResponseEntity.ok("Table created successfully");
    }

    @PostMapping("/update_data")
    public ResponseEntity<String>updateTable(@RequestBody UserLocation userLocation){
        UserLocation existingLocation=userLocationRepository.findById(userLocation.getId()).orElse(null);

        if(existingLocation==null){
            return ResponseEntity.badRequest().body("User Location not found");
        }
        existingLocation.setName(userLocation.getName());
        existingLocation.setLongitude(userLocation.getLongitude());
        existingLocation.setLatitude(userLocation.getLatitude());
        existingLocation.setExcluded(userLocation.isExcluded());

        userLocationRepository.save(existingLocation);

        return ResponseEntity.ok("Table Updated Successfully");
    }
}
