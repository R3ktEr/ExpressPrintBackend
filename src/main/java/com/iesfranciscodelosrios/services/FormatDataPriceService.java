package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.price.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class FormatDataPriceService {

    protected List<Object> sendFormattedDataToController(List<Product> values) {
        List<Object> colors = null;
        List<Object> endeds = null;
        List<Object> impressions = null;
        List<Object> sizes = null;
        List<Object> thicknessS = null;
        List<Object> copy = null;
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        for (Product p : values) {
            if (p instanceof Color) {
                Color c = (Color) p;
                if (colors == null)
                    colors = new ArrayList<>();
                LinkedHashMap<String, Object> color = new LinkedHashMap<>();
                color.put("id", c.getId());
                color.put("price", c.getPrice());
                color.put("isColor", c.isColor());
                colors.add(color);
            } else if (p instanceof Ended) {
                Ended e = (Ended) p;
                if (endeds == null)
                    endeds = new ArrayList<>();
                LinkedHashMap<String, Object> ended = new LinkedHashMap<>();
                ended.put("id", e.getId());
                ended.put("price", e.getPrice());
                ended.put("endedType", e.getEndedType().toString());
                endeds.add(ended);
            } else if (p instanceof ImpressionPerSide) {
                ImpressionPerSide i = (ImpressionPerSide) p;
                if (impressions == null)
                    impressions = new ArrayList<>();
                LinkedHashMap<String, Object> impression = new LinkedHashMap<>();
                impression.put("id", i.getId());
                impression.put("price", i.getPrice());
                impression.put("impressionsTypes", i.getImpressionsTypes().toString());
                impressions.add(impression);
            } else if (p instanceof Size) {
                Size s = (Size) p;
                if (sizes == null)
                    sizes = new ArrayList<>();
                LinkedHashMap<String, Object> size = new LinkedHashMap<>();
                size.put("id", s.getId());
                size.put("price", s.getPrice());
                size.put("sheetSize", s.getSizeSheet().toString());
                size.put("sizeOfSheet", s.getSheetSize());
                sizes.add(size);
            } else if (p instanceof Thickness) {
                Thickness t = (Thickness) p;
                if (thicknessS == null)
                    thicknessS = new ArrayList<>();
                LinkedHashMap<String, Object> thickness = new LinkedHashMap<>();
                thickness.put("id", t.getId());
                thickness.put("price", t.getPrice());
                thickness.put("thicknessType", t.getThicknessType().toString());
                thickness.put("description", t.getDescription());
                thicknessS.add(thickness);
            } else if (p instanceof Copy) {
                Copy c = (Copy) p;
                if (copy == null)
                    copy = new ArrayList<>();
                LinkedHashMap<String, Object> copymap = new LinkedHashMap<>();
                copymap.put("id", c.getId());
                copymap.put("price", c.getPrice());
                copy.add(copymap);
            }
        }
        if (colors != null)
            map.put("Color", colors);
        if (copy != null)
            map.put("Copy", copy);
        if (endeds != null)
            map.put("Endeds", endeds);
        if (impressions != null)
            map.put("ImpressionPerSide", impressions);
        if (sizes != null)
            map.put("Sizes", sizes);
        if (thicknessS != null)
            map.put("Thickness", thicknessS);
        return List.of(map);
    }

    //TODO eliminar que devuelva el ID porque no es necesaria esa información en el front(?
    protected List<Object> sendFormattedDataHistoryToController(List<Product> values) {
        List<Object> colors = null;
        List<Object> endeds = null;
        List<Object> impressions = null;
        List<Object> sizes = null;
        List<Object> thicknessS = null;
        List<Object> copy = null;
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        for (Product p : values) {
            if (p instanceof Color) {
                Color c = (Color) p;
                if (colors == null)
                    colors = new ArrayList<>();
                LinkedHashMap<String, Object> color = new LinkedHashMap<>();
                color.put("id", c.getId());
                color.put("price", c.getPrice());
                color.put("isColor", c.isColor());
                color.put("date", c.getDate());
                colors.add(color);
            } else if (p instanceof Ended) {
                Ended e = (Ended) p;
                if (endeds == null)
                    endeds = new ArrayList<>();
                LinkedHashMap<String, Object> ended = new LinkedHashMap<>();
                ended.put("id", e.getId());
                ended.put("price", e.getPrice());
                ended.put("endedType", e.getEndedType().toString());
                ended.put("date", e.getDate());
                endeds.add(ended);
            } else if (p instanceof ImpressionPerSide) {
                ImpressionPerSide i = (ImpressionPerSide) p;
                if (impressions == null)
                    impressions = new ArrayList<>();
                LinkedHashMap<String, Object> impression = new LinkedHashMap<>();
                impression.put("id", i.getId());
                impression.put("price", i.getPrice());
                impression.put("impressionsTypes", i.getImpressionsTypes().toString());
                impression.put("date", i.getDate());
                impressions.add(impression);
            } else if (p instanceof Size) {
                Size s = (Size) p;
                if (sizes == null)
                    sizes = new ArrayList<>();
                LinkedHashMap<String, Object> size = new LinkedHashMap<>();
                size.put("id", s.getId());
                size.put("price", s.getPrice());
                size.put("sheetSize", s.getSizeSheet().toString());
                size.put("sizeOfSheet", s.getSheetSize());
                size.put("date", s.getDate());
                sizes.add(size);
            } else if (p instanceof Thickness) {
                Thickness t = (Thickness) p;
                if (thicknessS == null)
                    thicknessS = new ArrayList<>();
                LinkedHashMap<String, Object> thickness = new LinkedHashMap<>();
                thickness.put("id", t.getId());
                thickness.put("price", t.getPrice());
                thickness.put("thicknessType", t.getThicknessType().toString());
                thickness.put("description", t.getDescription());
                thickness.put("date", t.getDate());
                thicknessS.add(thickness);
            } else if (p instanceof Copy) {
                Copy c = (Copy) p;
                if (copy == null)
                    copy = new ArrayList<>();
                LinkedHashMap<String, Object> copymap = new LinkedHashMap<>();
                copymap.put("id", c.getId());
                copymap.put("price", c.getPrice());
                copymap.put("date", c.getDate());
                copy.add(copymap);
            }
        }
        if (colors != null)
            map.put("Color", colors);
        if (copy != null)
            map.put("Copy", copy);
        if (endeds != null)
            map.put("Endeds", endeds);
        if (impressions != null)
            map.put("ImpressionPerSide", impressions);
        if (sizes != null)
            map.put("Sizes", sizes);
        if (thicknessS != null)
            map.put("Thickness", thicknessS);
        return List.of(map);
    }
}
