package com.hairdress.appointments.infrastructure.bbdd.models;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "clientes")
@EqualsAndHashCode(of = "id")
@Entity
public class Customer {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "cliente_id", unique = true, nullable = false, updatable = false)
  private Long id;

  @Column(name = "nombre")
  private String name;

  @Column(name = "apellido1")
  private String surname1;

  @Column(name = "apellido2")
  private String surname2;

  @Column(name = "telefono")
  private String phone;

  @Column(name = "correo")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "salt")
  private String salt;

  @Column(name = "alta")
  private Boolean registered;

  @Column(name = "primera_conexion")
  private Boolean firstConnection;

  @Column(name = "fecha_creacion")
  private Timestamp creationDate;

  @Column(name = "fecha_modificacion")
  private Timestamp modificationDate;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Appointment> appointments;
}
