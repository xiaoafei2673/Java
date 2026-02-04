package com.example.firstproject.security;

public class CurrentUser {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private CurrentUser(){}

    public static void setUserId(Long userId){
        USER_ID.set(userId);
    }
    public static Long getUserId(){
        return USER_ID.get();
    }
    public static void clear(){
        USER_ID.remove();
    }
}
