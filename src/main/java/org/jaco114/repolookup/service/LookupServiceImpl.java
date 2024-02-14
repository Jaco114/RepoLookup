package org.jaco114.repolookup.service;

import org.jaco114.repolookup.dto.BranchResponse;
import org.jaco114.repolookup.dto.LookupRequest;
import org.jaco114.repolookup.dto.LookupResponse;
import org.jaco114.repolookup.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class LookupServiceImpl implements LookupService {

    private final String ITEMS_PER_PAGE = "100";

    private final WebClient webClient;

    public LookupServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<LookupResponse> lookupUser(LookupRequest lookupRequest) {
        List<LookupResponse> allRepos = new ArrayList<>();
        String login = lookupRequest.username();

        int page = 1;
        while (true) {
            List<LookupResponse> repos = retrieveRepos(login, page);

            if (repos != null && !repos.isEmpty()) {
                allRepos.addAll(repos);
                page++;
            } else
                break;
        }

        insertBranchesInfo(allRepos);
        return allRepos;
    }


    private List<LookupResponse> retrieveRepos(String login, int page) {
        String url = "https://api.github.com/users/%s/repos?per_page=%s&page=%s".formatted(login, ITEMS_PER_PAGE, page);

        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.value() == 404, res -> Mono.error(new UserNotFoundException("User does not exist")))
                .bodyToFlux(LookupResponse.class)
                .filter(info -> !info.isFork())
                .collectList()
                .block();
    }

    private void insertBranchesInfo(List<LookupResponse> lookupResponse) {
        for (LookupResponse response : lookupResponse) {
            String url = "https://api.github.com/repos/%s/%s/branches"
                    .formatted(response.getOwner().getLogin(), response.getName());

            List<BranchResponse> branches = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(BranchResponse.class)
                    .collectList().block();

            response.setBranches(branches);
        }
    }

}
