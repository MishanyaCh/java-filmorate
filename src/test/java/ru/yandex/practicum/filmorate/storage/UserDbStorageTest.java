package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.database.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private UserStorage userStorage;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void beforeEach() {
        userStorage = new UserDbStorage(jdbcTemplate);
        user1 = new User("chizh909@mail.ru", "DSS",
                "Михаил", LocalDate.of(1995,12,6));
        user2 = new User("kogotonet@mail.ru", "Master",
                "Евгений", LocalDate.of(1985,3,23));
        user3 = new User("pdndr@gmail.com", "Vesker",
                "Егор", LocalDate.of(1993,8,10));
    }

    @Test
    public void insertNewUser() {
        User createdUser1 = userStorage.createUser(user1);
        User createdUser2 = userStorage.createUser(user2);

        assertThat(createdUser1)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1)
                .usingRecursiveComparison().isEqualTo(user1);

        assertThat(createdUser2)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id",2)
                .usingRecursiveComparison().isEqualTo(user2);
    }

    @Test
    public void updateExistingUser() {
        userStorage.createUser(user1);
        User userWithChangedLogin = new User(1,"chizh909@mail.ru", "Tanchen",
                "Михаил", LocalDate.of(1995,12, 6));
        User updatedUser1 = userStorage.updateUser(userWithChangedLogin);

        assertThat(updatedUser1)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(userWithChangedLogin);
    }

    @Test
    public void getAllUsers() {
        userStorage.createUser(user1);
        userStorage.createUser(user2);
        List<User> users = userStorage.getUsers();

        Assertions.assertNotNull(users);
        Assertions.assertEquals(2, users.size());
    }

    @Test
    public void returnEmptyListWhenUsersNotCreated() {
        List<User> users = userStorage.getUsers();

        Assertions.assertNotNull(users);
        Assertions.assertTrue(users.isEmpty());
    }

    @Test
    public void getUserById() {
        User createdUser1 = userStorage.createUser(user1);
        User user = userStorage.getUser(1);

        assertThat(user)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(createdUser1);
    }

    @Test
    public void returnNullWhenNotFindUserById() {
        userStorage.createUser(user1);
        User user = userStorage.getUser(88);

        Assertions.assertNull(user);
    }

    @Test
    public void addNewFriendForUser() {
        User user = userStorage.createUser(user1);
        int userId = user.getId();
        User friend = userStorage.createUser(user2);
        int friendId = friend.getId();

        List<User> friendsListBeforeAddNewFriend = userStorage.getUserFriends(userId);
        int sizeBeforeAdd = friendsListBeforeAddNewFriend.size();

        userStorage.addFriend(userId, friendId);
        List<User> friendsListAfterAddNewFriend = userStorage.getUserFriends(userId);
        int sizeAfterAdd = friendsListAfterAddNewFriend.size();

        Assertions.assertEquals(1, (sizeAfterAdd - sizeBeforeAdd));
    }

    @Test
    public void deleteFriendFromUser() {
        User user = userStorage.createUser(user1);
        int userId = user.getId();
        User friend = userStorage.createUser(user2);
        int friendId = friend.getId();
        User otherFriend = userStorage.createUser(user3);
        int otherFriendId = otherFriend.getId();

        userStorage.addFriend(userId, friendId);
        userStorage.addFriend(userId, otherFriendId);
        List<User> friendsListBeforeDeleteFriend = userStorage.getUserFriends(userId);
        int sizeBeforeDeleting = friendsListBeforeDeleteFriend.size();

        userStorage.deleteFriend(userId, friendId);
        List<User> friendsListAfterDeleteFriend = userStorage.getUserFriends(userId);
        int sizeAfterDeleting = friendsListAfterDeleteFriend.size();

        Assertions.assertEquals((sizeBeforeDeleting - sizeAfterDeleting), 1);
    }


    @Test
    public void getAllUserFriends() {
        User user = userStorage.createUser(user1);
        int userId = user.getId();
        User createdUser2 = userStorage.createUser(user2);
        int idFriend1 = createdUser2.getId();
        User createdUser3 = userStorage.createUser(user3);
        int idFriend2 = createdUser3.getId();

        userStorage.addFriend(userId, idFriend1);
        userStorage.addFriend(userId, idFriend2);

        List<User> friendsList = userStorage.getUserFriends(userId);
        Assertions.assertNotNull(friendsList);
        Assertions.assertEquals(2, friendsList.size());
    }

    @Test
    public void returnEmptyFriendsListWhenUserHaveNotFriends() {
        User user = userStorage.createUser(user1);
        int userId = user.getId();
        List<User> friendsList = userStorage.getUserFriends(userId);

        Assertions.assertNotNull(friendsList);
        Assertions.assertTrue(friendsList.isEmpty());
    }

    @Test
    public void getCommonFriendsForUser1AndUser2() {
        User userFirst = userStorage.createUser(user1);
        int userFirstId = userFirst.getId();
        User userSecond = userStorage.createUser(user2);
        int userSecondId = userSecond.getId();
        User commonFriend = userStorage.createUser(user3);
        int commonFriendId = commonFriend.getId();

        userStorage.addFriend(userFirstId, commonFriendId);
        userStorage.addFriend(userSecondId, commonFriendId);
        List<User> commonFriendsList = userStorage.getCommonFriendsWithOtherUser(userFirstId, userSecondId);

        Assertions.assertNotNull(commonFriendsList);
        Assertions.assertEquals(1, commonFriendsList.size());
    }

    @Test
    public void returnEmptyCommonFriendsListWhenUserHaveNotCommonFriends() {
        User userFirst = userStorage.createUser(user1);
        int userFirstId = userFirst.getId();
        User userSecond = userStorage.createUser(user2);
        int userSecondId = userSecond.getId();
        User friend = userStorage.createUser(user3);
        int friendId = friend.getId();

        userStorage.addFriend(userFirstId, friendId);
        List<User> commonFriendsList = userStorage.getCommonFriendsWithOtherUser(userFirstId, userSecondId);

        Assertions.assertNotNull(commonFriendsList);
        Assertions.assertTrue(commonFriendsList.isEmpty());
    }
}
