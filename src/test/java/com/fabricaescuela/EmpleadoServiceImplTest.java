package com.fabricaescuela;

import com.fabricaescuela.models.entity.Empleado;
import com.fabricaescuela.repository.EmpleadoRepository;
import com.fabricaescuela.service.EmpleadoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceImplTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoServiceImpl empleadoServiceImpl;

    @Test
    public void testFindAll() {
        //Arrange
        List<Empleado> listaSimulada = Arrays.asList(
                new Empleado(1, "CC", 103456789, "Ana", "ana@gmail.com", 1234567891L),
                new Empleado(2, "CC", 103464789, "Juan", "juan@gmail.com", 134567891L)
        );
        when(empleadoRepository.findAll()).thenReturn(listaSimulada);

        List<Empleado> esperado = Arrays.asList(
                new Empleado(1, "CC", 103456789, "Ana", "ana@gmail.com", 1234567891L),
                new Empleado(2, "CC", 103464789, "Juan", "juan@gmail.com", 134567891L)
        );

        //Act
        List<Empleado> resultado = empleadoServiceImpl.findAll();

        //Assert
        assertEquals(2, resultado.size());
        assertEquals("Ana", resultado.get(0).getNombreEmpleado());
        verify(empleadoRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        //Arrange
        int idSimulado = 4;
        Empleado empleadoSimulado = new Empleado(4, "CC", 103456789, "Carlos", "carlos@gmail.com", 1234567891L);
        when(empleadoRepository.findById(idSimulado)).thenReturn(Optional.of(empleadoSimulado));

        //Act
        Optional<Empleado> resultado = empleadoServiceImpl.findById(idSimulado);

        //Assert
        Assertions.assertTrue(resultado.isPresent());
        verify(empleadoRepository, times(1)).findById(idSimulado);
    }

    @Test
    public void testFindByNumeroDocumento() {
        //Arrange
        int numeroDocumentoSimulado = 103456789;
        Empleado empleadoSimulado = new Empleado(4, "CC", 103456789, "Carlos", "carlos@gmail.com", 1234567891L);
        when(empleadoRepository.findByNumeroDocumento(numeroDocumentoSimulado)).thenReturn(Optional.of(empleadoSimulado));

        //Act
        Optional<Empleado> resultado = empleadoServiceImpl.findByNumeroDocumento(numeroDocumentoSimulado);

        //Assert
        Assertions.assertTrue(resultado.isPresent());
        verify(empleadoRepository, times(1)).findByNumeroDocumento(numeroDocumentoSimulado);
    }

    @Test
    public void testSave() {
        //Arrange
        Empleado empleadoAGuardar = new Empleado(null, "CC", 103456789, "Carlos", "carlos@gmail.com", 1234567891L);
        Empleado empleadoGuardado = new Empleado(1, "CC", 103456789, "Carlos", "carlos@gmail.com", 1234567891L);
        when(empleadoRepository.save(empleadoAGuardar)).thenReturn(empleadoGuardado);

        //Act
        Empleado resultado = empleadoServiceImpl.save(empleadoAGuardar);

        //Assert
        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombreEmpleado());
        assertEquals(empleadoGuardado, resultado);
        verify(empleadoRepository, times(1)).save(empleadoAGuardar);
    }

    @Test
    public void testDeleteById() {
        //Arrange
        int idSimulado = 5;
        doNothing().when(empleadoRepository).deleteById(idSimulado);

        //Act
        empleadoServiceImpl.deleteById(idSimulado);

        //Assert
        verify(empleadoRepository, times(1)).deleteById(idSimulado);
    }
}