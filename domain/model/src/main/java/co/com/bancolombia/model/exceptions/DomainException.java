package co.com.bancolombia.model.exceptions;

import co.com.bancolombia.model.exceptions.gateways.ApplicationError;

import java.util.Set;

public class DomainException extends  RuntimeException implements ApplicationError {
    private Set<String> fields;

    public DomainException(String message, Set<String> fields){
        super(message);
        this.fields= fields;
    }

    @Override
    public Set<String> getFields(){
        return fields;
    }
}
