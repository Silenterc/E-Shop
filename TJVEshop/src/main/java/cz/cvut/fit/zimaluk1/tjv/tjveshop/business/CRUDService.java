package cz.cvut.fit.zimaluk1.tjv.tjveshop.business;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.DomainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public abstract class CRUDService<D extends DomainEntity<K>,K> {
    protected final JpaRepository<D, K> repository;

    protected CRUDService(JpaRepository<D, K> repo) {
        this.repository = repo;
    }
    public D create(D newOne) throws Exception{
        if(repository.existsById(newOne.getId())){
            throw new Exception("Entity with " + newOne.getId() + " id exists");
        }
        return repository.save(newOne);
    }
    public Optional<D> readById(K id){
        return repository.findById(id);
    }
    public Collection<D> readAll() {
        return repository.findAll();
    }
    public D update(D upOne) throws Exception {
        if(repository.existsById(upOne.getId())) {
            return repository.save(upOne);
        } else{
            throw new Exception("Cannot update, " + upOne.toString() + " does not exist");
        }
    }
    public void deleteById(K id) {
        repository.deleteById(id);
    }





}
