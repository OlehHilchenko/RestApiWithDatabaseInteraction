import com.olehhilchenko.model.Account;
import com.olehhilchenko.model.Developer;
import com.olehhilchenko.model.Skill;
import com.olehhilchenko.repository.DeveloperRepository;
import com.olehhilchenko.repository.hibernate.DeveloperDAOImpl;
import com.olehhilchenko.repository.hibernate.HibernateUtil;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;

public class TestRepo {

    private DeveloperRepository developerRepository = new DeveloperDAOImpl();
    private static boolean trig;

    static {
        trig = true;
    }
/*
    @Before
    public void setUp() throws IOException, SQLException, LiquibaseException {

        if (trig) {
            // Prepare the Hibernate configuration
            StandardServiceRegistry reg = new StandardServiceRegistryBuilder().configure().build();
            MetadataSources metaDataSrc = new MetadataSources(reg);

            // Get database connection
            Connection con = metaDataSrc.getServiceRegistry().getService(ConnectionProvider.class).getConnection();
            JdbcConnection jdbcCon = new JdbcConnection(con);

            // Initialize Liquibase and run the update
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcCon);
            Liquibase liquibase = new Liquibase("liquibase/dbInitialChangeLog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("test");

            // Create Hibernate SessionFactory
            // sf = metaDataSrc.addAnnotatedClass(Author.class).addAnnotatedClass(Book.class).buildMetadata().buildSessionFactory();
            trig = false;
        }
    }


    @After
    public void tearDown() {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("TRUNCATE TABLE developers;");
            query.executeUpdate();
            Query query1 = session.createSQLQuery("TRUNCATE TABLE skills;");
            query1.executeUpdate();
            Query query2 = session.createSQLQuery("TRUNCATE TABLE accounts;");
            query2.executeUpdate();
            Query query3 = session.createSQLQuery("TRUNCATE TABLE developer_account;");
            query3.executeUpdate();
            Query query4 = session.createSQLQuery("TRUNCATE TABLE developer_skill;");
            query4.executeUpdate();
            session.getTransaction().commit();
        }
    }
*/
    @Test
    public void testInsertAndSelectMethods() {

        Account account = new Account(0, "testStatus");
        Set<Skill> skills = new HashSet<>();
        skills.add(new Skill(0, "testSkill1"));
        skills.add(new Skill(0, "testSkill2"));
        Developer newDeveloper = new Developer(0, "testFirstName", "testLastName", account, skills);

        long newDevID = developerRepository.insert(newDeveloper);
        System.out.println(newDevID);
        Developer getDeveloper = developerRepository.select(newDevID);

        assertNotNull(getDeveloper);
        assertTrue(newDeveloper.getId() == getDeveloper.getId() &&
                newDeveloper.getFirstName().equals(getDeveloper.getFirstName()) &&
                newDeveloper.getLastName().equals(getDeveloper.getLastName()));
    }

    @Test
    public void testUpdateAndDeleteMethods() {

        Account account = new Account(0, "testStatus");
        Set<Skill> skills = new HashSet<>();
        skills.add(new Skill(0, "testSkill1"));
        skills.add(new Skill(0, "testSkill2"));
        Developer newDeveloper = new Developer(0, "testFirstName", "testLastName", account, skills);

        final long newDevID = developerRepository.insert(newDeveloper);
        System.out.println(newDevID);

        System.out.println(developerRepository.getAll());
        newDeveloper.setFirstName("valueChanged");
        newDeveloper.setLastName("valueChanged");
        developerRepository.update(newDeveloper);

        System.out.println(developerRepository.getAll());
        Developer getDeveloper = developerRepository.select(newDevID);
        assertTrue("valueChanged".equals(getDeveloper.getFirstName()) &&
                "valueChanged".equals(getDeveloper.getLastName()));

        developerRepository.delete(getDeveloper);
        assertThrows(ObjectNotFoundException.class, () -> {
            developerRepository.select(newDevID);
        });

    }

    @Test
    public void fillingDataBaseForSomeTest(){
        // You must use this test if you install postman and you want to see, how dos work DeveloperServlet.
        // This test is filling database use objects of class Developer.
        List<Developer> developerList = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            Account account = new Account(0, "testAccountData" + i);
            Set<Skill> skillSet = new HashSet<>();
            for (int j = 0; j < 5; j++){
                Skill skill = new Skill(0, "testName" + i + j);
                skillSet.add(skill);
            }
            Developer developer = new Developer();
            developer = new Developer(0, "testFirstName" + i, "testLastName" + i, account, skillSet);
            developerList.add(developer);
        }

        for (Developer d : developerList)
            developerRepository.insert(d);

    }

}
