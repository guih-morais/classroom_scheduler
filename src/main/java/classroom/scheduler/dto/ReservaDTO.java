package classroom.scheduler.dto;

import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.Sala;
import classroom.scheduler.models.StatusReserva;
import classroom.scheduler.models.Usuario;

import java.time.LocalDateTime;

public record ReservaDTO(
        Long id,
        LocalDateTime inicioReserva,
        LocalDateTime fimReserva,
        String nome,
        int numeroSala,
        StatusReserva status
) {

    public ReservaDTO(Reserva reserva) {
        this(reserva.getId(),
                reserva.getInicioReserva(),
                reserva.getFimReserva(),
                reserva.getUsuario().getNome(),
                reserva.getSala().getNumeroSala(),
                reserva.getStatusReserva());
    }

}
