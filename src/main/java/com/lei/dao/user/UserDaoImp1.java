package com.lei.dao.user;

import com.lei.dao.BaseDao;
import com.lei.pojo.Role;
import com.lei.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImp1 implements UserDao {
    //dao接口的实现类

    public User getLoginUser(Connection connection, String userCode) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if (connection != null) {
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};
            try {
                rs = BaseDao.execute(connection, sql, params, rs, pstm);
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserCode(rs.getString("userCode"));
                    user.setUserName(rs.getString("userName"));
                    user.setUserPassword(rs.getString("userPassword"));
                    user.setGender(rs.getInt("gender"));
                    user.setBirthday(rs.getDate("birthday"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    user.setUserRole(rs.getInt("userRole"));
                    user.setCreatedBy(rs.getInt("createdBy"));
                    user.setCreationDate(rs.getTimestamp("creationDate"));
                    user.setModifyBy(rs.getInt("modifyBy"));
                    user.setModifyDate(rs.getTimestamp("modifyDate"));
                }
                BaseDao.closeResource(null, pstm, rs);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public int updatePwd(Connection connection, int id, String password) {
        PreparedStatement pstm = null;
        //execute执行了几行的改动
        int execute = 0;
        if (connection != null) {
            String sql = "update smbms_user set userPassword = ? where id =?";
            Object params[] = {password, id};
            System.out.println("newpassword dao" + password);
            try {
                execute = BaseDao.execute(connection, sql, params, pstm);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BaseDao.closeResource(null, pstm, null);
        }

        return execute;
    }


    //根据用户名和角色查询总数
    @Override
    public int getUserCount(Connection connection, String username, int userrole) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;

        if (connection != null) {
            StringBuffer sql = new StringBuffer();

            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");
            ArrayList<Object> list = new ArrayList<Object>();

            if (username != null) {
                //and前面要空格
                sql.append(" and u.userName like ?");
                list.add("%" + username + "%");
            }
            if (userrole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userrole);
            }

            //list转化为数组
            Object[] objects = list.toArray();

            System.out.println("getUserCount sql:" + sql.toString());

            try {
                rs = BaseDao.execute(connection, sql.toString(), objects, rs, pstm);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (rs.next()) {
                count = rs.getInt("count");
                //从结果集中获取count
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return count;
    }

    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize)
            throws Exception {
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if (userName != null) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, sql.toString(), params, rs, pstm);
            while (rs.next()) {
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString("userName"));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setPhone(rs.getString("phone"));
                _user.setUserRole(rs.getInt("userRole"));
                _user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return userList;
    }

//    //角色列表    (已经放进RoleDao里面了）
//    @Override
//    public List<Role> getUserList(Connection connectionn) throws Exception {
//        PreparedStatement pstm = null;
//        ResultSet rs = null;
//        List<Role> list = new ArrayList<>();
//        if (connectionn != null) {
//            String sql = "select * from smbms_role";
//            Object[] params = {};
//            rs = BaseDao.execute(connectionn, sql, params, rs, pstm);
//        }
//        while (rs.next()) {
//            Role role = new Role();
//
//            role.setId(rs.getInt("id"));
//            role.setRoleCode(rs.getString("roleCode"));
//            role.setRoleName(rs.getString("roleName"));
//
//            list.add(role);
//
//        }
//        BaseDao.closeResource(null, pstm, rs);
//
//        return list;
//    }
}
