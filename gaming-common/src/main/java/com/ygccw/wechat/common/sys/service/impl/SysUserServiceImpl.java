package com.ygccw.wechat.common.sys.service.impl;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.ygccw.wechat.common.sys.dao.SysUserDao;
import com.ygccw.wechat.common.sys.entity.SysUser;
import com.ygccw.wechat.common.sys.enums.UserType;
import com.ygccw.wechat.common.sys.exception.UserAuthenticationException;
import com.ygccw.wechat.common.sys.service.SysUserService;
import com.ygccw.wechat.common.utils.UUIDUtil;
import core.framework.crypto.HMAC;
import core.framework.util.EncodingUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author nick.guo
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Inject
    SysUserDao sysUserDao;

    public SysUser authentication(SysUser sysUser) {
        SysUser oUser = findByUsername(sysUser.getUsername());
        if (null == oUser) {
            throw new UserAuthenticationException("user no exist");
        }
        if (!oUser.getPassword().equals(hashPassword(sysUser.getPassword(), oUser.getSalt()))) {
            throw new UserAuthenticationException("password error");
        }
        return oUser;
    }

    @Override
    public void save(SysUser sysUser) {
        SysUser origUser = findByUsername(sysUser.getUsername());
        Preconditions.checkState(null == origUser, " [username=" + sysUser.getUsername() + "] is exist");
        Date date = new Date();
        sysUser.setCreateTime(date);
        sysUser.setUpdateTime(date);
        sysUser.setStatus(1);
        String salt = getSalt();
        sysUser.setPassword(hashPassword(sysUser.getPassword(), salt));
        sysUser.setSalt(salt);
        sysUser.setUuid(UUIDUtil.generate());
        sysUserDao.save(sysUser);
    }

    @Override
    public void update(SysUser sysUser) {
        sysUser.setUpdateTime(new Date());
        sysUserDao.update(sysUser);
    }

    @Override
    public void resetPassword(SysUser sysUser) {
        String salt = getSalt();
        sysUser.setPassword(hashPassword(sysUser.getPassword(), salt));
        sysUser.setSalt(salt);
        this.update(sysUser);
    }

    @Override
    public void delete(String uuid) {
        SysUser sysUser = findByUuid(uuid);
        sysUser.setStatus(0);
        this.update(sysUser);
    }

    @Override
    public SysUser findByUuid(String uuid) {
        return sysUserDao.findByUuid(uuid);
    }

    @Override
    public void modifyPassword(SysUser sysUser, String newPassword) {
        SysUser oUser = null;
        try {
            oUser = authentication(sysUser);
        } catch (UserAuthenticationException e) {
            throw new RuntimeException(e);
        }
        String salt = getSalt();
        oUser.setPassword(hashPassword(sysUser.getPassword(), salt));
        oUser.setSalt(salt);
        this.update(oUser);
    }

    @Override
    public List<SysUser> list(int offset, int fetchSize) {
        return sysUserDao.list(offset, fetchSize);
    }

    @Override
    public List<SysUser> list(UserType userType, int offset, int fetchSize) {
        return sysUserDao.list(userType, offset, fetchSize);
    }

    @Override
    public List<SysUser> findByRoleUuid(String roleUuid) {
        return sysUserDao.findByRoleUuid(roleUuid);
    }

    @Override
    public List<SysUser> findByUserType(UserType userType) {
        return sysUserDao.findByUserType(userType);
    }

    public SysUser findByUsername(String username) {
        return sysUserDao.findByUsername(username);
    }

    String hashPassword(String password, String salt) {
        HMAC hmac = new HMAC();
        hmac.setSecretKey(salt.getBytes(Charsets.UTF_8));
        byte[] bytes = hmac.digest(password);
        return EncodingUtils.base64(bytes);
    }

    String getSalt() {
        Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        return EncodingUtils.base64(salt);
    }

   /* public static void main(String[] args) {
        SysUserServiceImpl sysUserService = new SysUserServiceImpl();
        String salt = sysUserService.getSalt();
        System.out.println(salt);
        String admin = sysUserService.hashPassword("admin", salt);
        System.out.println(admin);
    }*/
}
