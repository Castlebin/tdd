package tdd.args;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SchemaTest {

    @Test
    public void testSchemaBasicUsage() {
        String schemaText = "l:bool p:int d:str";
        Schema schema = new Schema(schemaText);
        assertEquals("bool", schema.getType("l"));
        assertEquals("int", schema.getType("p"));
        assertEquals("str", schema.getType("d"));
        assertEquals(null, schema.getType("x"));
    }

}