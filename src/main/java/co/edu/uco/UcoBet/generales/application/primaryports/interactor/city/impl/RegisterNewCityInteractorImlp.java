package co.edu.uco.UcoBet.generales.application.primaryports.interactor.city.impl;

import org.springframework.stereotype.Service;

import co.edu.uco.UcoBet.generales.application.primaryports.dto.city.RegisterNewCityDto;
import co.edu.uco.UcoBet.generales.application.primaryports.interactor.city.RegisterNewCityInteractor;
import co.edu.uco.UcoBet.generales.application.primaryports.mapper.RegisterNewCityDtoMapper;
import co.edu.uco.UcoBet.generales.application.usecase.city.RegisterNewCity;
import co.edu.uco.UcoBet.generales.crosscutting.tracing.TraceService;
import co.edu.uco.UcoBet.generales.domain.city.CityDomain;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegisterNewCityInteractorImlp implements RegisterNewCityInteractor{
	
	private RegisterNewCity registerNewCityUseCase;
	private final TraceService traceService;
	
	

	public RegisterNewCityInteractorImlp(RegisterNewCity registerNewCityUseCase, TraceService traceService) {
		this.registerNewCityUseCase = registerNewCityUseCase;
		this.traceService = traceService;
	}


	@Override
	public void execute(RegisterNewCityDto data) {
		traceService.logInfo("Iniciando el registro de la ciudad: " + data.getName());
		
		// Convertir DTO a dominio
		var cityDomain = RegisterNewCityDtoMapper.INSTANCE.toDomain(data);
		
		try {
			// Llamar al caso de uso
			registerNewCityUseCase.execute(cityDomain);
			traceService.logInfo("Ciudad registrada exitosamente: " + data.getName());
		} catch (Exception e) {
			traceService.logError("Error al registrar la ciudad: " + data.getName(), e);
			throw e; // Relanzar la excepción para que sea manejada en el controlador
		}
	}

}
