package com.drivelux.car_rental.controller;

import com.drivelux.car_rental.entity.CarEntity;
import com.drivelux.car_rental.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequestMapping("/cars")
@RestController
@CrossOrigin("*")
public class CarController {
    @Autowired
   private CarService carService;
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<CarEntity> addCar(

            @RequestParam String brand,
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam String transmission,
            @RequestParam String color,
            @RequestParam int modelYear,
            @RequestParam double price,
            @RequestParam String description,
            @RequestParam("image") MultipartFile file

    ) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        CarEntity car = new CarEntity();

        car.setBrand(brand);
        car.setName(name);
        car.setType(type);
        car.setTransmission(transmission);
        car.setColor(color);
        car.setModelYear(modelYear);
        car.setPrice(price);
        car.setDescription(description);

        CarEntity saved = carService.saveWithImage(car, file);

        return ResponseEntity.ok(saved);
    }
    @GetMapping
    public List<CarEntity> getAllCars() {
        return carService.getAllCars();
    }
//deleteing a car
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable String id) {

        carService.deleteCar(id);

        return ResponseEntity.ok().build();
    }
//    id sa get kar ka update karo ga
    @GetMapping("/{id}")
    public ResponseEntity<CarEntity> getCarById(@PathVariable String id) {

        CarEntity car = carService.getCarById(id);

        if (car == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(car);
    }
//    update of car
@PutMapping(value = "/{id}", consumes = "multipart/form-data")
public ResponseEntity<CarEntity> updateCar(

        @PathVariable String id,

        @RequestParam String brand,
        @RequestParam String name,
        @RequestParam String type,
        @RequestParam String transmission,
        @RequestParam String color,
        @RequestParam int modelYear,
        @RequestParam double price,
        @RequestParam String description,
        @RequestParam(value = "image", required = false) MultipartFile file

) {

    CarEntity car = new CarEntity();

    car.setBrand(brand);
    car.setName(name);
    car.setType(type);
    car.setTransmission(transmission);
    car.setColor(color);
    car.setModelYear(modelYear);
    car.setPrice(price);
    car.setDescription(description);

    CarEntity updated = carService.updateCar(id, car, file);

    return ResponseEntity.ok(updated);
}
}
