package classroom.scheduler.service;

import classroom.scheduler.dto.SalaDTO;
import classroom.scheduler.dto.UsuarioDTO;
import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Sala;
import classroom.scheduler.repository.SalaRepository;
import classroom.scheduler.validacoes.Validacao;
import classroom.scheduler.validacoes.ValidacaoSalaCapacidade;
import classroom.scheduler.validacoes.ValidacaoSalaNumeroJaExistente;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SalaService {
    @Autowired
    SalaRepository repositorio;

    @Transactional
    public ResponseEntity<SalaDTO> criarSala(SalaDTO dto) {
        Sala sala = new Sala(dto);

        List<Validacao> validacoes = new ArrayList<>(List.of(
                new ValidacaoSalaCapacidade(),
                new ValidacaoSalaNumeroJaExistente(repositorio)));

        validacoes.forEach(v -> v.validar(sala));
        repositorio.save(sala);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SalaDTO(sala));
    }

    public ResponseEntity<SalaDTO> buscarNumeroSala(int numeroSala) {
        Sala sala = repositorio.findByNumeroSala(numeroSala)
                .orElseThrow(() -> new NoSuchElementException("Nenhuma sala com este número foi localizada no banco de dados."));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SalaDTO(sala));
    }

    public ResponseEntity<List<SalaDTO>> buscarTodasSalas() {
        List<Sala> salas = repositorio.findBySalaAtivaIsTrue();
        List<SalaDTO> salasDTO = salas.stream().map(SalaDTO::new).toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(salasDTO);
    }

    @Transactional
    public ResponseEntity<String> deletarSalaNumero(Long id) {
        Optional<Sala> optionalSala = Optional.of(repositorio.getReferenceById(id));
        Sala sala = optionalSala
                .orElseThrow(() -> new NoSuchElementException("Sala não localizada no banco de dados."));
        sala.setSalaAtiva(false);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Sala deletada com sucesso.");
    }

    @Transactional
    public ResponseEntity<SalaDTO> editarSala(SalaDTO dto) {

        Optional<Sala> optionalSala = Optional.of(repositorio.getReferenceById(dto.id()));
        Sala sala = optionalSala
                .orElseThrow(() -> new NoSuchElementException("Sala não localizada no banco de dados."));

        if (dto.numeroSala() != null) {

            if (repositorio.findByNumeroSala(dto.numeroSala()).isEmpty()) {
                sala.setNumeroSala(dto.numeroSala());

            } else {
                throw new ValidacaoException("Já existe uma sala no banco de dados com este número.");
            }

        }

        if (dto.capacidade() != null) {
            if (dto.capacidade() > 0) {
                sala.setCapacidade(dto.capacidade());
            } else {
                throw new ValidacaoException("A capacidade deve ser superior a 0");
            }
        }
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new SalaDTO(sala));


    }
}
