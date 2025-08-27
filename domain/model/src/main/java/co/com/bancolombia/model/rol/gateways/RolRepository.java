package co.com.bancolombia.model.rol.gateways;

import co.com.bancolombia.model.rol.Rol;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RolRepository {
    Mono<Rol> save (Rol rol);
    Flux<Rol> findAll();
    Mono<Rol> findById(Long idRol);
    Mono<Rol> deleteById(Long idRol);
}
