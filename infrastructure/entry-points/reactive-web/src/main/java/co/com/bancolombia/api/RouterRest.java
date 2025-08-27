package co.com.bancolombia.api;

import co.com.bancolombia.api.config.UserPath;
import co.com.bancolombia.api.router.CreateUserRouter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {
    private final CreateUserRouter createUserRouter;
    private final UserPath userPath;
    private final Handler userHandler;
//    @Bean
//    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
//        return route(POST(userPath.getUsers()), userHandler::listenSaveUser)
//                .andRoute(GET("/api/usecase/path"), handler::listenGETUseCase)
//                .andRoute(POST("/api/usecase/otherpath"), handler::listenPOSTUseCase)
//                .and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase));
//    }
    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .add(createUserRouter.usuarioRouterFunction())
                .build();
    }
}
