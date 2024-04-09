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
@Table(name = "supplier")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_supplier")
    Long id;

    @Column(name = "name_supplier", nullable = false)
    String name;

    @Column(name = "email_supplier")
    String email;

    @Column(name = "phone_supplier")
    String phone;


}
