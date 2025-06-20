package com.pieceofcake.product_read_service.funding.entity;

import lombok.Getter;
@Getter
public enum SortBy {
    ID("id"),
    REMAINING_PIECE("fundingRead.remainingPieces"),
    PRICE("fundingRead.piecePrice");

    private final String label;
    SortBy(String label) {
        this.label = label;
    }
}
