package co.com.bancolombia.model.exceptions.gateways;

import java.util.Set;

public interface ApplicationError {
    Set<String> getFields();
}
