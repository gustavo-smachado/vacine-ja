package br.com.esucri.vacineja.vacina;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VacinaService {

    @PersistenceContext(unitName = "VacinasPU")
    private EntityManager entityManager;

    public List<Vacina> findAll() {
        return entityManager
                .createQuery("SELECT v FROM Vacina v")
                .getResultList();
    }

    public Vacina findById(Long id) {
        return entityManager.find(Vacina.class, id);
    }

    public Vacina add(Vacina vacina) {
        entityManager.persist(vacina);
        return vacina;
    }

    public Vacina update(Vacina vacina) {
        return entityManager.merge(vacina);
    }

    public void remove(Long id) {
        entityManager.remove(findById(id));
    }

}
