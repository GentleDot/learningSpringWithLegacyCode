package net.gentledot.springcodeproject.model.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProductVO {
    private String name;
    private double price;

    public ProductVO(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("price", price)
                .toString();
    }
}
