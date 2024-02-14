package org.jaco114.repolookup.dto;

public record BranchResponse(
        String name,
        Commit commit
) {
    public record Commit(String sha) {
    }
}
