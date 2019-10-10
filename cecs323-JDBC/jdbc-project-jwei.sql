/**
 * Author:  Jessica Wei
 * Created: Oct 9, 2019
 * Due: Oct 21, 2019
 */

/* Creates new database  */
--CREATE DATABASE IF NOT EXISTS ClientDriver;
/* Creates new user who has all permissions */
-- CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.jwei','password');
-- CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.sqlAuthorization','true');
-- /CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.fullAccessUsers', 'jwei');
-- GRANT ALL PRIVILEGES ON Books TO jwei;

/* Creates WritingGroups Table 
    Holds the name of the group, the head writer, the founding year, and the subject the group writes about.*/
CREATE TABLE WritingGroups(
    /*GroupName: */
    GroupName varchar(30) NOT NULL,
    /*HeadWriter: */
    HeadWriter varchar(50) NOT NULL,
    /*YearFormed: */
    YearFormed int NOT NULL,
    /*Subject: */
    Subject varchar(30) NOT NULL,
    CONSTRAINT writinggroups_pk PRIMARY KEY (GroupName)
);

/* Creates Publishers Table 
    Holds the name, address, phone number, and email of the publisher.*/
CREATE TABLE Publishers(
    /*PublisherName: */
    PublisherName varchar(50) NOT NULL,
    /*PublisherAddress: */
    PublisherAddress varchar(70) NOT NULL,
    /*PublisherPhone: */
    PublisherPhone varchar(11) NOT NULL,
    /*PublisherEmail: */
    PublisherEmail varchar(50) NOT NULL,
    CONSTRAINT publishers_pk PRIMARY KEY (PublisherName)
);

/* Creates Books Table 
    Holds the title of the book, the year it was published, the number of pages it has, the author group name, and the publisher name.*/
CREATE TABLE Books(
    /*GroupName: */
    GroupName varchar(30) NOT NULL,
    /*PublisherName: */
    PublisherName varchar(50) NOT NULL,
    /*BookTitle: */
    BookTitle varchar(50) NOT NULL,
    /*YearPublished: */
    YearPublished int NOT NULL,
    /*NumberPages: */
    NumberPages int NOT NULL,

    CONSTRAINT books_fk_1 FOREIGN KEY (GroupName) REFERENCES WritingGroups (GroupName),
    CONSTRAINT books_fk_2 FOREIGN KEY (PublisherName) REFERENCES Publishers (PublisherName),
    CONSTRAINT books_pk PRIMARY KEY (GroupName, BookTitle) ,
    CONSTRAINT books_ck_1 UNIQUE (BookTitle,PublisherName)
);

