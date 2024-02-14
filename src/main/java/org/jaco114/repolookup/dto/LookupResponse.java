package org.jaco114.repolookup.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class LookupResponse {

    private String name;
    private Owner owner;

    @JsonIgnore
    private boolean fork;

    private List<BranchResponse> branches;

    @Data
    public class Owner {
        private String login;
    }
}
