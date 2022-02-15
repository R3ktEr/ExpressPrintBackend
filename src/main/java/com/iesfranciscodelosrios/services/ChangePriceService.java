package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.price.*;
import com.iesfranciscodelosrios.repositories.price.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ChangePriceService {
    @Autowired
    ColorRepository colorRepository;
    @Autowired
    CopyRepository copyRepository;
    @Autowired
    EndedRepository endedRepository;
    @Autowired
    ImpressionPerSideRepository impressionPerSideRepository;
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    ThicknessRepository thicknessRepository;

    protected Color changeColorPrice(boolean isColor, float newPrice) {
        Color c = colorRepository.getColorPrice(isColor);
        if (c != null) {
            c.setValid(false);
            colorRepository.save(c);
        }
        Color color = new Color(newPrice, isColor, true);
        color.setDate(LocalDate.now());
        return colorRepository.save(color);
    }

    protected Copy changeCopyPrice(float newPrice) {
        Copy c = copyRepository.getLatestCopy();
        if (c != null) {
            c.setValid(false);
            copyRepository.save(c);
        }
        Copy copy = new Copy(newPrice, true);
        copy.setDate(LocalDate.now());
        return copyRepository.save(copy);
    }

    protected Ended changeEndedPrice(Enums.EndedType endedType, float newPrice) {
        Ended e = endedRepository.getLatestEnded(endedType);
        if (e != null) {
            e.setValid(false);
            endedRepository.save(e);
        }
        Ended ended = new Ended(endedType, newPrice, true);
        ended.setDate(LocalDate.now());
        return endedRepository.save(ended);
    }

    protected ImpressionPerSide changeImpressionPrice(Enums.ImpressionsTypes impressionsTypes, float newPrice) {
        ImpressionPerSide ips = impressionPerSideRepository.getLatestImpressionPerSide(impressionsTypes);
        if (ips != null) {
            ips.setValid(false);
            impressionPerSideRepository.save(ips);
        }
        ImpressionPerSide impressionPerSide = new ImpressionPerSide(impressionsTypes, newPrice, true);
        impressionPerSide.setDate(LocalDate.now());
        return impressionPerSideRepository.save(impressionPerSide);
    }

    protected Size changeSizePrice(Enums.sheetSize sheetSize, String sizeOfSheet, float newPrice) {
        Size s = sizeRepository.getLatestSize(sheetSize);
        if (s != null) {
            s.setValid(false);
            sizeRepository.save(s);
        }
        Size size = new Size(sheetSize, sizeOfSheet, newPrice, true);
        size.setDate(LocalDate.now());
        return sizeRepository.save(size);
    }

    protected Thickness changeThicknessPrice(Enums.ThicknessType thicknessType, String description, float newPrice) {
        Thickness t = thicknessRepository.getLatestThickness(thicknessType);
        if (t != null) {
            t.setValid(false);
            thicknessRepository.save(t);
        }
        Thickness thickness = new Thickness(thicknessType, description, newPrice, true);
        thickness.setDate(LocalDate.now());
        return thicknessRepository.save(thickness);
    }
}
