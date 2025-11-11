package com.fabricaescuela;

import com.fabricaescuela.models.dto.PaqueteDireccionUpdateRequest;
import com.fabricaescuela.models.dto.PaqueteResponseDto;
import com.fabricaescuela.models.entity.Estado;
import com.fabricaescuela.models.entity.HistorialEstado;
import com.fabricaescuela.models.entity.Paquete;
import com.fabricaescuela.repository.HistorialEstadoRepository;
import com.fabricaescuela.repository.PaqueteRepository;
import com.fabricaescuela.service.PaqueteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaqueteServiceImplTest {

    @Mock
    private PaqueteRepository paqueteRepository;

    @Mock
    private HistorialEstadoRepository historialEstadoRepository;

    @InjectMocks
    private PaqueteServiceImpl paqueteServiceImpl;

    void testObtenerTodos() {
        // Arrange
        Paquete paquete = new Paquete(1, null, "PKG001", "Juan", "Maria", LocalDate.now(), "Bogotá");
        Estado estado = new Estado(1, "En Ruta", "En camino al lugar");

        // Empleado puede ser null si no se usa en el test
        HistorialEstado historial = new HistorialEstado(1, null, paquete, estado, LocalDate.now());

        when(paqueteRepository.findAll()).thenReturn(List.of(paquete));
        when(historialEstadoRepository.findTopByIdPaquete_IdOrderByFechaHoraDesc(1))
                .thenReturn(Optional.of(historial));

        // Act
        List<PaqueteResponseDto> resultado = paqueteServiceImpl.obtenerTodos();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("en ruta", resultado.get(0).getEstadoActual().toLowerCase());
        verify(paqueteRepository, times(1)).findAll();
        verify(historialEstadoRepository, times(1))
                .findTopByIdPaquete_IdOrderByFechaHoraDesc(1);
    }

    @Test
    public void testConsultarPorCodigo_NoExiste() {
        //Arrange
        String codigoSimulado = "ABC";
        when(paqueteRepository.findByCodigoPaquete(codigoSimulado))
                .thenReturn(Optional.empty());

        //Act
        Optional<PaqueteResponseDto> resultado = paqueteServiceImpl.consultarPorCodigo(codigoSimulado);

        //Assert
        assertTrue(resultado.isEmpty());
        verify(paqueteRepository, times(1)).findByCodigoPaquete(codigoSimulado);
    }

    @Test
    public void testConsultarPorCodigo_Existe() {
        // Arrange
        Paquete paquete = new Paquete(1, null, "PKG002", "Pedro", "Luis", LocalDate.now(), "Medellín");
        Estado estado = new Estado(1, "En Ruta", "En camino al lugar");
        HistorialEstado historial = new HistorialEstado(1, null, paquete, estado, LocalDate.now());

        when(paqueteRepository.findByCodigoPaquete("PKG002")).thenReturn(Optional.of(paquete));
        when(historialEstadoRepository.findTopByIdPaquete_IdOrderByFechaHoraDesc(1))
                .thenReturn(Optional.of(historial));

        // Act
        Optional<PaqueteResponseDto> resultado = paqueteServiceImpl.consultarPorCodigo("PKG002");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("En Ruta", resultado.get().getEstadoActual());
        verify(paqueteRepository).findByCodigoPaquete("PKG002");
    }

    @Test
    public void testConsultarEnRutaPorCodigo_EnRuta() {
        // Arrange
        Paquete paquete = new Paquete(1, null, "PKG003", "Ana", "Mario", LocalDate.now(), "Cali");
        Estado estado = new Estado(1, "En Ruta", "EN camino");
        HistorialEstado historial = new HistorialEstado(1, null, paquete, estado, LocalDate.now());

        when(paqueteRepository.findByCodigoPaquete("PKG003")).thenReturn(Optional.of(paquete));
        when(historialEstadoRepository.findTopByIdPaquete_IdOrderByFechaHoraDesc(1))
                .thenReturn(Optional.of(historial));

        // Act
        Optional<PaqueteResponseDto> resultado = paqueteServiceImpl.consultarEnRutaPorCodigo("PKG003");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("En Ruta", resultado.get().getEstadoActual());
    }

    @Test
    public void testConsultarEnRutaPorCodigo_NoEnRuta() {
        // Arrange
        Paquete paquete = new Paquete(2, null, "PKG004", "Laura", "Andrés", LocalDate.now(), "Cartagena");
        Estado estado = new Estado(1, "En Ruta", "EN camino");
        HistorialEstado historial = new HistorialEstado(1, null, paquete, estado, LocalDate.now());

        when(paqueteRepository.findByCodigoPaquete("PKG004")).thenReturn(Optional.of(paquete));
        when(historialEstadoRepository.findTopByIdPaquete_IdOrderByFechaHoraDesc(2))
                .thenReturn(Optional.of(historial));

        // Act
        Optional<PaqueteResponseDto> resultado = paqueteServiceImpl.consultarEnRutaPorCodigo("PKG004");

        // Assert
        assertTrue(resultado.isPresent());

    }

    @Test
    public void testActualizarDireccion_Exito() {
        // Arrange
        Paquete paquete = new Paquete(1, null, "PKG005", "Sofia", "Diego", LocalDate.now(), "Medellín");
        Estado estado = new Estado(1, "En Ruta", "EN camino");
        HistorialEstado historial = new HistorialEstado(1, null, paquete, estado, LocalDate.now());
        PaqueteDireccionUpdateRequest request = new PaqueteDireccionUpdateRequest("Bogotá", "Carlos");

        when(paqueteRepository.findByCodigoPaquete("PKG005")).thenReturn(Optional.of(paquete));
        when(historialEstadoRepository.findTopByIdPaquete_IdOrderByFechaHoraDesc(1))
                .thenReturn(Optional.of(historial));
        when(paqueteRepository.save(any(Paquete.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        PaqueteResponseDto resultado = paqueteServiceImpl.actualizarDireccion("PKG005", request);

        // Assert
        assertNotNull(resultado);
        assertEquals("Bogotá", resultado.getDestino());
        assertEquals("Carlos", resultado.getDestinatario());
        verify(paqueteRepository).save(any(Paquete.class));
    }

    @Test
    public void testActualizarDireccion_PaqueteNoEncontrado() {
        // Arrange
        when(paqueteRepository.findByCodigoPaquete("NO_EXISTE")).thenReturn(Optional.empty());
        PaqueteDireccionUpdateRequest request = new PaqueteDireccionUpdateRequest("Cali", "Laura");

        // Act & Assert
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> paqueteServiceImpl.actualizarDireccion("NO_EXISTE", request));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

}