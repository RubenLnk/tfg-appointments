package com.hairdress.appointments.infrastructure.bbdd.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@NoArgsConstructor
@Table(name = "profesionales")
@EqualsAndHashCode(of = "id")
@Entity
public class Professional implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "profesional_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "uid")
    private String uid;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "nombre")
    private String name;

    @Column(name = "apellido1")
    private String surname1;

    @Column(name = "apellido2")
    private String surname2;

    @Column(name = "fecha_creacion")
    private Timestamp creationDate;

    @Column(name = "fecha_modificacion")
    private Timestamp modifyDate;

    @OneToMany(mappedBy = "creatorProfessional", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointmentsCreated;
}
