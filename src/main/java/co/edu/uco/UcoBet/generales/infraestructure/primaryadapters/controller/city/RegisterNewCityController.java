package co.edu.uco.UcoBet.generales.infraestructure.primaryadapters.controller.city;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.UcoBet.generales.application.primaryports.dto.city.RegisterNewCityDto;
import co.edu.uco.UcoBet.generales.application.primaryports.interactor.city.RegisterNewCityInteractor;
import co.edu.uco.UcoBet.generales.crosscutting.exceptions.UcoBetException;
import co.edu.uco.UcoBet.generales.crosscutting.helpers.UUIDHelper;
import co.edu.uco.UcoBet.generales.infraestructure.primaryadapters.controller.response.CityResponse;
import co.edu.uco.UcoBet.generales.infraestructure.primaryadapters.services.SanitizerService;

@RestController
@RequestMapping("/generales/api/v1/cities")
public class RegisterNewCityController {
	
	private final RegisterNewCityInteractor registerNewCityInteractor;
	private final SanitizerService sanitizerService;

	// Inyección de dependencias para RegisterNewCityInteractor y SanitizerService
	public RegisterNewCityController(final RegisterNewCityInteractor registerNewCityInteractor,
	                                 final SanitizerService sanitizerService) {
	    this.registerNewCityInteractor = registerNewCityInteractor;
	    this.sanitizerService = sanitizerService;
	}
	
	@GetMapping
	public RegisterNewCityDto getDummy() {
	    return RegisterNewCityDto.create("rionegro", UUIDHelper.getDefault());
	}
	
	@PostMapping
	public ResponseEntity<CityResponse> crear(@RequestBody RegisterNewCityDto city) {
	    HttpStatus httpStatusCode = HttpStatus.ACCEPTED;
	    CityResponse ciudadResponse = new CityResponse();

	    try {
	        // Sanitiza el nombre de la ciudad
	        String sanitizedName = sanitizerService.sanitize(city.getName());

	        // Crea un nuevo DTO con el nombre sanitizado
	        RegisterNewCityDto citySanitized = RegisterNewCityDto.create(sanitizedName, city.getState());

	        // Ejecuta el proceso de registro de la ciudad
	        registerNewCityInteractor.execute(citySanitized);
	        ciudadResponse.getMensajes().add("Ciudad creada exitosamente");
	    } catch (final UcoBetException excepcion) {
	        httpStatusCode = HttpStatus.BAD_REQUEST;
	        ciudadResponse.getMensajes().add(excepcion.getUserMessage());
	        excepcion.printStackTrace();
	    } catch (final Exception excepcion) {
	        httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
	        ciudadResponse.getMensajes().add("Se ha presentado un problema tratando de registrar la nueva Ciudad");
	        excepcion.printStackTrace();
	    }

	    return new ResponseEntity<>(ciudadResponse, httpStatusCode);
	}
}