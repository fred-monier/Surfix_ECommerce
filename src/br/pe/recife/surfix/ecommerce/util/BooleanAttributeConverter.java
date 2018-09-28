package br.pe.recife.surfix.ecommerce.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BooleanAttributeConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean value) {        
        return ((value == null || !value) ? 0 : 1);            
    }    

    @Override
    public Boolean convertToEntityAttribute(Integer value) {
        return (value == 1);        
    }

}
