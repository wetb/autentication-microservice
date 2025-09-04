package co.com.bancolombia.api;

import co.com.bancolombia.api.router.CreateUserRouter;
import co.com.bancolombia.api.router.FindUserRouter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {
    private final CreateUserRouter createUserRouter;
    private final FindUserRouter findUserRouter;


    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .add(createUserRouter.usuarioRouterFunction())
                .add(findUserRouter.findUserByIdentityDocumentFunction())
                .build();
    }
}
