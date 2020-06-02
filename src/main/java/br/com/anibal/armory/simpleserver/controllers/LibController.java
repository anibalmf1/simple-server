package br.com.anibal.armory.simpleserver.controllers;

import br.com.anibal.armory.simpleserver.models.Lib;
import br.com.anibal.armory.simpleserver.models.LibResponse;
import br.com.anibal.armory.simpleserver.services.LibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/lib")
@CrossOrigin
public class LibController {

    @Autowired
    LibService service;

    @GetMapping
    public Mono<LibResponse> list(@RequestParam String search) {
        try {
            return service.search(search);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Lib> listFlux(@RequestParam String search) {
        try {
            return service.search(search)
                    .flatMapMany(libResponse -> Flux.fromIterable(libResponse.getResults()).delayElements(Duration.ofMillis(100)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
