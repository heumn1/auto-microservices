package ru.heumn.storageservice.storages.entity;


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
@Table(name = "supplier_parts")
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sp")
    Long id;

    @ManyToOne
    @JoinColumn(name = "id_supplier")
    SupplierEntity supplier;

    @ManyToOne
    @JoinColumn(name = "id_part")
    PartEntity part;

    @Column(name = "price_part", nullable = false)
    Double price;
}
