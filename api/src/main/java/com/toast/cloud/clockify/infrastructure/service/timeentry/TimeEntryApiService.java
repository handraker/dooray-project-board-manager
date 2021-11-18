package com.toast.cloud.clockify.infrastructure.service.timeentry;

import com.toast.cloud.clockify.infrastructure.service.timeentry.vo.Project;
import com.toast.cloud.clockify.infrastructure.service.timeentry.vo.StartTimerRequest;
import com.toast.cloud.clockify.infrastructure.service.timeentry.vo.StopTimerRequest;
import com.toast.cloud.clockify.infrastructure.service.timeentry.vo.TimeEntry;
import com.toast.cloud.common.client.DpbmExchangeStrategies;
import com.toast.cloud.common.client.DpbmWebClient;
import com.toast.cloud.common.client.filter.SimpleLoggingFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class TimeEntryApiService {

    private final WebClient webClient;
    private final static String WORKSPACE_ID = "5d5ccefa79b77323aac2cf49";
    private final static String USER_ID = "5c369418b0798731dc47c8d2";
    private final static String API_KEY = "YmYyM2Q5MjgtZjIwZS00MDBhLTk3YTctZjJjMDE3NjJiNjBh";

    public TimeEntryApiService() {
        this.webClient = DpbmWebClient.builder()
            .baseUrl("https://api.clockify.me")
            .filter(new SimpleLoggingFilter())
            .exchangeStrategies(DpbmExchangeStrategies.exchangeStrategies())
            .build();
    }

    public List<TimeEntry> getTimeEntryList(boolean inProgress) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/v1/workspaces/{workspaceId}/user/{userId}/time-entries")
                .queryParam("in-progress", inProgress)
                .build(WORKSPACE_ID, USER_ID))
            .header("X-Api-Key", API_KEY)
            .exchange()
            .flatMap(clientResponse -> clientResponse.bodyToMono(TimeEntry[].class))
            .map(List::of)
            .onErrorReturn(List.of())
            .block();
    }

    public void startTimer(ZonedDateTime start, String description, String projectId) {
        webClient.post()
            .uri(uriBuilder -> uriBuilder.path("/api/v1/workspaces/{workspaceId}/user/{userId}/time-entries")
                .build(WORKSPACE_ID, USER_ID))
            .header("X-Api-Key", API_KEY)
            .bodyValue(new StartTimerRequest(start, description, projectId))
            .exchange()
            .flatMap(clientResponse -> clientResponse.bodyToMono(Void.class))
            .block();
    }

    public void stopTimer() {
        webClient.patch()
            .uri(uriBuilder -> uriBuilder.path("/api/v1/workspaces/{workspaceId}/user/{userId}/time-entries")
                .build(WORKSPACE_ID, USER_ID))
            .header("X-Api-Key", API_KEY)
            .bodyValue(new StopTimerRequest(ZonedDateTime.now(ZoneOffset.UTC).withNano(0)))
            .exchange()
            .flatMap(clientResponse -> clientResponse.bodyToMono(Void.class))
            .block();
    }

    public List<Project> getProjects() {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/v1/workspaces/{workspaceId}/projects")
                .build(WORKSPACE_ID))
            .header("X-Api-Key", API_KEY)
            .exchange()
            .flatMap(clientResponse -> clientResponse.bodyToMono(Project[].class))
            .map(List::of)
            .onErrorReturn(List.of())
            .block();
    }

}
