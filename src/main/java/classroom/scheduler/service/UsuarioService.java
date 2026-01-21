package classroom.scheduler.service;

import classroom.scheduler.dto.UsuarioDTO;
import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.validacoes.*;
import classroom.scheduler.models.Usuario;
import classroom.scheduler.repository.UsuarioRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repositorio;

    @Transactional
    public ResponseEntity<UsuarioDTO> criarUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario(dto);

        List<Validacao> validacoes = new ArrayList<>(List.of(
                new ValidacaoVazioOuNulo(),
                new ValidacaoUsuarioNomeJaCadastrado(repositorio),
                new ValidacaoUsuarioEmailJaCadastrado(repositorio)));
                validacoes.forEach(v -> v.validar(usuario));

        repositorio.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioDTO(usuario));
    }


    public ResponseEntity<UsuarioDTO> buscarUsuario(String nome) {
        //Tenta buscar pelo Nome
        Optional<Usuario> optionalUsuario = repositorio.findByNome(nome);
        if(optionalUsuario.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new UsuarioDTO(optionalUsuario.get()));
        }
        //Tenta Buscar pelo Email
        optionalUsuario = repositorio.findByEmail(nome);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UsuarioDTO(optionalUsuario
                        .orElseThrow(() -> new NoSuchElementException("Este Nome ou Email não foi encontrado no banco de dados"))));
    }
    public ResponseEntity<List<UsuarioDTO>> buscarTodosUsuarios() {
        List<Usuario> usuarios = repositorio.findAll();
        List<UsuarioDTO> usuariosdto = usuarios.stream().map(UsuarioDTO::new).toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuariosdto);
    }
    @Transactional
    public ResponseEntity<String> deletarUsuario(Long id) {
        Usuario usuario = repositorio.getReferenceById(id);
        repositorio.delete(usuario);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Usuário deletado com sucesso!");
    }

    @Transactional
    public ResponseEntity<UsuarioDTO> editarUsuario(UsuarioDTO dto) {
        Optional<Usuario> optionalUsuario = Optional.of(repositorio.getReferenceById(dto.id()));
        Usuario usuario = optionalUsuario.orElseThrow(
                () -> new NoSuchElementException("Usuário não localizado no banco de dados"));

        if(StringUtils.isBlank(dto.nome())) {
            throw new ValidacaoException("O campo nome do Usuário deve ser preenchido");
        }
        usuario.setNome(dto.nome());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new UsuarioDTO(usuario));
    }
}
