-- DROP TABLE IF EXISTS TESTQ;
CREATE TABLE RSS_MESSAGE
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    link        VARCHAR NOT NULL,
--     author      VARCHAR(255) NOT NULL,
    guid        VARCHAR NOT NULL,
    pub_date    TIMESTAMP WITH TIME ZONE,
    primary key (id)
);