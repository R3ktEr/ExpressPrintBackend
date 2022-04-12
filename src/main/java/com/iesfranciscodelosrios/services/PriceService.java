package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.price.*;
import com.iesfranciscodelosrios.repositories.QueryEnums;
import com.iesfranciscodelosrios.repositories.price.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    @Autowired
    ChangePriceService changePriceService;
    @Autowired
    FormatDataPriceService formatDataPriceService;
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LogManager.getLogger(PriceService.class);

    public List<Object> getAllPricesValidated() {
        List<Product> result = new ArrayList<>();
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
        LOGGER.info("Se ha recuperado la lista de los precios vigentes");
        return formatDataPriceService.sendFormattedDataToController(result);
    }

    public List<Object> getAllHistoricalPrices(int offSet) {
        List<Product> result = new ArrayList<>();
        Query historicalPrices = em.createNativeQuery(QueryEnums.historicalOffSet.getQuery());
        historicalPrices.setParameter(1, Math.max(offSet, 0));
        List<Object> queryResult = (List<Object>) historicalPrices.getResultList();
        for (Object o : queryResult) {
            Object[] columns = (Object[]) o;
            String type = (String) columns[0];
            Long id = ((BigInteger) columns[1]).longValue();
            Float price = (Float) columns[2];
            Boolean isValid = (Boolean) columns[3];
            LocalDate date = new java.util.Date(((Date) columns[4]).getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            switch (type) {
                case "color" -> {
                    Color c = new Color();
                    c.setId(id);
                    c.setPrice(price);
                    c.setValid(isValid);
                    c.setDate(date);
                    c.setColor((Boolean) columns[5]);
                    result.add(c);
                }
                case "copy" -> {
                    Copy c = new Copy();
                    c.setId(id);
                    c.setPrice(price);
                    c.setDate(date);
                    c.setValid(isValid);
                    result.add(c);
                }
                case "ended" -> {
                    Ended e = new Ended();
                    e.setId(id);
                    e.setPrice(price);
                    e.setDate(date);
                    e.setValid(isValid);
                    e.setEndedType(Enums.EndedType.values()[(Integer) columns[6]]);
                    result.add(e);
                }
                case "impressionperside" -> {
                    ImpressionPerSide i = new ImpressionPerSide();
                    i.setId(id);
                    i.setPrice(price);
                    i.setDate(date);
                    i.setValid(isValid);
                    i.setImpressionsTypes(Enums.ImpressionsTypes.values()[(Integer) columns[7]]);
                    result.add(i);
                }
                case "size" -> {
                    Size s = new Size();
                    s.setId(id);
                    s.setPrice(price);
                    s.setDate(date);
                    s.setValid(isValid);
                    s.setSizeSheet(Enums.sheetSize.values()[(Integer) columns[8]]);
                    s.setSheetSize((String) columns[9]);
                    result.add(s);
                }
                case "thickness" -> {
                    Thickness t = new Thickness();
                    t.setId(id);
                    t.setPrice(price);
                    t.setDate(date);
                    t.setValid(isValid);
                    t.setDescription((String) columns[10]);
                    t.setThicknessType(Enums.ThicknessType.values()[(Integer) columns[11]]);
                    result.add(t);
                }
            }
        }
        LOGGER.info("Se ha procesado correctamente una petición al historial de precios");
        return formatDataPriceService.sendFormattedDataHistoryToController(result);
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
                            result.add(changePriceService.changeColorPrice((Boolean) colormap.get("isColor"), (float) ((Double) colormap.get("price")).doubleValue()));
                            LOGGER.info("Se ha modificado el precio del " + ((Boolean) colormap.get("isColor") ? "Color" : "Blanco y Negro") + " a " + (float) ((Double) colormap.get("price")).doubleValue());
                        }
                    }
                    case "Copy" -> {
                        List<Object> copyList = (List<Object>) value;
                        for (Object copy : copyList) {
                            LinkedHashMap<String, Object> copymap = (LinkedHashMap<String, Object>) copy;
                            result.add(changePriceService.changeCopyPrice((float) ((Double) copymap.get("price")).doubleValue()));
                            LOGGER.info("Se ha modificado el precio de la copia a " + (float) ((Double) copymap.get("price")).doubleValue());
                        }
                    }
                    case "Endeds" -> {
                        List<Object> endedList = (List<Object>) value;
                        for (Object ended : endedList) {
                            LinkedHashMap<String, Object> endedmap = (LinkedHashMap<String, Object>) ended;
                            result.add(changePriceService.changeEndedPrice(Enums.EndedType.values()[Integer.valueOf(endedmap.get("endedType").toString())], (float) ((Double) endedmap.get("price")).doubleValue()));
                            LOGGER.info("Se ha modificado el precio del acabado " + Enums.EndedType.values()[Integer.valueOf(endedmap.get("endedType").toString())] + " a " + (float) ((Double) endedmap.get("price")).doubleValue());
                        }
                    }
                    case "ImpressionPerSide" -> {
                        List<Object> impressionList = (List<Object>) value;
                        for (Object impression : impressionList) {
                            LinkedHashMap<String, Object> impressionmap = (LinkedHashMap<String, Object>) impression;
                            result.add(changePriceService.changeImpressionPrice(Enums.ImpressionsTypes.values()[Integer.valueOf(impressionmap.get("impressionsTypes").toString())], (float) ((Double) impressionmap.get("price")).doubleValue()));
                            LOGGER.info("Se ha modificado el precio del tipo de impressión " + Enums.ImpressionsTypes.values()[Integer.valueOf(impressionmap.get("impressionsTypes").toString())] + " a " + (float) ((Double) impressionmap.get("price")).doubleValue());
                        }
                    }
                    case "Sizes" -> {
                        List<Object> sizeList = (List<Object>) value;
                        for (Object size : sizeList) {
                            LinkedHashMap<String, Object> sizemap = (LinkedHashMap<String, Object>) size;
                            result.add(changePriceService.changeSizePrice(Enums.sheetSize.values()[Integer.valueOf(sizemap.get("sheetSize").toString())], sizemap.get("sizeOfSheet").toString(), (float) ((Double) sizemap.get("price")).doubleValue()));
                            LOGGER.info("Se ha modificado un precio del tamaño " + Enums.sheetSize.values()[Integer.valueOf(sizemap.get("sheetSize").toString())] + " a " + (float) ((Double) sizemap.get("price")).doubleValue());
                        }
                    }
                    case "Thickness" -> {
                        List<Object> thicknessList = (List<Object>) value;
                        for (Object thickness : thicknessList) {
                            LinkedHashMap<String, Object> thicknessmap = (LinkedHashMap<String, Object>) thickness;
                            result.add(changePriceService.changeThicknessPrice(Enums.ThicknessType.values()[Integer.valueOf(thicknessmap.get("thicknessType").toString())], thicknessmap.get("description").toString(), (float) ((Double) thicknessmap.get("price")).doubleValue()));
                            LOGGER.info("Se ha modificado un precio del grosor " + Enums.ThicknessType.values()[Integer.valueOf(thicknessmap.get("thicknessType").toString())] + " a " + (float) ((Double) thicknessmap.get("price")).doubleValue());
                        }
                    }
                }
            }
        }
        LOGGER.info("Se han modificado los precios de los elementos anteriormetne loggeados");
        return formatDataPriceService.sendFormattedDataToController(result);
    }

}
