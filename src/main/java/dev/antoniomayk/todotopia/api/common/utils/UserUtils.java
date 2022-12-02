package dev.antoniomayk.todotopia.api.common.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.antoniomayk.todotopia.api.common.entity.User;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class UserUtils {

    public User encryptUserPassword(@NotNull User user) {
        final var hash = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        return user.withPassword(hash);
    }

}
