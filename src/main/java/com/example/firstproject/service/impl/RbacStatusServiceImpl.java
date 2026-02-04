
package com.example.firstproject.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.firstproject.entity.Permission;
import com.example.firstproject.entity.Role;
import com.example.firstproject.mapper.PermissionMapper;
import com.example.firstproject.mapper.RoleMapper;
import com.example.firstproject.service.RbacStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RbacStatusServiceImpl implements RbacStatusService {

    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;
    @Override
    public void setRoleStatus(String roleCode, int status) {
        Role r = roleMapper.selectOne(new QueryWrapper<Role>().eq("code", roleCode));
        if (r == null) throw new IllegalArgumentException("角色不存在: " + roleCode);
        r.setStatus(status);
        roleMapper.updateById(r);
    }

    @Override
    public void setPermissionStatus(String permCode, int status) {
        Permission p = permissionMapper.selectOne(new QueryWrapper<Permission>().eq("code", permCode));
        if (p == null) throw new IllegalArgumentException("权限不存在: " + permCode);
        p.setStatus(status);
        permissionMapper.updateById(p);
    }
}

