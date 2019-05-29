package roboticHand;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;
import roboticHand.Model.User;
import roboticHand.Tools.Encryptor;

import java.util.List;
import java.util.Random;

public class Runner {

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
                String name = "";
                do{
                    name = wordGenerator();
                }while (!read(session, name));


                session.save(new User(name, Encryptor.hashPassword(""), "", "",
                        'V', "SSS", Encryptor.hashPassword("")));
            } catch (Exception e) {
                System.out.println(e.toString());
                session.close();
            }
        }
        session.getTransaction().commit();
        session.close();
    }

    private static String wordGenerator(){
        int numberOfWords = 1;

        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();
        for(int i = 0; i < numberOfWords; i++)
        {
            char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
            for(int j = 0; j < word.length; j++)
            {
                word[j] = (char)('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }
        return randomStrings[0];
    }

    private static boolean read(Session session, String login){
        Query q = session.createQuery("SELECT _user FROM User _user WHERE login = '" + login + "'");
        List<User> users = q.list();
        if(users.size() == 0){
            return true;
        }else{
            return false;
        }
    }
}
