package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.handler.role.RoleGetAllHandler;
import com.mms.mms_api.business.query.role.RoleGetAllQuery;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.dto.user.RoleDto;
import com.mms.mms_api.util.mapper.RoleMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    
    private final RoleMapper roleMapper;

    public List<RoleDto> handle(RoleGetAllQuery request) {
        RoleGetAllHandler handler = new RoleGetAllHandler(request, roleMapper, roleRepository);
        return handler.execute();
    }
}
