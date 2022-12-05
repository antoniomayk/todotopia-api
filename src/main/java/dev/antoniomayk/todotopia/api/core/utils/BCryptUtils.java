package dev.antoniomayk.todotopia.api.core.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

@UtilityClass
public class BCryptUtils {

    public Boolean verifyPassword(final @NotNull String plainTextPassword, final @NotNull String encryptedPassword) {
        return BCrypt.verifyer()
                .verify(plainTextPassword.getBytes(StandardCharsets.UTF_8), encryptedPassword.getBytes(StandardCharsets.UTF_8))
                .verified;
    }

}
