package cz.cvut.fit.zimaluk1.tjv.tjveshop.api;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.CRUDService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.DomainEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

public class CRUDController<D extends DomainEntity<K>, DT, K> {
    protected CRUDService<D, K> ser;
    protected Function<DT, D> toEntity;
    protected Function<D, DT> toDto;
    public CRUDController(CRUDService<D, K> ser, Function<DT, D> toEntity, Function<D, DT> toDto) {
        this.ser = ser;
        this.toEntity = toEntity;
        this.toDto = toDto;
    }
    @PostMapping
    public DT create(@RequestBody DT c){
        D newD = ser.create(toEntity.apply(c));
        return toDto.apply(newD);
    }
    @GetMapping
    public Collection<DT> getAll(){
        Collection<DT> toBeRet = new ArrayList<DT>();
        for(D c : ser.readAll()){
            toBeRet.add(toDto.apply(c));
        }
        return toBeRet;
    }
    @GetMapping("/{id}")
    public DT getById(@PathVariable K id){
        Optional<D> maybe = ser.readById(id);
        if(maybe.isPresent()){
            return toDto.apply(maybe.get());
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }
    }
    @PutMapping("/{id}")
    public DT updateById(@PathVariable K id, @RequestBody DT upOne){
        D conv = toEntity.apply(upOne);
        conv.setId(id);
        try{
            D up = ser.update(conv);
            return toDto.apply(up);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable K id){
        ser.deleteById(id);
    }







}
