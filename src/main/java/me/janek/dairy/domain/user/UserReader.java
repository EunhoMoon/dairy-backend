package me.janek.dairy.domain.user;

public interface UserReader {

    User getUserByEmail(String email);

    boolean isUserExists(String email);

}
