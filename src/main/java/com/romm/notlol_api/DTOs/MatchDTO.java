package com.romm.notlol_api.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class MatchDTO {
    @JsonInclude
    public InfoDTO info;
    @JsonIgnore
    public String metadata;
}
