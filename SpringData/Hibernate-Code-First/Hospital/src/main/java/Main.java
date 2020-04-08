import entities.Diagnose;
import entities.Medicament;
import entities.Patient;
import entities.Visitation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.HashSet;


public class Main {
    public static void main(String[] args) {

        Medicament medicament = new Medicament();
        medicament.setName("Medicament 1");
        medicament.setPatients(new HashSet<>());

        Medicament medicament2 = new Medicament();
        medicament2.setName("Medicament 2");
        medicament2.setPatients(new HashSet<>());

        Diagnose diagnose = new Diagnose();
        diagnose.setName("Diagnose 1");
        diagnose.setComments("Diagnose 1 comment");
        diagnose.setPatients(new HashSet<>());

        Diagnose diagnose2 = new Diagnose();
        diagnose2.setName("Diagnose 2");
        diagnose2.setComments("Diagnose 2 comment");
        diagnose2.setPatients(new HashSet<>());

        Visitation visitation = new Visitation();
        visitation.setVisitationDate(new Date());
        visitation.setComments("Visitation 1 comment");

        Visitation visitation2 = new Visitation();
        visitation2.setVisitationDate(new Date());
        visitation2.setComments("Visitation 2 comment");

        Patient patient = new Patient();
        patient.setFirstName("Ivan");
        patient.setLastName("Ivanov");
        patient.setBirthDate(new Date());
        patient.setAddress("Sofia, Tintyava");
        patient.setHasInsurance(true);
        patient.setEmail("ivan_ivanov@gmail.com");
        patient.setDiagnoses(new HashSet<>() {{
            add(diagnose);
            add(diagnose2);
        }});
        patient.setMedicaments(new HashSet<>() {{
            add(medicament);
            add(medicament2);
        }});
        patient.setVisitations(new HashSet<>() {{
            add(visitation);
            add(visitation2);
        }});

        Patient patient2 = new Patient();
        patient2.setFirstName("Ivan");
        patient2.setLastName("Ionkov");
        patient2.setBirthDate(new Date());
        patient2.setAddress("Sofia, Strelbishte");
        patient2.setHasInsurance(false);
        patient2.setEmail("ionkov@mail.bg");
        patient2.setDiagnoses(new HashSet<>() {{
            add(diagnose);
            add(diagnose2);
        }});
        patient2.setMedicaments(new HashSet<>() {{
            add(medicament);
            add(medicament2);
        }});

        diagnose.getPatients().add(patient);
        diagnose.getPatients().add(patient2);
        diagnose2.getPatients().add(patient);
        diagnose2.getPatients().add(patient2);

        medicament.getPatients().add(patient);
        medicament.getPatients().add(patient2);
        medicament2.getPatients().add(patient);
        medicament2.getPatients().add(patient2);

        visitation.setPatient(patient);
        visitation2.setPatient(patient);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hospitaldb");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();


        try {
            transaction.begin();

            manager.persist(medicament);
            manager.persist(medicament2);

            manager.persist(diagnose);
            manager.persist(diagnose2);

            manager.persist(visitation);
            manager.persist(visitation2);

            manager.persist(patient);
            manager.persist(patient2);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        manager.close();
        factory.close();
    }
}
