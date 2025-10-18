package com.fabricaescuela.models.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "historial_estados")
public class HistorialEstado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistoriaEstadol", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEmpleado")
    private Empleado idEmpleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPaquete")
    private Paquete idPaquete; // El nombre debe coincidir con el método del repositorio

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEstado")
    private Estado idEstado;

    @Column(name = "fechaHora")
    private LocalDate fechaHora;

    public HistorialEstado(Integer id, Empleado idEmpleado, Paquete idPaquete, Estado idEstado, LocalDate fechaHora) {
        this.id = id;
        this.idEmpleado = idEmpleado;
        this.idPaquete = idPaquete;
        this.idEstado = idEstado;
        this.fechaHora = fechaHora;
    }

    public HistorialEstado() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HistorialEstado that = (HistorialEstado) o;
        return Objects.equals(id, that.id) && Objects.equals(idEmpleado, that.idEmpleado) && Objects.equals(idPaquete, that.idPaquete) && Objects.equals(idEstado, that.idEstado) && Objects.equals(fechaHora, that.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idEmpleado, idPaquete, idEstado, fechaHora);
    }
}