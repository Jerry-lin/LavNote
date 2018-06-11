package cn.edu.hust.dao.user;
import cn.edu.hust.domain.User;
import cn.edu.hust.mapper.UserMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

@Repository
public class UserDaoImpl implements UserMapper{
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void insertUser(User user){
        //this.hbaseTemplate.put("User",user.getEmail(),"info","email",user.getEmail().getBytes());
        String sql="cn.edu.hust.mapper.UserMapper.insertUser";
        this.sqlSessionFactory.openSession().insert(sql,user);
    }

    @Override
    public User findUserByEmail(String email) {
        String sql="cn.edu.hust.mapper.UserMapper.findUserByEmail";
        return this.sqlSessionFactory.openSession().selectOne(sql,email);
    }

    @Override
    public void updateUserByEmail(String email) {
        String sql="cn.edu.hust.mapper.UserMapper.updateUserByEmail";
        this.sqlSessionFactory.openSession().update(sql,email);
    }

    @Override
    public void updatePasswdByEmail(User user) {
        String sql="cn.edu.hust.mapper.UserMapper.updatePasswdByEmail";
        this.sqlSessionFactory.openSession().update(sql,user);
    }

    @Override
    public User findUserByEmailAndPasswd(User user) {
        String sql="cn.edu.hust.mapper.UserMapper.findUserByEmailAndPasswd";
        return this.sqlSessionFactory.openSession().selectOne(sql,user);
    }

    @Override
    public void deleteByEmail(String email) {
        String sql="cn.edu.hust.mapper.UserMapper.deleteByEmail";
        this.sqlSessionFactory.openSession().delete(email);
    }

}
