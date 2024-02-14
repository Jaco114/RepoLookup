package org.jaco114.repolookup.service;

import org.jaco114.repolookup.dto.LookupRequest;
import org.jaco114.repolookup.dto.LookupResponse;

import java.util.List;

public interface LookupService {
    List<LookupResponse> lookupUser(LookupRequest lookupRequest);
}
