import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        try{
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            sessionFactory = new
                    Configuration().configure().buildSessionFactory();
            System.out.println("SessionFactory creada correctamente");
        }catch (Exception e){
            System.err.println("Error al crear sessionFactory");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    public static SessionFactory getSessionFactory(){return
            sessionFactory;}
    public static void shutdown(){
        if (sessionFactory != null){
            sessionFactory.close();
        }
    }
}
