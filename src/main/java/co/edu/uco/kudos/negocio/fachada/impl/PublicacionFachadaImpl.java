package co.edu.uco.kudos.negocio.fachada.impl;

import java.util.UUID;

import co.edu.uco.kudos.dto.CalificacionDTO;
import co.edu.uco.kudos.dto.CalificarDTO;
import co.edu.uco.kudos.dto.JustificacionDTO;
import co.edu.uco.kudos.dto.NuevoReporteDTO;
import co.edu.uco.kudos.dto.ReporteDTO;
import co.edu.uco.kudos.dto.ResumenCalificacionDTO;
import co.edu.uco.kudos.dto.VetoDTO;
import co.edu.uco.kudos.negocio.fachada.FachadaBase;
import co.edu.uco.kudos.negocio.fachada.PublicacionFachada;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.CalificacionEnsambladorDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.ReporteEnsambladorDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.VetoEnsambladorDTO;
import co.edu.uco.kudos.negocio.negocio.impl.CalificacionNegocioImpl;
import co.edu.uco.kudos.negocio.negocio.impl.ReporteNegocioImpl;
import co.edu.uco.kudos.negocio.negocio.impl.VetoNegocioImpl;

public final class PublicacionFachadaImpl extends FachadaBase implements PublicacionFachada {

	@Override
	public CalificacionDTO calificar(UUID idActor, UUID idPublicacion, CalificarDTO datos) {
		return ejecutarEnTransaccion("registrar la calificación", daoFactory -> CalificacionEnsambladorDTO.getInstancia()
				.convertirADTO(new CalificacionNegocioImpl(daoFactory).calificar(idActor, idPublicacion,
						datos.getEstrellas())));
	}

	@Override
	public ResumenCalificacionDTO consultarCalificaciones(UUID idActor, UUID idPublicacion) {
		return ejecutarConsulta("consultar las calificaciones", daoFactory -> {
			var resumen = new CalificacionNegocioImpl(daoFactory).consultarResumen(idActor, idPublicacion);
			return new ResumenCalificacionDTO(resumen.promedio(), resumen.cantidad(), resumen.miCalificacion());
		});
	}

	@Override
	public ReporteDTO reportar(UUID idActor, UUID idPublicacion, NuevoReporteDTO datos) {
		return ejecutarEnTransaccion("registrar el reporte", daoFactory -> ReporteEnsambladorDTO.getInstancia()
				.convertirADTO(new ReporteNegocioImpl(daoFactory).reportar(idActor, idPublicacion, datos.getMotivo(),
						datos.getDescripcion())));
	}

	@Override
	public VetoDTO vetar(UUID idActor, UUID idPublicacion, JustificacionDTO datos) {
		return ejecutarEnTransaccion("vetar la publicación", daoFactory -> VetoEnsambladorDTO.getInstancia()
				.convertirADTO(new VetoNegocioImpl(daoFactory).vetar(idActor, idPublicacion, datos.getJustificacion())));
	}
}
