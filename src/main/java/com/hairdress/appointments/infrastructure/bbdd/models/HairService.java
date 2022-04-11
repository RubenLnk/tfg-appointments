package com.hairdress.appointments.infrastructure.bbdd.models;

import java.io.Serializable;
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
public class HairService implements Serializable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "servicio_id", unique = true, nullable = false, updatable = false)
  private Long id;

  @Column(name = "nombre")
  private String name;

  @Column(name = "descripcion")
  private String description;

  @Column(name = "precio")
  private Double price;

  @Column(name = "duracion")
  private Integer duration;

  @Column(name = "fecha_creacion")
  private Timestamp creationDate;

  @Column(name = "fecha_modificacion")
  private Timestamp modificationDate;
}
