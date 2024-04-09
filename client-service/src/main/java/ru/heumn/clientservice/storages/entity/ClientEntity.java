package ru.heumn.clientservice.storages.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    Long id;

    @Column(name = "name_client")
    String name;

    @Column(name = "lastname_client")
    String lastname;

    @Column(name = "patronymic_client")
    String patronymic;

    @Column(name = "number_client")
    String number;

    @Column(name = "email_client")
    String email;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client_order", joinColumns = {@JoinColumn(name = "id_client")}, inverseJoinColumns = {@JoinColumn(name = "id_order")})
    Set<OrderEntity> orders = new HashSet<>();
}
