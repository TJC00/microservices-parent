package com.chipthnky.apigateway.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventories")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private Integer quantity;
}
