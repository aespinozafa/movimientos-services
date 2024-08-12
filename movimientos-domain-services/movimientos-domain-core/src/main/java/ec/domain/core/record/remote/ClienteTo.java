package ec.domain.core.record.remote;

import lombok.Builder;

@Builder
public record ClienteTo(Integer id, PersonaTo personaId, String contrasena, Boolean estado) {}
