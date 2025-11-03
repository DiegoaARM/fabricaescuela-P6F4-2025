package com.fabricaescuela;

import com.fabricaescuela.models.entity.HistorialEstado;
import com.fabricaescuela.models.entity.Paquete;
import com.fabricaescuela.repository.HistorialEstadoRepository;
import com.fabricaescuela.service.HistorialEstadoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistorialEstadoServiceImplTest {

    @Mock
    private HistorialEstadoRepository historialEstadoRepository;

    @InjectMocks
    private HistorialEstadoServiceImpl historialEstadoServiceImpl;

    @Test
    public void testFindAll() {
        //Arrange
        HistorialEstado historial1 = new HistorialEstado(1, null, null, null, LocalDate.now());
        HistorialEstado historial2 = new HistorialEstado(2, null, null, null, LocalDate.now().minusDays(1));
        List<HistorialEstado> listaSimulada = Arrays.asList(historial1, historial2);
        when(historialEstadoRepository.findAll()).thenReturn(listaSimulada);

        //Act
        List<HistorialEstado> resultado = historialEstadoServiceImpl.findAll();

        //Assert
        assertEquals(2, resultado.size());
        assertEquals(listaSimulada, resultado);
        verify(historialEstadoRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        //Arrange
        HistorialEstado historial = new HistorialEstado(5, null, null, null, LocalDate.now());
        when(historialEstadoRepository.findById(5)).thenReturn(Optional.of(historial));

        //Act
        Optional<HistorialEstado> resultado = historialEstadoServiceImpl.findById(5);

        //Assert
        Assertions.assertTrue(resultado.isPresent());
        assertEquals(5, resultado.get().getId());
        verify(historialEstadoRepository, times(1)).findById(5);
    }

    @Test
    public void testFindByIdPaquete() {
        //Arrange
        Paquete paqueteSimulado = new Paquete();
        paqueteSimulado.setId(10);

        HistorialEstado historial1 = new HistorialEstado();
        historial1.setId(1);
        historial1.setIdPaquete(paqueteSimulado);
        historial1.setFechaHora(LocalDate.now());

        HistorialEstado historial2 = new HistorialEstado();
        historial2.setId(2);
        historial2.setIdPaquete(paqueteSimulado);
        historial2.setFechaHora(LocalDate.now().minusDays(2));

        List<HistorialEstado> listaSimulada = Arrays.asList(historial1, historial2);
        when(historialEstadoRepository.findByIdPaquete_Id(10)).thenReturn(listaSimulada);

        //Act
        List<HistorialEstado> resultado = historialEstadoServiceImpl.findByIdPaquete(10);

        //Assert
        assertEquals(2, resultado.size());
        assertEquals(10, resultado.get(0).getIdPaquete().getId());
        assertEquals(listaSimulada, resultado);
        verify(historialEstadoRepository, times(1)).findByIdPaquete_Id(10);
    }

    @Test
    public void testSave() {
        //Arrange
        LocalDate fechaHora = LocalDate.now();
        HistorialEstado historialAGuardar = new HistorialEstado(null, null, null, null, fechaHora);
        HistorialEstado historialGuardado = new HistorialEstado(1, null, null, null, fechaHora);

        when(historialEstadoRepository.save(historialAGuardar)).thenReturn(historialGuardado);

        //Act
        HistorialEstado resultado = historialEstadoServiceImpl.save(historialAGuardar);

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(historialGuardado, resultado);
        verify(historialEstadoRepository, times(1)).save(historialAGuardar);
    }

    @Test
    public void testDeleteById() {
        //Arrange
        int idSimulado = 3;
        doNothing().when(historialEstadoRepository).deleteById(idSimulado);

        //Act
        historialEstadoServiceImpl.deleteById(idSimulado);

        //Assert
        verify(historialEstadoRepository, times(1)).deleteById(idSimulado);
    }
}