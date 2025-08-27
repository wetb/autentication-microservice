package co.com.bancolombia.api.errors;

import co.com.bancolombia.model.Errors.gateways.ApplicationError;

import java.util.Set;

public class ErrorValidation  extends  RuntimeException implements ApplicationError {
    private Set<String> fields;
    public ErrorValidation(String message, Set<String> fields){
        super(message);
        this.fields = fields;
    }

    @Override
    public Set<String> getFields(){
        return fields;
    }
}
