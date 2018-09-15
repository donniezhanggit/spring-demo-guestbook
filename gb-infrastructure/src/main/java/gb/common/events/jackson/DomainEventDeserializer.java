package gb.common.events.jackson;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import gb.common.events.DomainEvent;


public class DomainEventDeserializer
extends StdDeserializer<DomainEvent> {
    private static final long serialVersionUID = 1L;

    private static final String TYPE_FIELD = "type";


    private final Map<String, Class<?>> classes;


    public
    DomainEventDeserializer(final Map<String, Class<?>> eventClasses) {
        super(DomainEvent.class);
        this.classes = eventClasses;
    }


    @Override
    public DomainEvent deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        final ObjectMapper mapper = (ObjectMapper) p.getCodec();
        final ObjectNode root = mapper.readTree(p);
        final String className = root.get(TYPE_FIELD).textValue();
        final Class<?> eventClass = classes.get(className);

        if(eventClass == null) {
            return null;
        }

        return (DomainEvent) mapper.readValue(root.toString(), eventClass);
    }
}
