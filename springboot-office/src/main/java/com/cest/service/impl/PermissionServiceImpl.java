package com.cest.service.impl;

import com.cest.db.dao.TbPermissionDao;
import com.cest.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private TbPermissionDao permissionDao;

    @Override
    public ArrayList<HashMap> searchAllPermission() {
        ArrayList<HashMap> list = permissionDao.searchAllPermission();
        return list;
    }
}
