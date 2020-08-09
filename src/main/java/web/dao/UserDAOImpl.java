package web.dao;


import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    @PersistenceContext(unitName = "emf")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> allUsers() {
        return entityManager.createQuery("from " + User.class.getName()).getResultList();     //session.createQuery("from User").list();
    }

    public void add(User user) {
        System.out.println("ымы тут, добавляем user-а!!!");
        System.out.println("User.id = " + user.getId() + "User.name = " + user.getName() + "User.age = " + user.getAge());
        entityManager.persist(user);
    }

    public void delete(User user) {
        entityManager.remove(entityManager.find(User.class, user.getId()));
    }

    public void edit(User user) {
        entityManager.merge(user);
    }

    public User getById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByName(String username) {
        User user = null;
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.login=:username");
        query.setParameter("username", username);
        try {
            user = (User) query.getSingleResult();
        } catch (Exception e) {
            // Handle exception
        }
        return user;
    }
}


