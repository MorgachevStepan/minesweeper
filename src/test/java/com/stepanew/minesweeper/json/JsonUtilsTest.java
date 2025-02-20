package com.stepanew.minesweeper.json;

import com.stepanew.minesweeper.exception.DeserializationException;
import com.stepanew.minesweeper.exception.SerializationException;
import com.stepanew.minesweeper.utils.json.JsonUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonUtilsTest {

    private static final TestObject TEST_OBJECT = new TestObject("test", 123);
    private static final String VALID_JSON = "{\"name\":\"test\",\"value\":123}";

    @Test
    void testSerialize() {
        String json = JsonUtils.serialize(TEST_OBJECT);

        assertThat(json).isEqualTo(VALID_JSON);
    }

    @Test
    void testSerializeThrowsException() {
        Object invalidObject = new Object() {
            @SuppressWarnings("unused")
            public String getInvalidData() {
                throw new RuntimeException("Invalid data");
            }
        };

        assertThrows(
                SerializationException.class,
                () -> JsonUtils.serialize(invalidObject)
        );
    }

    @Test
    void testDeserialize() {
        TestObject obj = JsonUtils.deserialize(VALID_JSON, TestObject.class);

        assertThat(obj.name()).isEqualTo("test");
        assertThat(obj.value()).isEqualTo(123);
    }

    @Test
    void testDeserializeThrowsException() {
        String invalidJson = "invalid json";

        assertThrows(
                DeserializationException.class,
                () -> JsonUtils.deserialize(invalidJson, TestObject.class)
        );
    }

    private record TestObject(
            String name,
            int value
    ) {
    }
}