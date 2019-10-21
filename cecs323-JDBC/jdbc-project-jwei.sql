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
    GroupName varchar(25) NOT NULL,
    /*HeadWriter: The head author of the book, the one who is explicitly named first*/
    HeadWriter varchar(25) NOT NULL,
    /*YearFormed: The year in which the group was formed*/
    YearFormed int NOT NULL,
    /*Subject: The genere in which the the book belongs to*/
    Subject varchar(25) NOT NULL,
    CONSTRAINT writinggroups_pk PRIMARY KEY (GroupName)
);

/* Creates Publishers Table 
    Holds the name, address, phone number, and email of the publisher.*/
CREATE TABLE Publishers(
    /*PublisherName: The name used to uniquely identify a publishing company*/
    PublisherName varchar(25) NOT NULL,
    /*PublisherAddress: The address at which the publishing company is located*/
    PublisherAddress varchar(25) NOT NULL,
    /*PublisherPhone: The phone number that the publisher can be contacted at*/
    PublisherPhone varchar(11) NOT NULL,
    /*PublisherEmail: The email that the publisher can be contacted at */
    PublisherEmail varchar(25) NOT NULL,
    CONSTRAINT publishers_pk PRIMARY KEY (PublisherName)
);

/* Creates Books Table 
    Holds the title of the book, the year it was published, the number of pages it has, the author group name, and the publisher name.*/
CREATE TABLE Books(
     /*GroupName: The name used to uniquely identify the group of authors that wrote this book*/
    GroupName varchar(25) NOT NULL,
    /*PublisherName: The name used to uniquely identify the publishing company that published this book*/
    PublisherName varchar(25) NOT NULL,
    /*BookTitle: The name that the book is refered to by*/
    BookTitle varchar(25) NOT NULL,
    /*YearPublished: The year in which the authors and publishers published the book*/
    YearPublished int NOT NULL,
    /*NumberPages: The number of pages that the book holds in total*/
    NumberPages int NOT NULL,

    CONSTRAINT books_fk_1 FOREIGN KEY (GroupName) REFERENCES WritingGroups (GroupName),
    CONSTRAINT books_fk_2 FOREIGN KEY (PublisherName) REFERENCES Publishers (PublisherName),
    CONSTRAINT books_pk PRIMARY KEY (GroupName, BookTitle) ,
    CONSTRAINT books_ck_1 UNIQUE (BookTitle,PublisherName)
);

/* Inserts 12 entries into the WritingGroups table */
INSERT INTO WritingGroups(groupname,headwriter,yearformed,subject) values
('HelloWorld','Dennis Ritchie', 1941, 'Technology'),
('Jade Studios','Dakota McNally',2018,'Recreation'),
('AJR','Ryan Met',2005,'Music'),
('AsciiTable','Jessica Wei', 2000, 'Biography'),
('Chicken Coup','Hanson Nguyen',2019,'Administration'),
('Just One Cookbook', 'Namiko Chen', 2018, 'Cooking'),
('Tasty', 'Ze Frank', 2006, 'Cooking'),
('Shakespeare Fan Club', 'William Shakespeare', 1564, 'Classic'),
('BESST', 'Ariel Nam', 2018, 'Cohort'),
('VGDA', 'Alex Pinedo', 2019, 'Technology'),
('Chess Club', 'Justin Cheung', 2017, 'Recreation'),
('Asexual and Straight', 'Linda Trinh', 2019, 'Biography');

/* Inserts 12 entries into the Publishers table */
INSERT INTO Publishers(publishername, publisheraddress, publisherphone, publisheremail) values
('WorldHello', '0000 Galaxy Avenue', '10000000000', 'earth@galaxy.org'),
('Make Believe', '9239 Rainbow Road', '1231232345', 'dreamstarters@imail.com'),
('Eeveeloutions', '3241 Pallet Town', '19092931235', 'profoak@catchemall.com'),
('TenTen Prints', '1010 Perfect Street', '11010101010', 'perfection10@plushie.org'),
('Blue Bunny', '3453 Fun Avenue', '14343943753', 'funplayfun@plushie.org'),
('Mini Duc', '5755 Grass Land', '14086963049', 'miniduc@plushie.org'),
('Sweet Prints', '1383 Candy Road', '19432034932', 'sweetprints@gmail.com'),
('Total Concentration', '9843 Tanji Road', '12832392054', 'nezuko@demonslayer.com'),
('WowThat', '1932 Blank Street', '13942034854', 'memethiswow@yahoo.com'),
('Ummmmm', '3284 Blanking Avenue', '13854349584', 'contactumm@ummmm.com'),
('Running Out', '1234 Help Street', '19099099090', 'gethelp@runningout.com'),
('Of Ideas', '2324 Void Street', '19999999999', 'noidea@plshelp.com');

/* Inserts 12 entries into the Books table */
INSERT INTO Books(groupname,publishername,booktitle,yearpublished,numberpages) values
('HelloWorld', 'WorldHello', 'Hello To the World', 1950, 500),
('VGDA', 'Make Believe', 'Intro to Game Development',  2019, 50),
('Jade Studios', 'Eeveeloutions', 'Our First Game', 2018, 60),
('Chess Club', 'TenTen Prints', 'We Had 3 Members', 2017, 1010),
('Asexual and Straight', 'Blue Bunny', 'I Really Am', 2019, 5),
('AJR', 'Mini Duc', 'Dear Winter',2010, 79),
('AsciiTable', 'Sweet Prints', 'I Die Everyday',2015, 99),
('Just One Cookbook', 'Total Concentration', 'Cook Right',2018, 46),
('Chicken Coup', 'WowThat', 'Bawk Bawk Beans',2019, 10),
('Tasty', 'Ummmmm', 'Video Binging',2014, 563),
('BESST', 'Running Out', 'Actually a Harem',2019, 8),
('Shakespeare Fan Club', 'Of Ideas', 'Outdated Poetry', 1829, 9999);
