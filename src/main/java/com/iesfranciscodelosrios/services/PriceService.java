package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.price.*;
import com.iesfranciscodelosrios.repositories.price.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public List<Object> getAllPrices() {
        List<Product> result = new ArrayList<>();
        result.add(colorRepository.getColorPrice(false));
        result.add(colorRepository.getColorPrice(true));
        result.add(copyRepository.getLatestCopy());
        for (Enums.EndedType e : Enums.EndedType.values()) {
            result.add(endedRepository.getLatestEnded(e.getICode()));
        }
        for (Enums.ImpressionsTypes e : Enums.ImpressionsTypes.values()) {
            result.add(impressionPerSideRepository.getLatestImpressionPerSide(e.getICode()));
        }
        for (Enums.sheetSize e : Enums.sheetSize.values()) {
            result.add(sizeRepository.getLatestSize(e.getICode()));
        }
        for (Enums.ThicknessType e : Enums.ThicknessType.values()) {
            result.add(thicknessRepository.getLatestThickness(e.getICode()));
        }
        return sendFormattedDataToController(result);
    }

    public List<Object> changeAllPrices(List<Object> newValues) {
        List<Product> result = new ArrayList<>();
        for (Object o : newValues) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) o;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                switch (key) {
                    case "Color" -> {
                        List<Object> colorList = (List<Object>) value;
                        for (Object color : colorList) {
                            LinkedHashMap<String, Object> colormap = (LinkedHashMap<String, Object>) color;
                            result.add(changeColorPrice((Boolean) colormap.get("isColor"), (float) ((Double) colormap.get("price")).doubleValue()));
                        }
                    }
                    case "Copy" -> {
                        LinkedHashMap<String, Object> copymap = (LinkedHashMap<String, Object>) value;
                        result.add(changeCopyPrice((float) ((Double) copymap.get("price")).doubleValue()));
                    }
                    case "Ended" -> {
                        List<Object> endedList = (List<Object>) value;
                        for (Object ended : endedList) {
                            LinkedHashMap<String, Object> endedmap = (LinkedHashMap<String, Object>) ended;
                            result.add(changeEndedPrice(Enums.EndedType.valueOf(endedmap.get("endedType").toString()), (float) ((Double) endedmap.get("price")).doubleValue()));
                        }
                    }
                    case "ImpressionPerSide" -> {
                        List<Object> impressionList = (List<Object>) value;
                        for (Object impression : impressionList) {
                            LinkedHashMap<String, Object> impressionmap = (LinkedHashMap<String, Object>) impression;
                            result.add(changeImpressionPrice(Enums.ImpressionsTypes.valueOf(impressionmap.get("impressionsTypes").toString()), (float) ((Double) impressionmap.get("price")).doubleValue()));
                        }
                    }
                    case "Size" -> {
                        List<Object> sizeList = (List<Object>) value;
                        for (Object size : sizeList) {
                            LinkedHashMap<String, Object> sizemap = (LinkedHashMap<String, Object>) size;
                            result.add(changeSizePrice(Enums.sheetSize.valueOf(sizemap.get("sheetSize").toString()), sizemap.get("sheetSize").toString(), (float) ((Double) sizemap.get("price")).doubleValue()));
                        }
                    }
                    case "Thickness" -> {
                        List<Object> thicknessList = (List<Object>) value;
                        for (Object thickness : thicknessList) {
                            LinkedHashMap<String, Object> thicknessmap = (LinkedHashMap<String, Object>) thickness;
                            result.add(changeThicknessPrice(Enums.ThicknessType.valueOf(thicknessmap.get("thicknessType").toString()), thicknessmap.get("description").toString(), (float) ((Double) thicknessmap.get("price")).doubleValue()));
                        }
                    }
                }
            }
        }
        return sendFormattedDataToController(result);
    }

    private List<Object> sendFormattedDataToController(List<Product> values) {
        List<Object> colors = null;
        List<Object> endeds = null;
        List<Object> impressions = null;
        List<Object> sizes = null;
        List<Object> thicknessS = null;
        List<Object> copy = null;
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        for (Product p : values) {
            if(p instanceof Color){
                Color c = (Color) p;
                if(colors == null)
                    colors = new ArrayList<>();
                LinkedHashMap<String, Object> color = new LinkedHashMap<>();
                color.put("id", c.getId());
                color.put("price", c.getPrice());
                color.put("isColor", c.isColor());
                colors.add(color);
            }else if(p instanceof Ended){
                Ended e = (Ended) p;
                if(endeds == null)
                    endeds = new ArrayList<>();
                LinkedHashMap<String, Object> ended = new LinkedHashMap<>();
                ended.put("id", e.getId());
                ended.put("price", e.getPrice());
                ended.put("endedType", e.getEndedType().toString());
                endeds.add(ended);
            }else if(p instanceof ImpressionPerSide){
                ImpressionPerSide i = (ImpressionPerSide) p;
                if(impressions == null)
                    impressions = new ArrayList<>();
                LinkedHashMap<String, Object> impression = new LinkedHashMap<>();
                impression.put("id", i.getId());
                impression.put("price", i.getPrice());
                impression.put("impressionsTypes", i.getImpressionsTypes().toString());
                impressions.add(impression);
            }else if(p instanceof Size){
                Size s = (Size) p;
                if(sizes == null)
                    sizes = new ArrayList<>();
                LinkedHashMap<String, Object> size = new LinkedHashMap<>();
                size.put("id", s.getId());
                size.put("price", s.getPrice());
                size.put("sheetSize", s.getSizeSheet().toString());
                size.put("sizeOfSheet", s.getSheetSize());
                sizes.add(size);
            }else if(p instanceof Thickness){
                Thickness t = (Thickness) p;
                if(thicknessS == null)
                    thicknessS = new ArrayList<>();
                LinkedHashMap<String, Object> thickness = new LinkedHashMap<>();
                thickness.put("id", t.getId());
                thickness.put("price", t.getPrice());
                thickness.put("thicknessType", t.getThicknessType().toString());
                thickness.put("description", t.getDescription());
                thicknessS.add(thickness);
            }else if(p instanceof Copy){
                Copy c = (Copy) p;
                if(copy == null)
                	copy = new ArrayList<>();
                LinkedHashMap<String, Object> copymap = new LinkedHashMap<>();
                copymap.put("id", c.getId());
                copymap.put("price", c.getPrice());
                copy.add(copymap);
            }
        }
        map.put("Color", colors);
        map.put("Copy", copy);
        map.put("Endeds", endeds);
        map.put("ImpressionPerSide", impressions);
        map.put("Sizes", sizes);
        map.put("Thickness", thicknessS);
        return List.of(map);
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

    private Copy changeCopyPrice(float newPrice) {
        Copy c = copyRepository.getLatestCopy();
        if (c != null) {
            c.setValid(false);
            copyRepository.save(c);
        }
        Copy copy = new Copy(newPrice, true);
        return copyRepository.save(copy);
    }

    private Ended changeEndedPrice(Enums.EndedType endedType, float newPrice) {
        Ended e = endedRepository.getLatestEnded(endedType.getICode());
        if (e != null) {
            e.setValid(false);
            endedRepository.save(e);
        }
        Ended ended = new Ended(endedType, newPrice, true);
        return endedRepository.save(ended);
    }

    private ImpressionPerSide changeImpressionPrice(Enums.ImpressionsTypes impressionsTypes, float newPrice) {
        ImpressionPerSide ips = impressionPerSideRepository.getLatestImpressionPerSide(impressionsTypes.getICode());
        if (ips != null) {
            ips.setValid(false);
            impressionPerSideRepository.save(ips);
        }
        ImpressionPerSide impressionPerSide = new ImpressionPerSide(impressionsTypes, newPrice, true);
        return impressionPerSideRepository.save(impressionPerSide);
    }

    private Size changeSizePrice(Enums.sheetSize sheetSize, String sizeOfSheet, float newPrice) {
        Size s = sizeRepository.getLatestSize(sheetSize.getICode());
        if (s != null) {
            s.setValid(false);
            sizeRepository.save(s);
        }
        Size size = new Size(sheetSize, sizeOfSheet, newPrice, true);
        return sizeRepository.save(size);
    }

    private Thickness changeThicknessPrice(Enums.ThicknessType thicknessType, String description, float newPrice) {
        Thickness t = thicknessRepository.getLatestThickness(thicknessType.getICode());
        if (t != null) {
            t.setValid(false);
            thicknessRepository.save(t);
        }
        Thickness thickness = new Thickness(thicknessType, description, newPrice, true);
        return thicknessRepository.save(thickness);
    }

}
