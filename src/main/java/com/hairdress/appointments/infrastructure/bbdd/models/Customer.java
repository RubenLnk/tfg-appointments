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
  private String nombre;

  @Column(name = "apellido1")
  private String apellido1;

  @Column(name = "apellido2")
  private String apellido2;

  @Column(name = "telefono")
  private String telefono;

  @Column(name = "correo")
  private String correo;

  @Column(name = "password")
  private String password;

  @Column(name = "fecha_creacion")
  private Timestamp fechaCreacion;

  @Column(name = "fecha_modificacion")
  private Timestamp fechaModificacion;

  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Appointment> citas;
}
