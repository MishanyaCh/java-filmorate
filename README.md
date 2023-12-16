# Java-Filmorate

## Схема БД Filmorate
<picture>
    <img src="https://raw.githubusercontent.com/MishanyaCh/java-filmorate/a40926fdecd8024582961e2442509429ecc2e379/FilmorateDB%20schema.png">
</picture>

## Примеры основных SQL запросов к БД
1. Получение фильма по id
   ~~~
   SELECT *
   FROM films
   WHERE film_id = ХХ;
   ~~~ 
2. Получение жанров фильма с id = XX
   ~~~
   SELECT *
   FROM films_genre AS f_g 	   
   LEFT OUTER JOIN genre AS g ON f_g.genre_id = g.genre_id
   WHERE film_id = ХХ; 
   ~~~
3. Получение N самых популярных фильмов
   ~~~
   SELECT *
   FROM (SELECT film_id,
                COUNT(DISTINCT user_id) AS count_likes
         FROM likes
         GROUP BY film_id
         ORDER BY count_likes DESC
         LIMIT N) AS top_N_films
   INNER JOIN films AS f ON top_N_films.film_id = f.film_id;  
   ~~~
4. Получение пользователя по id
   ~~~
   SELECT *
   FROM users
   WHERE user_id = ХХ;
   ~~~
5. Получение списка всех друзей пользователя с id = XX
   ~~~
   SELECT *
   FROM users
   WHERE user_id IN (SELECT friend_id
                     FROM friends_list
                     WHERE user_id = XX and is_friend = TRUE);
   ~~~
6. Получение списка общих друзей между пользователями с id = XX и с id = YY
   ~~~
   SELECT f_table1.friend_id AS common_friends
   FROM (SELECT friend_id,
                user_id
         FROM friends_list
         WHERE user_id = XX and is_friend = TRUE) AS f_table1
   LEFT OUTER JOIN (SELECT friend_id,
                           user_id
                    FROM friends_list
                    WHERE user_id = YY and is_friend = TRUE) AS f_table2 ON f_table1.friend_id = f_table2.friend_id;
   ~~~
