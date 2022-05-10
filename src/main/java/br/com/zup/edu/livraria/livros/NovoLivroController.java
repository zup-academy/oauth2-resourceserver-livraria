package br.com.zup.edu.livraria.livros;

import br.com.zup.edu.livraria.autores.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class NovoLivroController {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    @PostMapping("/api/livros")
    public ResponseEntity<?> cadastra(@RequestBody @Valid NovoLivroRequest request, UriComponentsBuilder uriBuilder) {

        Livro livro = request.toModel(autorRepository);
        repository.save(livro);

        URI location = uriBuilder.path("/api/livros/{id}")
                .buildAndExpand(livro.getId())
                .toUri();

        return ResponseEntity
                .created(location).build();
    }
}
