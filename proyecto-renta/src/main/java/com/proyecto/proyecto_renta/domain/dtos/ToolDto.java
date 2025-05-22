package com.proyecto.proyecto_renta.domain.dtos;

import com.proyecto.proyecto_renta.domain.entities.Tool;

public class ToolDto {
    private Long id;
    private String name;
    private String description;
    private String model;
    private String brand;
    private double costPerDay;
    private int availableQuantity;
    private String category;
    private String supplier;

    // Constructor desde Tool
    public ToolDto(Tool tool) {
        this.id = tool.getId();
        this.name = tool.getName();
        this.description = tool.getDescription();
        this.model = tool.getModel();
        this.brand = tool.getBrand();
        this.costPerDay = tool.getCostPerDay();
        this.availableQuantity = tool.getAvailableQuantity();

        // Evitar NPE si categoría o proveedor son null
        this.category = tool.getCategories() != null ? tool.getCategories().getName() : null;
        this.supplier = tool.getSupplier() != null ? tool.getSupplier().getName() : null; // Asegúrate que Supplier tenga getName()
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(double costPerDay) {
        this.costPerDay = costPerDay;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    
}