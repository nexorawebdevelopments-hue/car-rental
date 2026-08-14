package com.drivelux.car_rental.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "cars")
public class CarEntity {

    @Id
    private String id;

    private String image;

    private String brand;

    private String name;

    private String type;

    private String transmission;

    private String color;

    private int modelYear;

    private double price;

    private String description;
}