package br.com.esucri.vacineja.vacinado;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VacinadoService {

    @PersistenceContext(unitName = "VacinadosPU")
    private EntityManager entityManager;

    public List<Vacinado> findAll() {
        return entityManager
                .createQuery("SELECT v FROM Vacinado v")
                .getResultList();
    }

    public Vacinado findById(Long id) {
        return entityManager.find(Vacinado.class, id);
    }

    public Vacinado add(Vacinado vacinado) {
        entityManager.persist(vacinado);
        return vacinado;
    }

    public Vacinado update(Vacinado vacinado) {
        return entityManager.merge(vacinado);
    }

    public void remove(Long id) {
        entityManager.remove(findById(id));
    }

}
