package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.price.*;
import com.iesfranciscodelosrios.repositories.price.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceService {
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

    public List<Price> getAllPrices() {
        List<Price> result = new ArrayList<>();
        result.add(colorRepository.getColorPrice(false));
        result.add(colorRepository.getColorPrice(true));
        result.add(copyRepository.getLatestCopy());
        for (Enums.EndedType e : Enums.EndedType.values()) {
            result.add(endedRepository.getLatestEnded(e));
        }
        for (Enums.ImpressionsTypes e : Enums.ImpressionsTypes.values()) {
            result.add(impressionPerSideRepository.getLatestImpressionPerSide(e));
        }
        for (Enums.sheetSize e : Enums.sheetSize.values()) {
            result.add(sizeRepository.getLatestSize(e));
        }
        for (Enums.ThicknessType e : Enums.ThicknessType.values()) {
            result.add(thicknessRepository.getLatestThickness(e));
        }
        return result;
    }

    public List<Price> changeAllPrices(List<Price> newValues) {
        List<Price> result = new ArrayList<>();
        for (Price p : newValues) {
            if (p instanceof Color c)
                p = changeColorPrice(c.isColor(), c.getPrice());
            else if (p instanceof Copy c)
                changeCopyPrice(c.getPrice());
            else if (p instanceof Ended e)
                changeEndedPrice(e.getEndedType(), e.getPrice());
            else if (p instanceof ImpressionPerSide i)
                changeImpressionPrice(i.getImpressionsTypes(), i.getPrice());
            else if (p instanceof Size s)
                changeSizePrice(s.getEndedType(), s.getSheetSize(), s.getPrice());
            else if (p instanceof Thickness t)
                changeThicknessPrice(t.getThicknessType(), t.getDescription(), t.getPrice());
            result.add(p);
        }
        return result;
    }

    private Color changeColorPrice(boolean isColor, float newPrice) {
        Color c = colorRepository.getColorPrice(isColor);
        if (c != null) {
            c.setValid(false);
            colorRepository.save(c);
        }
        Color color = new Color(newPrice, isColor, true);
        return colorRepository.save(color);
    }

    private void changeCopyPrice(float newPrice) {
        Copy c = copyRepository.getLatestCopy();
        if (c != null) {
            c.setValid(false);
            copyRepository.save(c);
        }
        Copy copy = new Copy(newPrice, true);
        copyRepository.save(copy);
    }

    private void changeEndedPrice(Enums.EndedType endedType, float newPrice) {
        Ended e = endedRepository.getLatestEnded(endedType);
        if (e != null) {
            e.setValid(false);
            endedRepository.save(e);
        }
        Ended ended = new Ended(endedType, newPrice, true);
        endedRepository.save(ended);
    }

    private void changeImpressionPrice(Enums.ImpressionsTypes impressionsTypes, float newPrice) {
        ImpressionPerSide ips = impressionPerSideRepository.getLatestImpressionPerSide(impressionsTypes);
        if (ips != null) {
            ips.setValid(false);
            impressionPerSideRepository.save(ips);
        }
        ImpressionPerSide impressionPerSide = new ImpressionPerSide(impressionsTypes, newPrice, true);
        impressionPerSideRepository.save(impressionPerSide);
    }

    private void changeSizePrice(Enums.sheetSize sheetSize, String sizeOfSheet, float newPrice) {
        Size s = sizeRepository.getLatestSize(sheetSize);
        if (s != null) {
            s.setValid(false);
            sizeRepository.save(s);
        }
        Size size = new Size(sheetSize, sizeOfSheet, newPrice, true);
        sizeRepository.save(size);
    }

    private void changeThicknessPrice(Enums.ThicknessType thicknessType, String description, float newPrice) {
        Thickness t = thicknessRepository.getLatestThickness(thicknessType);
        if (t != null) {
            t.setValid(false);
            thicknessRepository.save(t);
        }
        Thickness thickness = new Thickness(thicknessType, description, newPrice, true);
        thicknessRepository.save(thickness);
    }

}
