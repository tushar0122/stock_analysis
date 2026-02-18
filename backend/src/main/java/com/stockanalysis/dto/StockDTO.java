package com.stockanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDTO {
    private Long id;
    private String name;
    private String symbol;
    private String sector;
    private String industry;
    private String exchange;
    private String marketCap;
}
