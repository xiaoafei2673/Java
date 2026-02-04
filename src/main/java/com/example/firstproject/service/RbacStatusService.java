package com.example.firstproject.service;

public interface RbacStatusService {
    void setRoleStatus(String roleCode, int status);
    void setPermissionStatus(String permCode, int status);
}
