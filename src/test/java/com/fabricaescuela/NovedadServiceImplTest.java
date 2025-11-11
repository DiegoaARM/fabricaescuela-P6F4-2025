package com.fabricaescuela;

import com.fabricaescuela.models.entity.Novedad;
import com.fabricaescuela.models.entity.Paquete;
import com.fabricaescuela.repository.NovedadRepository;
import com.fabricaescuela.service.NovedadServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NovedadServiceImplTest {

    @Mock
    private NovedadRepository novedadRepository;

    @InjectMocks
    private NovedadServiceImpl novedadServiceImpl;

    @Test
    public void testFindByIdPaqueteId() {
        //Arrange
        Paquete paqueteSimulado = new Paquete(7, null, "123", "Juan", "Carlos", LocalDate.now(), "Medellin");

        Novedad novedad1 = new Novedad(1, paqueteSimulado, "Retraso", "Retraso por tráfico", LocalDate.now());
        Novedad novedad2 = new Novedad(2, paqueteSimulado, "Entrega fallida", "Destinatario ausente", LocalDate.now().minusDays(1));

        List<Novedad> listaSimulada = Arrays.asList(novedad1, novedad2);

        when(novedadRepository.findByIdPaquete_Id(7)).thenReturn(listaSimulada);

        //Act
        List<Novedad> resultado = novedadServiceImpl.findByIdPaqueteId(7);

        //Assert
        assertEquals(2, resultado.size());
        assertEquals(listaSimulada, resultado);
        assertEquals(7, resultado.get(0).getIdPaquete().getId());
        verify(novedadRepository, times(1)).findByIdPaquete_Id(7);
    }

    @Test
    public void testRegistrarNovedad_SinFecha() {
        //Arrange
        Novedad novedad = new Novedad();
        novedad.setDescripcion("Novedad sin fecha");
        when(novedadRepository.save(any(Novedad.class))).thenAnswer(inv -> inv.getArgument(0));

        //Act
        Novedad resultado = novedadServiceImpl.registrarNovedad(novedad);

        //Assert
        assertNotNull(resultado.getFechaHora());
        verify(novedadRepository, times(1)).save(any(Novedad.class));
    }

    @Test
    public void testRegistrarNovedad_ConFecha() {
        //Arrange
        Novedad novedad = new Novedad();
        novedad.setDescripcion("Con fecha");
        novedad.setFechaHora(LocalDate.of(2024, 1, 1));
        when(novedadRepository.save(novedad)).thenReturn(novedad);

        //Act
        Novedad resultado = novedadServiceImpl.registrarNovedad(novedad);

        //Assert
        assertEquals(LocalDate.of(2024, 1, 1), resultado.getFechaHora());
        verify(novedadRepository, times(1)).save(novedad);
    }

    @Test
    public void testObtenerTodasLasNovedades() {
        //Arrange
        List<Novedad> listaSimulada = Arrays.asList(
                new Novedad(1, null, "RETRASO", "Se retrasó el paquete", LocalDate.now()),
                new Novedad(2, null, "DAÑO", "El paquete llegó dañado", LocalDate.now())
        );
        when(novedadRepository.findAll()).thenReturn(listaSimulada);

        //Act
        List<Novedad> resultado = novedadServiceImpl.obtenerTodasLasNovedades();

        //Assert
        assertEquals(2, resultado.size());
        assertEquals("DAÑO", resultado.get(1).getTipoNovedad());
        verify(novedadRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerNovedadPorId_Existe() {
        //Arrange
        Novedad novedad = new Novedad(1, null, "RETRASO", "Test", LocalDate.now());
        when(novedadRepository.findById(1)).thenReturn(Optional.of(novedad));

        //Act
        Optional<Novedad> resultado = novedadServiceImpl.obtenerNovedadPorId(1);

        //Assert
        assertTrue(resultado.isPresent());
        assertEquals("RETRASO", resultado.get().getTipoNovedad());
        verify(novedadRepository, times(1)).findById(1);
    }

    @Test
    public void testObtenerNovedadPorId_NoExiste() {
        //Arrange
        when(novedadRepository.findById(99)).thenReturn(Optional.empty());

        //Act
        Optional<Novedad> resultado = novedadServiceImpl.obtenerNovedadPorId(99);

        //Assert
        assertFalse(resultado.isPresent());
        verify(novedadRepository, times(1)).findById(99);
    }

    @Test
    public void testActualizarNovedad_Existe() {
        //Arrange
        Novedad existente = new Novedad(1, null, "RETRASO", "Descripción vieja", LocalDate.of(2023, 1, 1));
        Novedad nueva = new Novedad(1, null, "DAÑO", "Descripción nueva", LocalDate.of(2024, 1, 1));

        when(novedadRepository.findById(1)).thenReturn(Optional.of(existente));
        when(novedadRepository.save(any(Novedad.class))).thenAnswer(inv -> inv.getArgument(0));

        //Act
        Novedad resultado = novedadServiceImpl.actualizarNovedad(1, nueva);

        //Assert
        assertNotNull(resultado);
        assertEquals("DAÑO", resultado.getTipoNovedad());
        assertEquals("Descripción nueva", resultado.getDescripcion());
        verify(novedadRepository).save(any(Novedad.class));
    }

    @Test
    public void testActualizarNovedad_NoExiste() {
        //Arrange
        Novedad nueva = new Novedad(1, null, "DAÑO", "Nueva", LocalDate.now());
        when(novedadRepository.findById(1)).thenReturn(Optional.empty());

        //Act
        Novedad resultado = novedadServiceImpl.actualizarNovedad(1, nueva);

        //Assert
        assertNull(resultado);
        verify(novedadRepository, never()).save(any());
    }

    @Test
    public void testEliminarNovedad() {
        //Arrange
        int idSimulado = 7;
        doNothing().when(novedadRepository).deleteById(idSimulado);

        //Act
        novedadServiceImpl.eliminarNovedad(idSimulado);

        //Assert
        verify(novedadRepository, times(1)).deleteById(idSimulado);
    }
}