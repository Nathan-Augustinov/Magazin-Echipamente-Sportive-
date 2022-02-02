package org.loose.fis.proiect.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.proiect.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.proiect.model.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class UserServiceTest
{

    public static final String MANAGER = "Manager";

    @Test
    @DisplayName("Database is initialized and there are no users")
    void testUserDatabaseIsInitializedAndNoUserIsPersisted()
    {
        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();

    }

    @Test
    @DisplayName("User is successfully persisted to database")
    void testUserIsAddedToDatabase() throws UsernameAlreadyExistsException
    {
        UserService.addUser(MANAGER,MANAGER,MANAGER,MANAGER,MANAGER,MANAGER);
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user = UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(MANAGER);
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(MANAGER,MANAGER));
        assertThat(user.getRole()).isEqualTo(MANAGER);
        assertThat(user.getFirstname()).isEqualTo(MANAGER);
        assertThat(user.getLastname()).isEqualTo(MANAGER);
        assertThat(user.getEmail()).isEqualTo(MANAGER);
    }

    @Test
    @DisplayName("User can not be added twice")
    void testUserCannotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addUser(MANAGER,MANAGER,MANAGER,MANAGER,MANAGER,MANAGER);
            UserService.addUser(MANAGER,MANAGER,MANAGER,MANAGER,MANAGER,MANAGER);
        });
    }

    @Test
    @DisplayName("Check user does not already exists in the database")
    void testCheckUserDoesNotAlreadyExists()
    {
        assertThrows(UsernameAlreadyExistsException.class, ()-> {
            UserService.addUser(MANAGER,MANAGER,MANAGER,MANAGER,MANAGER,MANAGER);
            UserService.checkUserDoesNotAlreadyExist(MANAGER);
        });
    }

    @Test
    @DisplayName("Test encode password method")
    void testEncodePassword() throws UsernameAlreadyExistsException {
        UserService.addUser(MANAGER,MANAGER,MANAGER,MANAGER,MANAGER,MANAGER);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(MANAGER,MANAGER));
    }

    @Test
    @DisplayName("Test if database is closed")
    void testDatabaseIsClosed()
    {
        UserService.CloseDatabase();
        assertThat(UserService.getDatabase().isClosed()).isEqualTo(true);
        UserService.initDatabase();
    }

    @BeforeEach
    void setUp() throws Exception
    {
        FileSystemService.APPLICATION_FOLDER=".test-registration-example";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();

    }

    @AfterEach
    void tearDown() {
        UserService.CloseDatabase();
    }

    @BeforeAll
    static void beforeAll() {

    }

    @AfterAll
    static void afterAll() {

    }
}