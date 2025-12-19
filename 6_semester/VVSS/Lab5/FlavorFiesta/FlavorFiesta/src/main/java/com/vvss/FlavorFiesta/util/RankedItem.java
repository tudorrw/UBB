package com.vvss.FlavorFiesta.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RankedItem<T> {

    private T entity;
    private int rank;

    public RankedItem(T entity, Comparable<?> value) {
        this.entity = entity;
    }
}
