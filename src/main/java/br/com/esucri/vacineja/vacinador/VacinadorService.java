package br.com.esucri.vacineja.vacinador;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

@Stateless
public class VacinadorService {

    @PersistenceContext(unitName = "VacinejaPU")
    private EntityManager entityManager;

    public List<Vacinador> findAll() {
        return entityManager
                .createQuery("SELECT v FROM Vacinador v")
                .getResultList();
    }

    public Vacinador findById(Long id) {
        Vacinador vacinador = entityManager.find(Vacinador.class, id);
        if(vacinador == null) {
            throw new NotFoundException("Vacinador com o id " + id + " não encontrado");
        }
        return vacinador;
    }

    public Vacinador add(Vacinador vacinador) {
        validaNome(vacinador);
        validaExistenciaVacinador(vacinador);

        entityManager.persist(vacinador);
        return vacinador;
    }

    public Vacinador update(Vacinador vacinador) {
        Long id = vacinador.getId();
        findById(id);        
        validaNome(vacinador);        
        return entityManager.merge(vacinador);
    }

    public void remove(Long id) {
        entityManager.remove(findById(id));
    }
    
    private void validaNome(Vacinador vacinador) {
        if (vacinador.getNome().length() < 3) {
            throw new BadRequestException("O nome do vacinador não pode conter menos que três caracteres");
        }
    }

    private void validaExistenciaVacinador(Vacinador vacinador) {
        List<Vacinador> resultList = entityManager
                .createQuery("SELECT v FROM Vacinador v WHERE LOWER(v.nome) = :nome", Vacinador.class)
                .setParameter("nome", vacinador.getNome().toLowerCase())
                .getResultList();
        
        if (resultList != null && !resultList.isEmpty()) {
            throw new BadRequestException("O vacinador já está cadastrado em nossa base");
        }
    }

}