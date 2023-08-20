package panomete.project.cometopimabe.security.payload.response;

import java.util.List;

public record PageAuthDto(
        int totalPages,
        long totalElements,
        int size,
        int page,
        List<AuthDto> content
) {
}
