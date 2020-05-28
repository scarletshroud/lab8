package src.elements;

import java.io.Serializable;

public enum UnitOfMeasure implements Serializable {
    PCS("PCS"),
    MILLILITERS("MILLILITERS"),
    GRAMS("GRAMS");

    private final String unit;

    UnitOfMeasure(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
