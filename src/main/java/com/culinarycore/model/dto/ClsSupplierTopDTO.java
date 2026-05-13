package com.culinarycore.model.dto;

public class ClsSupplierTopDTO {
    private int id;
    private String name;
    private int totalQuantitySupplied;

    public ClsSupplierTopDTO(int id, String name, int totalQuantitySupplied) {
        this.id = id;
        this.name = name;
        this.totalQuantitySupplied = totalQuantitySupplied;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getTotalQuantitySupplied() { return totalQuantitySupplied; }

    @Override
    public String toString() {
        return "SupplierTopDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", totalQuantitySupplied=" + totalQuantitySupplied +
                '}';
    }
}