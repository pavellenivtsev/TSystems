package com.tsystems.service.impl;

import com.tsystems.service.api.GeneratorService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    /**
     * Generates the driver's personal number
     *
     * @return 2 numbers and 5 uppercase or lowercase letters
     */
    @Override
    public String generateDriverPersonalNumber() {
        return new Random().ints(48, 58)
                .limit(2)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .append(new Random().ints(65, 123)
                        .filter(i -> (i < 91 || i > 96))
                        .limit(5)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append))
                .toString();
    }

    /**
     * Generates a unique order number
     *
     * @return 4 numbers and 3 uppercase or lowercase letters
     */
    @Override
    public String generateOrderUniqueNumber() {
        return new Random().ints(48, 58)
                .limit(4)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .append(new Random().ints(65, 123)
                        .filter(i -> (i < 91 || i > 96))
                        .limit(3)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append))
                .toString();
    }

}
