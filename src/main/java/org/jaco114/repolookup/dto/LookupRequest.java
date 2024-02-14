package org.jaco114.repolookup.dto;

import jakarta.validation.constraints.NotNull;

public record LookupRequest(@NotNull(message = "'username' cannot be null") String username) {
}
