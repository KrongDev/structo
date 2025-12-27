package com.archbuilder.auth.application.command;

public record OAuthenticateCommand(
    String provider,
    String authorizationCode
) {}