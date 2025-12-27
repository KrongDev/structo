package com.archbuilder.user.application.command;

public record UpdateUserCommand(
    String userId,
    String name
) {}