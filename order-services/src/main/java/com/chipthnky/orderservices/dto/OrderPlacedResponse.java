package com.chipthnky.orderservices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPlacedResponse {
    private String orderNumber;
    private String message;
    private LocalDateTime createdAt;
}
