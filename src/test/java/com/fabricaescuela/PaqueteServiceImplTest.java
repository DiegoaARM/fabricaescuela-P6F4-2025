package com.fabricaescuela;

import com.fabricaescuela.models.dto.PaqueteResponseDto;
import com.fabricaescuela.models.entity.Paquete;
import com.fabricaescuela.repository.PaqueteRepository;
import com.fabricaescuela.service.PaqueteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaqueteServiceImplTest {

    @Mock
    private PaqueteRepository paqueteRepository;

    @InjectMocks
    private PaqueteServiceImpl paqueteServiceImpl;

    @Test
    public void testConsultarPorCodigo_CuandoExistePaquete() {
        //Arrange
        Paquete paqueteSimulado = new Paquete(
                1,
                null,
                "PKT123",
                "Juan Pérez",
                "María Gómez",
                LocalDate.now(),
                "Medellín"
        );

        when(paqueteRepository.findByCodigoPaquete("PKT123")).thenReturn(Optional.of(paqueteSimulado));

        //Act
        Optional<PaqueteResponseDto> resultado = paqueteServiceImpl.consultarPorCodigo("PKT123");

        //Assert
        assertTrue(resultado.isPresent());
        assertEquals(paqueteSimulado.getId(), resultado.get().getId());
        assertEquals(paqueteSimulado.getCodigoPaquete(), resultado.get().getCodigoPaquete());
        assertEquals(paqueteSimulado.getRemitente(), resultado.get().getRemitente());
        assertEquals(paqueteSimulado.getDestinatario(), resultado.get().getDestinatario());
        assertEquals("...", resultado.get().getDescripcion()); // descripción temporal
        verify(paqueteRepository, times(1)).findByCodigoPaquete("PKT123");
    }

    @Test
    public void testConsultarPorCodigo_CuandoNoExistePaquete() {
        //Arrange
        String codigoSimulado = "NO_EXISTE";
        when(paqueteRepository.findByCodigoPaquete(codigoSimulado))
                .thenReturn(Optional.empty());

        //Act
        Optional<PaqueteResponseDto> resultado = paqueteServiceImpl.consultarPorCodigo(codigoSimulado);

        //Assert
        assertTrue(resultado.isEmpty());
        verify(paqueteRepository, times(1)).findByCodigoPaquete(codigoSimulado);
    }
}
