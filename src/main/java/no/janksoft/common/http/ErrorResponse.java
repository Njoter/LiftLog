package no.janksoft.common.http;

public record ErrorResponse(
        String code,
        String message,
        int status
) {}
