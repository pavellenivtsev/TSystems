package com.tsystems.service.api;

public interface GeneratorService {

    /**
     * Generates the driver's personal number
     *
     * @return personal number
     */
    String generateDriverPersonalNumber();

    /**
     * Generates a unique order number
     *
     * @return
     */
    String generateOrderUniqueNumber();


}
