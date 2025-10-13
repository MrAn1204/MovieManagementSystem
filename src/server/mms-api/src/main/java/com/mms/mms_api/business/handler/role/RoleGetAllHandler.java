package com.mms.mms_api.business.handler.role;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.util.mapper.RoleMapper;

import java.util.List;
import com.mms.mms_api.business.query.role.RoleGetAllQuery;
import com.mms.mms_api.dto.RoleDto;

public class RoleGetAllHandler extends RoleBaseHandler<RoleGetAllQuery, List<RoleDto>> {
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    public RoleGetAllHandler(RoleGetAllQuery request, RoleMapper roleMapper, RoleRepository roleRepository) {
        super(request);
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> execute() {
        return roleRepository.findAll().stream()
            .map(roleMapper::toDto)
            .toList();
    }
}
