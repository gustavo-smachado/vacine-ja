package br.com.esucri.vacineja.vacina;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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

    public Map<Long, Long> dashboard() {
        List<Long> vacinas = entityManager
                .createQuery("SELECT EXTRACT(MONTH FROM v.validade) AS MonthOfDate FROM Vacina v")
                .getResultList();
        
       Map<Long, Long> counted = vacinas.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
       
       return counted;
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
        validaFabricacao(vacina);
        validaValidade(vacina);
        entityManager.persist(vacina);
        return vacina;
    }

    public Vacina update(Vacina vacina) {
        Long id = vacina.getId();
        findById(id);        
        validaTipo(vacina);
        validaFabricacao(vacina);
        validaValidade(vacina);        
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
    
    private void validaFabricacao(Vacina vacina) {
        if (vacina.getFabricacao().isAfter(LocalDate.now())) {
            throw new BadRequestException("A data de fabricação da vacina é posterior a data atual");
        }
    }
    
    private void validaValidade(Vacina vacina) {
        if (vacina.getValidade().isBefore(LocalDate.now())) {
            throw new BadRequestException("A data de validade da vacina é anterior a data atual");
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
