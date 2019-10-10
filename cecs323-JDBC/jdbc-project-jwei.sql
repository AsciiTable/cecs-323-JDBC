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
    /*GroupName: The name used to uniquely identify a group of authors*/
    GroupName varchar(30) NOT NULL,
    /*HeadWriter: The head author of the book, the one who is explicitly named first*/
    HeadWriter varchar(50) NOT NULL,
    /*YearFormed: The year in which the group was formed*/
    YearFormed int NOT NULL,
    /*Subject: The genere in which the the book belongs to*/
    Subject varchar(30) NOT NULL,
    CONSTRAINT writinggroups_pk PRIMARY KEY (GroupName)
);

/* Creates Publishers Table 
    Holds the name, address, phone number, and email of the publisher.*/
CREATE TABLE Publishers(
    /*PublisherName: The name used to uniquely identify a publishing company*/
    PublisherName varchar(50) NOT NULL,
    /*PublisherAddress: The address at which the publishing company is located*/
    PublisherAddress varchar(70) NOT NULL,
    /*PublisherPhone: The phone number that the publisher can be contacted at*/
    PublisherPhone varchar(11) NOT NULL,
    /*PublisherEmail: The email that the publisher can be contacted at */
    PublisherEmail varchar(50) NOT NULL,
    CONSTRAINT publishers_pk PRIMARY KEY (PublisherName)
);

/* Creates Books Table 
    Holds the title of the book, the year it was published, the number of pages it has, the author group name, and the publisher name.*/
CREATE TABLE Books(
     /*GroupName: The name used to uniquely identify the group of authors that wrote this book*/
    GroupName varchar(30) NOT NULL,
    /*PublisherName: The name used to uniquely identify the publishing company that published this book*/
    PublisherName varchar(50) NOT NULL,
    /*BookTitle: The name that the book is refered to by*/
    BookTitle varchar(50) NOT NULL,
    /*YearPublished: The year in which the authors and publishers published the book*/
    YearPublished int NOT NULL,
    /*NumberPages: The number of pages that the book holds in total*/
    NumberPages int NOT NULL,

    CONSTRAINT books_fk_1 FOREIGN KEY (GroupName) REFERENCES WritingGroups (GroupName),
    CONSTRAINT books_fk_2 FOREIGN KEY (PublisherName) REFERENCES Publishers (PublisherName),
    CONSTRAINT books_pk PRIMARY KEY (GroupName, BookTitle) ,
    CONSTRAINT books_ck_1 UNIQUE (BookTitle,PublisherName)
);

INSERT INTO WritingGroups(groupname,headwriter,yearformed,subject) values
('HelloWorld','Dennis Ritchie', 1941, 'Technology'),
('Jade Studios','Dakota McNally',2018,'Technology'),
('AJR','Ryan Met',2005,'Music'),
('AsciiTable','Jessica Wei', 2000, 'Biography'),
('Chicken Coup','Hanson Nguyen',2019,'Administration'),
('Just One Cookbook', 'Namiko Chen', 2018, 'Cooking'),
('Tasty', 'Ze Frank', 2006, 'Cooking'),
('Shakespeare Fan Club', 'William Shakespeare', 1564, 'Classic'),
('BESST', 'Ariel Nam', 2018, 'Cohort');

