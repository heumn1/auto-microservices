package ru.heumn.storageservice.storages.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heumn.storageservice.storages.Status;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ordering")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    Long id;

    @Column(name = "type_order", unique = true)
    String type;

    @Column(name = "brand_car_order", nullable = false)
    String brandCar;

    @Column(name = "number_car_order", nullable = false)
    String numberCar;

    @Column(name = "number_client_order", nullable = false)
    String numberClient;

    @Column(name = "estimate_cost_order", nullable = false)
    Double price;

    @Column(name = "status_order", nullable = false)
    @Enumerated(EnumType.STRING)
    Status status;

    @OneToMany(fetch = FetchType.EAGER) // <- был merge
    @JoinTable(name = "order_part", joinColumns = {@JoinColumn(name = "id_order")}, inverseJoinColumns = {@JoinColumn(name = "id_part")})
    List<PartEntity> parts;
}
