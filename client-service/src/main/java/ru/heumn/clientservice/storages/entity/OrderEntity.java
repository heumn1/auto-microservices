package ru.heumn.clientservice.storages.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.heumn.clientservice.storages.Status;

import java.util.List;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity order = (OrderEntity) o;
        return Objects.equals(id, order.id) && Objects.equals(type, order.type) && Objects.equals(brandCar, order.brandCar) && Objects.equals(numberCar, order.numberCar) && Objects.equals(numberClient, order.numberClient) && Objects.equals(price, order.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, brandCar, numberCar, numberClient, price);
    }
}
