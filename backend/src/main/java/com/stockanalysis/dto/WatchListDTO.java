package com.stockanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchListDTO {
    private Long id;
    private String name;
    private String description;

    @Builder.Default
    private Set<StockDTO> stocks = new HashSet<>();
}
