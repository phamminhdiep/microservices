package com.example.hotelservice;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hotels")
@Slf4j
@CrossOrigin("*")
public class HotelController {
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/tours/{hotelId}")
	public Tour getHotelWithTour(@PathVariable Long hotelId) {
		Hotel hotel = hotelRepository.findById(hotelId).get();
		Tour tour = restTemplate.getForObject("http://localhost:8060/tours/" + hotel.getTourId(), Tour.class);
		return tour;
	}
	
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
    	Hotel hotel = hotelRepository.findById(id).get();
        if (hotel != null) {
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
    	Hotel createdHotel = hotelRepository.save(hotel);
        System.out.println(createdHotel + "ok");
        return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
    	Hotel updatedHotel = hotelRepository.save(hotel);
        if (updatedHotel != null) {
            return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
    	hotelRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
