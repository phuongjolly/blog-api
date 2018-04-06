TRUNCATE TABLE `user`;
INSERT INTO `user` (`email`, `password`) values('correctuser@email.com', 'correctpassword');
TRUNCATE TABLE `post`;
INSERT INTO `post` (title, description, `content`) values('My Girl', 'My sweetest girl ever', 'I love you so much moa moa');
TRUNCATE TABLE `comment`;
INSERT INTO `comment` (user_id, post_id, content) values(1, 1, 'Oh your girl so cute :)');
INSERT INTO `comment` (user_id, post_id, content) values(1, 1, 'Your family so cute babe, Wish you always like tha');
