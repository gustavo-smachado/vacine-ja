package br.com.esucri.vacineja.vacina;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

@Stateless
public class VacinaService {

    @PersistenceContext(unitName = "VacinejaPU")
    private EntityManager entityManager;

    public List<Vacina> findAll() {
        return entityManager
                .createQuery("SELECT v FROM Vacina v")
                .getResultList();
    }

    public Vacina findById(Long id) {
        Vacina vacina = entityManager.find(Vacina.class, id);
        if(vacina == null) {
            throw new NotFoundException("Vacina com o id " + id + " não encontrada");
        }
        return vacina;
    }

    public Vacina add(Vacina vacina) {
        validaTipo(vacina);
        validaExistenciaVacina(vacina);

        entityManager.persist(vacina);
        return vacina;
    }

    public Vacina update(Vacina vacina) {
        Long id = vacina.getId();
        findById(id);        
        validaTipo(vacina);        
        return entityManager.merge(vacina);
    }

    public void remove(Long id) {
        entityManager.remove(findById(id));
    }
    
    private void validaTipo(Vacina vacina) {
        if (vacina.getTipo().length() < 3) {
            throw new BadRequestException("O tipo da vacina não pode conter menos que três caracteres");
        }
    }

    private void validaExistenciaVacina(Vacina vacina) {
        List<Vacina> resultList = entityManager
                .createQuery("SELECT v FROM Vacina v WHERE LOWER(v.tipo) = :tipo", Vacina.class)
                .setParameter("tipo", vacina.getTipo().toLowerCase())
                .getResultList();
        
        if (resultList != null && !resultList.isEmpty()) {
            throw new BadRequestException("A vacina já está cadastrada em nossa base");
        }
    }

}
