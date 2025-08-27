package co.com.bancolombia.api.exceptions;

import co.com.bancolombia.model.exceptions.gateways.ApplicationError;

import java.util.Set;

public class ExceptionsValidation extends  RuntimeException implements ApplicationError {
    private Set<String> fields;
    public ExceptionsValidation(String message, Set<String> fields){
        super(message);
        this.fields = fields;
    }

    @Override
    public Set<String> getFields(){
        return fields;
    }
}
