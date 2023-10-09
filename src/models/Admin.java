package models;

import interfaces.Credential;

public record Admin(String username, String password) implements Credential {

}
