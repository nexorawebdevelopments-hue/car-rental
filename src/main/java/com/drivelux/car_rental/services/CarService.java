package com.drivelux.car_rental.services;

import com.cloudinary.Cloudinary;
import com.drivelux.car_rental.entity.CarEntity;
import com.drivelux.car_rental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private Cloudinary cloudinary;

    public CarEntity saveWithImage(CarEntity car, MultipartFile file) {

        try {

            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Image is required");
            }

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of("folder", "cars")
            );

            car.setImage((String) uploadResult.get("secure_url"));

            return carRepository.save(car);

        } catch (Exception e) {
            throw new RuntimeException("failed to save car", e);
        }

    }
    // Get All Cars

    public List<CarEntity> getAllCars() {
        return carRepository.findAll();
    }

// Delete Car

    public void deleteCar(String id) {

        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car not found");
        }

        carRepository.deleteById(id);
    }
    public CarEntity getCarById(String id) {
        return carRepository.findById(id).orElse(null);
    }
    public CarEntity updateCar(String id, CarEntity car, MultipartFile file) {

        CarEntity existingCar = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        existingCar.setBrand(car.getBrand());
        existingCar.setName(car.getName());
        existingCar.setType(car.getType());
        existingCar.setTransmission(car.getTransmission());
        existingCar.setColor(car.getColor());
        existingCar.setModelYear(car.getModelYear());
        existingCar.setPrice(car.getPrice());
        existingCar.setDescription(car.getDescription());

        try {

            // Agar nayi image upload hui hai to usko update karo
            if (file != null && !file.isEmpty()) {

                Map uploadResult = cloudinary.uploader().upload(
                        file.getBytes(),
                        Map.of("folder", "cars")
                );

                existingCar.setImage((String) uploadResult.get("secure_url"));
            }

            return carRepository.save(existingCar);

        } catch (Exception e) {
            throw new RuntimeException("Failed to update car", e);
        }
    }
}