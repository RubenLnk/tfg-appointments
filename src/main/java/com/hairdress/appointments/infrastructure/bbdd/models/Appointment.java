package com.hairdress.appointments.infrastructure.bbdd.models;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "citas")
@EqualsAndHashCode(of = "id")
@Entity
public class Appointment {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "cita_id", unique = true, nullable = false, updatable = false)
  private Long id;

  @Column(name = "fecha_cita")
  private Timestamp appointmentDate;

  @Column(name = "precio")
  private Double price;

  @Column(name = "observaciones")
  private String observations;

  @ManyToOne
  @JoinColumn(name="cliente_id")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "profesional_creador")
  private Professional creatorProfessional;

  @Column(name = "activo")
  private Boolean active;

  @Column(name = "fecha_creacion")
  private Timestamp creationDate;

  @Column(name = "fecha_modificacion")
  private Timestamp modificationDate;

  @Column(name = "fecha_anulacion")
  private Timestamp cancellationDate;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "servicio_cita",
      joinColumns = @JoinColumn(name = "cita_id"),
      inverseJoinColumns = @JoinColumn(name = "servicio_id"))
  private List<Service> services;
}
