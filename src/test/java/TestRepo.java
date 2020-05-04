import com.olehhilchenko.models.Account;
import com.olehhilchenko.models.Developer;
import com.olehhilchenko.models.Skill;
import com.olehhilchenko.repository.DeveloperRepository;
import com.olehhilchenko.repository.DeveloperRepositoryImplement;
import com.olehhilchenko.repository.hibernate.HibernateUtilities;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;

public class TestRepo {

    DeveloperRepository developerRepository = new DeveloperRepositoryImplement();

    @After
    public void tearDown() {
        try (Session session = HibernateUtilities.getSessionFactory().getCurrentSession()) {
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



    @Test
    public void testInsertAndSelectMethods(){

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
    public void testUpdateAndDeleteMethods(){

        Account account = new Account(0, "testStatus");
        Set<Skill> skills = new HashSet<>();
        skills.add(new Skill(0, "testSkill1"));
        skills.add(new Skill(0, "testSkill2"));
        Developer newDeveloper = new Developer(0, "testFirstName", "testLastName", account, skills);

        final long newDevID = developerRepository.insert(newDeveloper);
        System.out.println(newDevID);

        System.out.println(developerRepository.getDeveloperList());
        newDeveloper.setFirstName("valueChanged");
        newDeveloper.setLastName("valueChanged");
        developerRepository.update(newDeveloper);

        System.out.println(developerRepository.getDeveloperList());
        Developer getDeveloper = developerRepository.select(newDevID);
        assertTrue("valueChanged".equals(getDeveloper.getFirstName()) &&
                "valueChanged".equals(getDeveloper.getLastName()));

        developerRepository.delete(getDeveloper);
        assertThrows(ObjectNotFoundException.class, () -> {
            developerRepository.select(newDevID);
        });

    }
}
