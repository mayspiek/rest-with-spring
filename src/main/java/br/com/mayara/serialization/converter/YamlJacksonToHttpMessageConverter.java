package br.com.mayara.serialization.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import java.io.StringReader;

public class YamlJacksonToHttpMessageConverter extends AbstractJackson2HttpMessageConverter {
    public YamlJacksonToHttpMessageConverter() {
        // serializaçao de todos os campos exceto nulos
        super(new YAMLMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL),
                MediaType.parseMediaType("application/x-yaml")
        );
    }
}
