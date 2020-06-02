package br.com.anibal.armory.simpleserver.services;

import br.com.anibal.armory.simpleserver.models.Lib;
import br.com.anibal.armory.simpleserver.models.LibResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LibService {

    private final String CDN_URL = "https://api.cdnjs.com/libraries?search={search}&fields=name,version";

    @Autowired
    private WebClient libWebClient;

    public Mono<LibResponse> search(String search) {
        return libWebClient.get().uri(CDN_URL, search)
                .retrieve()
                .bodyToMono(LibResponse.class);
    }

}
