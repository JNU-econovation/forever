package com.example.forever.application.auth;

public record LoginResult(
        String name,
        String major, 
        String school,
        String email
) {
    
    public static LoginResult forExistingMember(String name, String major, String school) {
        return new LoginResult(name, major, school, null);
    }
    
    public static LoginResult forSignupRequired(String email) {
        return new LoginResult(null, null, null, email);
    }
}
