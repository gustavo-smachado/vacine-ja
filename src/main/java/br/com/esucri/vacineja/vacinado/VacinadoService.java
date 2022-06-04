package br.com.esucri.vacineja.vacinado;

import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Stateless
public class VacinadoService {

    @PersistenceContext(unitName = "VacinejaPU")
    private EntityManager entityManager;

    public List<Vacinado> findAll() {
        return entityManager
                .createQuery("SELECT v FROM Vacinado v")
                .getResultList();
    }

    public Vacinado findById(Long id) {
        Vacinado vacinado = entityManager.find(Vacinado.class, id);
        if(vacinado == null) {
            throw new NotFoundException("Vacinado com o id " + id + " não encontrado");
        }
        return vacinado;
    }

    public Vacinado add(Vacinado vacinado) {
        validaNome(vacinado);
        validaRg(vacinado);
        validaCpf(vacinado);
        validaNascimento(vacinado);
        validaExistenciaVacinado(vacinado);

        entityManager.persist(vacinado);
        return vacinado;
    }

    public Vacinado update(Vacinado vacinado) {
        Long id = vacinado.getId();
        findById(id);        
        validaNome(vacinado);
        validaRg(vacinado);
        validaCpf(vacinado);        
        validaNascimento(vacinado);
        return entityManager.merge(vacinado);
    }

    public void remove(Long id) {
        entityManager.remove(findById(id));
    }
    
    private void validaNome(Vacinado vacinado) {
        if (vacinado.getNome().length() < 3) {
            throw new BadRequestException("O nome do vacinado não pode conter menos que três caracteres");
        }
    }
    
    private void validaRg(Vacinado vacinado) {
        if (vacinado.getRg().length() < 9) {
            throw new BadRequestException("O RG do vacinado está incompleto");
        }
    }
    
    private void validaCpf(Vacinado vacinado) {
        if (vacinado.getCpf().length() < 14) {
            throw new BadRequestException("O CPF do vacinado está incompleto");
        }
    }
    
    private void validaNascimento(Vacinado vacinado) {
        if (vacinado.getNascimento().isAfter(LocalDate.now())) {
            throw new BadRequestException("A data de nascimento do vacinado é posterior a data atual");
        }
    }

    private void validaExistenciaVacinado(Vacinado vacinado) {
        List<Vacinado> resultList = entityManager
                .createQuery("SELECT v FROM Vacinado v WHERE LOWER(v.nome) = :nome", Vacinado.class)
                .setParameter("nome", vacinado.getNome().toLowerCase())
                .getResultList();
        
        if (resultList != null && !resultList.isEmpty()) {
            throw new BadRequestException("O vacinado já está cadastrado em nossa base");
        }
    }

}