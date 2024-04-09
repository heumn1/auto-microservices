package ru.heumn.orderservice.storages.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parts")
public class PartEntity {

    @Id
    @Column(name = "id_part")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name_part", nullable = false)
    String name;

    @Column(name = "description_part")
    String description;

    @Column(name = "category_part", nullable = false)
    String category;

    @Column(name = "count_part", nullable = false)
    Integer count;

    @Column(name = "price_part", nullable = false)
    Double price;
}
