package co.edu.uco.UcoBet.generales;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import co.edu.uco.UcoBet.generales.application.primaryports.dto.city.RegisterNewCityDto;
import co.edu.uco.UcoBet.generales.application.primaryports.interactor.city.RegisterNewCityInteractor;
import co.edu.uco.UcoBet.generales.crosscutting.helpers.UUIDHelper;
import co.edu.uco.UcoBet.generales.infraestructure.primaryadapters.controller.city.RegisterNewCityController;
import co.edu.uco.UcoBet.generales.infraestructure.primaryadapters.services.SanitizerService;

public class RegisterNewCityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegisterNewCityInteractor registerNewCityInteractor;

    @Mock
    private SanitizerService sanitizerService;

    @InjectMocks
    private RegisterNewCityController registerNewCityController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registerNewCityController).build();
    }

    @Test
    public void testCrearCitySuccess() throws Exception {
        // Datos de entrada para la ciudad
        RegisterNewCityDto cityDto = RegisterNewCityDto.create("Medellín", UUIDHelper.getDefault());

        // Configuración del comportamiento de los mocks
        when(sanitizerService.sanitize(any(String.class))).thenReturn("Medellín");
        doNothing().when(registerNewCityInteractor).execute(any(RegisterNewCityDto.class));

        mockMvc.perform(post("/generales/api/v1/cities")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Medellín\",\"state\":\"" + UUIDHelper.getDefault() + "\"}"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"mensajes\":[\"Ciudad creada exitosamente\"]}"));
    }
}


