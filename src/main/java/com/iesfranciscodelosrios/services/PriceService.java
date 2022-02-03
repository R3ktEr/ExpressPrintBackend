package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.repositories.price.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public record PriceService(ColorRepository colorRepository, CopyRepository copyRepository,
                           EndedRepository endedRepository, ImpressionPerSideRepository impressionPerSideRepository,
                           SizeRepository sizeRepository, ThicknessRepository thicknessRepository) {

    @Autowired
    public PriceService {}

}
