package com.baeldung.mongo;

import com.baeldung.mongo.crud.CrudClient;
import com.baeldung.mongo.crud.model.Event;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrudClientUnitTest {

    @BeforeAll
    public static void setup() {
        CrudClient.setup();
    }

    @Test
    @Order(1)
    public void whenInsertingEventsWithDate_thenCheckForDocument() {
        assertNotNull(CrudClient.insertEventsWithDate(CrudClient.pianoLessons));
        assertNotNull(CrudClient.insertEventsWithDate(CrudClient.soccerGame));
    }

    @Test
    @Order(2)
    public void whenReadingEventsByDate_thenCheckForReturnedDocument() {
        assertNotNull(CrudClient.readEventsByDate(CrudClient.dateQuery));
    }

    @Test
    @Order(3)
    public void whenReadingEventsByDateRange_thenCheckForReturnedDocument() {
        List<Event> events = CrudClient.readEventsByDateRange(CrudClient.from, CrudClient.to);
        assertNotNull(events);
        assertEquals(1, events.size());
    }

    @Test
    @Order(5)
    public void whenUpdatingEventsDateField_thenCheckUpdatedCount() {
        assertEquals(1, CrudClient.updateDateField());
    }

    @Test
    @Order(6)
    public void whenUpdatingManyEvents_thenCheckUpdatedCount() {
        long updates = CrudClient.updateManyEventsWithDateCriteria(CrudClient.updateManyFrom, CrudClient.updateManyTo);
        assertTrue(1 < updates);
    }

    @Test
    @Order(7)
    public void whenDeletingEventsWithDate_thenCheckDeletedCount() {
        assertEquals(2, CrudClient.deleteEventsByDate(CrudClient.deleteFrom, CrudClient.deleteTo));
    }

    @Test
    @Order(8)
    public void whenInsertingEventWithDateAndTimeZone_thenCheckForDocument() {
        assertNotNull(CrudClient.insertEventsWithDate(CrudClient.pianoLessonsTZ));
    }

    @Test
    @Order(9)
    public void whenReadingEventsWithDateAndTimeZone_thenCheckInsertedCount() {
        assertNotEquals(CrudClient.pianoLessonsTZ.dateTime, CrudClient.readEventsByDateWithTZ(CrudClient.dateQueryEventWithTZ));
    }

    @Test
    @Order(10)
    public void whenDeletingEventsWithDateAndTimeZone_thenCheckDeletedCount() {
        assertEquals(1, CrudClient.deleteEventsByDate(CrudClient.deleteFrom, CrudClient.deleteTo));
    }

    @AfterAll
    public static void close() throws IOException {
        CrudClient.dropDb();
        CrudClient.close();
    }

}
