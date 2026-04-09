package com.mms.mms_api.business.handler.role;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.dto.user.RoleDto;
import com.mms.mms_api.util.mapper.RoleMapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.role.RoleGetAllQuery;

@Component
public class RoleGetAllHandler extends RoleBaseHandler<RoleGetAllQuery, List<RoleDto>> {
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    public RoleGetAllHandler(RoleMapper roleMapper, RoleRepository roleRepository) {
        super();
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> execute(RoleGetAllQuery request) {
        return roleRepository.findAll().stream()
            .map(roleMapper::toDto)
            .toList();
    }
}
