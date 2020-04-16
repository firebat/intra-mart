package jp.co.intra_mart.foundation.logic.data.basic;

import jp.co.intra_mart.foundation.logic.data.PropertyDefinition;
import jp.co.intra_mart.foundation.logic.exception.IllegalDataAccessException;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapTypeDefinition extends BasicTypeDefinition<Map> {

    @Override
    public String getId() {
        return "map";
    }

    @Override
    public Class<Map> getType() {
        return Map.class;
    }

    @Override
    public PropertyDefinition getPropertyByName(String name) {
        return new MapEntryPropertyDefinition(BasicTypeDefinitions.ANY.getId(), name);
    }

    @Override
    public Map newInstance() throws IllegalDataAccessException {
        return new LinkedHashMap();
    }

    public static class MapEntryPropertyDefinition implements PropertyDefinition {

        @Getter
        private String typeId;
        @Getter
        private String name;

        public MapEntryPropertyDefinition(String typeId, String name) {
            this.typeId = typeId;
            this.name = name;
        }

        @Override
        public Object getValue(Object parent) throws IllegalDataAccessException {
            if (!(parent instanceof Map)) {
                throw new IllegalDataAccessException(parent + " cannot be cast to Map.");
            }
            Map<String, Object> map = (Map<String, Object>) parent;
            return map.get(name);
        }

        @Override
        public void setValue(Object parent, Object value) throws IllegalDataAccessException {
            if (!(parent instanceof Map)) {
                throw new IllegalDataAccessException(parent + " cannot be cast to Map.");
            }
            Map<String, Object> map = (Map<String, Object>) parent;
            map.put(name, value);
        }


        @Override
        public boolean isBasicType() {
            return true;
        }

        @Override
        public boolean isListable() {
            return false;
        }

        @Override
        public boolean isRequired() {
            return false;
        }

        @Override
        public String getDescription() {
            return null;
        }
    }
}
