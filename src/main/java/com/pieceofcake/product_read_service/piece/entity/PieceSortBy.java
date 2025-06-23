package com.pieceofcake.product_read_service.piece.entity;

import lombok.Getter;

@Getter
public enum PieceSortBy {
    ID("id"),
    POPULARITY("pieceRead.tradeQuantity"),
    PRICE("pieceRead.closingPrice");

    private final String label;
    PieceSortBy(String label) {
        this.label = label;
    }
}
