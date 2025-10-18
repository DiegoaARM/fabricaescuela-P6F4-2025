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
@Table(name = "empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmpleado", nullable = false)
    private Integer id;

    @Size(max = 30)
    @Column(name = "tipoDocumento", length = 30)
    private String tipoDocumento;

    @Column(name = "numeroDocumento")
    private Integer numeroDocumento;

    @Size(max = 100)
    @Column(name = "nombreEmpleado", length = 100)
    private String nombreEmpleado;

    @Size(max = 100)
    @Column(name = "correo", length = 100)
    private String correo;

    @Column(name = "telefono")
    private Integer telefono;

    public Empleado(Integer id, String tipoDocumento, Integer numeroDocumento, String nombreEmpleado, String correo, Integer telefono) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombreEmpleado = nombreEmpleado;
        this.correo = correo;
        this.telefono = telefono;
    }


    public Empleado() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(id, empleado.id) && Objects.equals(tipoDocumento, empleado.tipoDocumento) && Objects.equals(numeroDocumento, empleado.numeroDocumento) && Objects.equals(nombreEmpleado, empleado.nombreEmpleado) && Objects.equals(correo, empleado.correo) && Objects.equals(telefono, empleado.telefono);
    }

}