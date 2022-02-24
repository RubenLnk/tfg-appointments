package com.hairdress.appointments.infrastructure.bbdd.models;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "servicios")
@EqualsAndHashCode(of = "id")
@Entity
public class Service {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "profesional_id", unique = true, nullable = false, updatable = false)
  private Long id;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "descripcion")
  private String descripcion;

  @Column(name = "precio")
  private Double precio;

  @Column(name = "duracion")
  private Integer duracion;

  @Column(name = "fecha_creacion")
  private Timestamp fechaCreacion;

  @Column(name = "fecha_modificacion")
  private Timestamp fechaModificacion;
}
