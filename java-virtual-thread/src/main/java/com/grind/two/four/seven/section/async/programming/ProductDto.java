package com.grind.two.four.seven.section.async.programming;

public class ProductDto {

    public String getProduct() {
        return product;
    }

    private final String product;

    @Override
    public String toString() {
        return "ProductDto{" +
                "product='" + product + '\'' +
                ", rating=" + rating +
                '}';
    }

    public Integer getRating() {
        return rating;
    }

    private final Integer rating;

    public ProductDto(final String product, final Integer rating) {
        this.product = product;
        this.rating = rating;
    }
}
