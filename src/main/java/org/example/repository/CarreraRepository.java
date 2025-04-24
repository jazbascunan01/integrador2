package org.example.repository;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.factory.JPAUtil;
import org.example.modelo.Carrera;

import java.io.FileReader;

public class CarreraRepository {
    public void insertarDesdeCSV(String path) {
        EntityManager em = JPAUtil.getEntityManager();
        String root = "src\\main\\resources\\" + path;
        try (CSVReader reader = new CSVReader(new FileReader(root))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Carrera carrera = new Carrera();
                carrera.setNombre(linea[1]);
                carrera.setDuracion(Integer.parseInt(linea[2]));

                em.persist(carrera);

            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
