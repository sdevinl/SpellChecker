import org.junit.*;
import static org.junit.Assert.*;


public class HashTableTester {
    private  HashTable hash = new HashTable(5);
    //private  HashTable hash1 = new HashTable(1, "test.txt");

    @Before
    public void setUp() {
        hash.insert("dog");
        hash.insert("forat");

    }

    @Test
    public void insertTest() {
        assertEquals(true, hash.insert("Orange"));
        assertEquals(true, hash.lookup("Orange"));

        assertEquals(true, hash.insert("Red"));
        assertEquals(true, hash.lookup("Red"));

        assertEquals(false, hash.insert("Red"));

        try {
            assertEquals(true, hash.insert(null));
            fail();
        } catch (NullPointerException e) {
        }


    }

    @Test
    public void deleteTest() {
        assertTrue(hash.delete("dog"));
        assertFalse(hash.lookup("dog"));

        assertTrue(hash.delete("forat"));
        assertFalse(hash.lookup("forat"));

        try {
         hash.delete(null);
         fail();
        } catch (NullPointerException e) {
            //pass
        }
    }

    @Test
    public void lookUpTest() {
        assertTrue(hash.lookup("dog"));
        assertFalse(hash.lookup("moring"));

        try {
            hash.lookup(null);
            fail();
        } catch (NullPointerException e) {

        }
    }

    @Test
    public void getSizeTest() {
        assertEquals(2, hash.getSize());
        hash.insert("cCat");
        assertEquals(3, hash.getSize());
        hash.delete("cCat");
        assertEquals(2, hash.getSize());

    }


}