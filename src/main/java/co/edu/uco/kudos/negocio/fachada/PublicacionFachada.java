package co.edu.uco.kudos.negocio.fachada;

import java.util.UUID;

import co.edu.uco.kudos.dto.CalificacionDTO;
import co.edu.uco.kudos.dto.CalificarDTO;
import co.edu.uco.kudos.dto.JustificacionDTO;
import co.edu.uco.kudos.dto.NuevoReporteDTO;
import co.edu.uco.kudos.dto.ReporteDTO;
import co.edu.uco.kudos.dto.ResumenCalificacionDTO;
import co.edu.uco.kudos.dto.VetoDTO;

public interface PublicacionFachada {

	CalificacionDTO calificar(UUID idActor, UUID idPublicacion, CalificarDTO datos);

	ResumenCalificacionDTO consultarCalificaciones(UUID idActor, UUID idPublicacion);

	ReporteDTO reportar(UUID idActor, UUID idPublicacion, NuevoReporteDTO datos);

	VetoDTO vetar(UUID idActor, UUID idPublicacion, JustificacionDTO datos);
}
