package com.fabricaescuela;

import com.fabricaescuela.models.entity.Estado;
import com.fabricaescuela.repository.EstadoRepository;
import com.fabricaescuela.service.EstadoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstadoServiceImplTest {

    @Mock
    private EstadoRepository estadoRepository;

    @InjectMocks
    private EstadoServiceImpl estadoServiceImpl;

    @Test
    public void testFindAll() {
        //Arrange
        List<Estado> listaSimulada = Arrays.asList(
                new Estado(1, "En bodega", "El paquete se encuentra en la bodega"),
                new Estado(2, "En ruta", "El paquete está en camino hacia su destino")
        );
        when(estadoRepository.findAll()).thenReturn(listaSimulada);

        List<Estado> esperado = Arrays.asList(
                new Estado(1, "En bodega", "El paquete se encuentra en la bodega"),
                new Estado(2, "En ruta", "El paquete está en camino hacia su destino")
        );

        //Act
        List<Estado> resultado = estadoServiceImpl.findAll();

        //Assert
        assertEquals(2, resultado.size());
        assertEquals("En bodega", resultado.get(0).getNombreEstado());
        assertEquals(esperado, resultado);
        verify(estadoRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        //Arrange
        int idSimulado = 3;
        Estado estadoSimulado = new Estado(3, "Entregado", "El paquete ha sido entregado al destinatario");
        when(estadoRepository.findById(idSimulado)).thenReturn(Optional.of(estadoSimulado));

        //Act
        Optional<Estado> resultado = estadoServiceImpl.findById(idSimulado);

        //Assert
        assertTrue(resultado.isPresent());
        assertEquals("Entregado", resultado.get().getNombreEstado());
        verify(estadoRepository, times(1)).findById(idSimulado);
    }

    @Test
    public void testSave() {
        //Arrange
        Estado estadoAGuardar = new Estado(null, "En bodega", "El paquete se encuentra en la bodega");
        Estado estadoGuardado = new Estado(1, "En bodega", "El paquete se encuentra en la bodega");
        when(estadoRepository.save(estadoAGuardar)).thenReturn(estadoGuardado);

        //Act
        Estado resultado = estadoServiceImpl.save(estadoAGuardar);

        //Assert
        assertNotNull(resultado);
        assertEquals("En bodega", resultado.getNombreEstado());
        assertEquals(estadoGuardado, resultado);
        verify(estadoRepository, times(1)).save(estadoAGuardar);
    }

    @Test
    public void testDeleteById() {
        //Arrange
        int idSimulado = 2;
        doNothing().when(estadoRepository).deleteById(idSimulado);

        //Act
        estadoServiceImpl.deleteById(idSimulado);

        //Assert
        verify(estadoRepository, times(1)).deleteById(idSimulado);
    }
}
