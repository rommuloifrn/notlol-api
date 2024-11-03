package com.romm.notlol_api.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

public record PlayerDTO(String gameName, String tagLine, @Schema(hidden = true) String puuid) {
    
}
