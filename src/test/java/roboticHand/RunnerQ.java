package roboticHand;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import roboticHand.Model.Question;
import roboticHand.Model.User;
import roboticHand.Tools.Encryptor;

import java.util.List;
import java.util.Random;

public class RunnerQ {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        //Opens session for operations on database
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (int i = 0; i < 100000; i++) {
            try {
                session.save(new Question("Dianaa", "Test question", null, "N"));
            } catch (Exception e) {
                System.out.println(e.toString());
                session.close();
            }
        }
        session.getTransaction().commit();
        session.close();
    }
}
