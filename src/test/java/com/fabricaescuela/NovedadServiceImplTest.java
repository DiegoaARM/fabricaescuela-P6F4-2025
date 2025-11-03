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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NovedadServiceImplTest {

    @Mock
    private NovedadRepository novedadRepository;

    @InjectMocks
    private NovedadServiceImpl novedadServiceImpl;

    @Test
    public void testFindByIdPaqueteId() {
        // ---------- Arrange ----------
        Paquete paqueteSimulado = new Paquete(7, null, "123", "Juan", "Carlos", LocalDate.now(), "Medellin");

        Novedad novedad1 = new Novedad(1, paqueteSimulado, "Retraso", "Retraso por tráfico", LocalDate.now());
        Novedad novedad2 = new Novedad(2, paqueteSimulado, "Entrega fallida", "Destinatario ausente", LocalDate.now().minusDays(1));

        List<Novedad> listaSimulada = Arrays.asList(novedad1, novedad2);

        when(novedadRepository.findByIdPaquete_Id(7)).thenReturn(listaSimulada);

        // ---------- Act ----------
        List<Novedad> resultado = novedadServiceImpl.findByIdPaqueteId(7);

        // ---------- Assert ----------
        assertEquals(2, resultado.size());
        assertEquals(listaSimulada, resultado);
        assertEquals(7, resultado.get(0).getIdPaquete().getId());
        verify(novedadRepository, times(1)).findByIdPaquete_Id(7);
    }
}