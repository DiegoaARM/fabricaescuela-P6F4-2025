package com.fabricaescuela.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "estados")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstado", nullable = false)
    private Integer id;

    @Size(max = 30)
    @Column(name = "nombreEstado", length = 30)
    private String nombreEstado;

    @Size(max = 255)
    @Column(name = "descripcionEstado")
    private String descripcionEstado;

    public Estado() {
    }

    public Estado(Integer id, String nombreEstado, String descripcionEstado) {
        this.id = id;
        this.nombreEstado = nombreEstado;
        this.descripcionEstado = descripcionEstado;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Estado estado = (Estado) o;
        return Objects.equals(id, estado.id) && Objects.equals(nombreEstado, estado.nombreEstado) && Objects.equals(descripcionEstado, estado.descripcionEstado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreEstado, descripcionEstado);
    }
}