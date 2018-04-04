-- TODO update client regionID if branch changes

-- create the database
DROP DATABASE IF EXISTS arnoldrouan;
CREATE DATABASE arnoldrouan;

-- select the database
USE arnoldrouan;

CREATE TABLE region
(
	r_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL UNIQUE,
    r_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE service
(
	s_id INT AUTO_INCREMENT PRIMARY KEY,
    s_name VARCHAR(255) NOT NULL,
    s_type VARCHAR(255) NOT NULL,
    s_description VARCHAR(255) NOT NULL,
    s_price INT NOT NULL,
    s_available BOOL NOT NULL
);

CREATE TABLE package
(
	p_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL UNIQUE,
    p_name VARCHAR(255) NOT NULL,
    p_price INT NOT NULL
);

CREATE TABLE package_service
(
	p_id INT NOT NULL,
    s_id INT NOT NULL,
    PRIMARY KEY (p_id, s_id),
    CONSTRAINT ps_p_id_fk
		FOREIGN KEY (p_id)
		REFERENCES package (p_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
	CONSTRAINT ps_s_id_fk
		FOREIGN KEY (s_id)
		REFERENCES service (s_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE package_available
(
	r_id INT NOT NULL,
    p_id INT NOT NULL,
    available BOOL NOT NULL,
    PRIMARY KEY (r_id,p_id),
    CONSTRAINT pa_r_id_fk
		FOREIGN KEY (r_id)
		REFERENCES region (r_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
	CONSTRAINT pa_p_id_fk
		FOREIGN KEY (p_id)
		REFERENCES package (p_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE customer
(
	c_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL UNIQUE,
    c_name VARCHAR(255) NOT NULL,
    r_id INT NOT NULL,
    p_id INT NOT NULL,
    c_cc_name VARCHAR(255) NOT NULL,
    c_cc_number CHAR(16) NOT NULL,
    c_cc_expiration_month INT NOT NULL, 
    c_cc_expiration_year INT NOT NULL, 
    c_cc_cvv INT NOT NULL,
    c_billing_street VARCHAR(255) NOT NULL,
    c_billing_city VARCHAR(255) NOT NULL,
    c_billing_state VARCHAR(255) NOT NULL,
    c_billing_zip INT NOT NULL,
    c_username VARCHAR(255) NOT NULL,
    c_password VARCHAR(255) NOT NULL,
    c_customer_since DATE NOT NULL,
    c_package_start DATE NOT NULL,
    c_package_end DATE NOT NULL,
    c_phone VARCHAR(255) NOT NULL,
    c_email VARCHAR(255) NOT NULL,
	c_street_address VARCHAR(255) NOT NULL,
    c_city VARCHAR(255) NOT NULL,
    c_state CHAR(2) NOT NULL,
    c_zip INT NOT NULL,
    c_newsletter BOOL NOT NULL,
	CONSTRAINT c_r_id_fk
		FOREIGN KEY (r_id)
		REFERENCES region (r_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT c_p_id_fk
		FOREIGN KEY (p_id)
		REFERENCES package (p_id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT
);

CREATE TABLE employee
(
	e_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL UNIQUE,
    e_name VARCHAR(255) NOT NULL,
    r_id INT NOT NULL,
    e_salary INT NOT NULL,
    e_role VARCHAR(255) NOT NULL,
    e_username VARCHAR(255) NOT NULL,
    e_password VARCHAR(255) NOT NULL,
    e_start_date DATE,
    e_phone VARCHAR(255) NOT NULL,
    e_email VARCHAR(255) NOT NULL,
    e_street_address VARCHAR(255) NOT NULL,
    e_city VARCHAR(255) NOT NULL,
    e_state CHAR(2) NOT NULL,
    e_zip INT NOT NULL,
    CONSTRAINT e_r_id_fk
		FOREIGN KEY (r_id)
		REFERENCES region (r_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE appointment
(
	a_id INT AUTO_INCREMENT PRIMARY KEY UNIQUE NOT NULL,
    e_id INT NOT NULL,
    c_id INT NOT NULL,
    a_time DATE NOT NULL,
    a_price INT NOT NULL,
    a_notes VARCHAR(255) NOT NULL,
    CONSTRAINT a_e_id_fk
		FOREIGN KEY (e_id)
		REFERENCES employee (e_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
	CONSTRAINT a_c_id_fk
		FOREIGN KEY (c_id)
		REFERENCES customer (c_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- insert region data 
INSERT INTO `region` VALUES (2,'California'),(1,'Massachusetts'),(3,'New York');

-- insert service data
INSERT INTO `service` VALUES (1,'Basic cable','cable','Include local channels as well as 10 national networks',20,1),(2,'standard cable','cable','Enjoy over 80 different networks',50,1),(3,'premium cable','cable','Enjoy over 500 premium networks on top of all local and national channels',100,1),(4,'basic internet','internet','Includes basic service with 25 Mbps Upload/Download',40,1),(5,'standard internet','internet','See impressive speed with 50 Mbps upload and download',70,1),(6,'premium internet','internet','Blazing fast speeds with an upload and download rate of 200 mbps',100,1),(7,'basic phone','phone','Unlimited local calls with the option for a fixed rate out of state calls',20,1),(8,'standard phone','phone','Includes local and national calling with a fixed rate international option',40,1),(9,'premium phone','phone','Unlimited international calling',80,1);

-- insert package data
INSERT INTO `package` VALUES (1,'Basic Cable',20),(2,'Standard Cable',50),(3,'Premium Cable',100),(4,'Basic Internet',40),(5,'Standard Internet',70),(6,'Premium Internet',100),(7,'Basic Phone',20),(8,'Standard Phone',40),(9,'Premium Phone',80),(10,'Basic Bundle',60),(11,'Standard Bundle',170),(12,'Premium Bundle',240),(13,'\"The Browser\"\" Bundle\"',160),(14,'\"The Binger\"\" Bundle\"',150),(15,'\"The Caller\"\" Bundle\"',170),(16,'Entertainment Bundle',180),(17,'Standard Internet and Cable',100),(18,'Stnadard Phone and Internet',90),(19,'Standard Phone and Cable',70);

-- insert package service data
INSERT INTO `package_service` VALUES (1,1),(10,1),(2,2),(11,2),(13,2),(15,2),(17,2),(19,2),(3,3),(12,3),(14,3),(16,3),(4,4),(10,4),(5,5),(11,5),(14,5),(15,5),(17,5),(18,5),(6,6),(12,6),(13,6),(16,6),(7,7),(10,7),(16,7),(8,8),(11,8),(13,8),(14,8),(18,8),(19,8),(9,9),(12,9),(15,9);

-- insert package available data
INSERT INTO `package_available` VALUES (1,1,1),(1,2,1),(1,3,1),(1,4,1),(1,5,1),(1,6,1),(1,7,1),(1,8,1),(1,9,0),(1,10,1),(1,11,1),(1,12,1),(1,13,0),(1,14,1),(1,15,1),(1,16,0),(1,17,1),(1,18,1),(1,19,1),(2,1,1),(2,2,1),(2,3,1),(2,4,1),(2,5,1),(2,6,1),(2,7,1),(2,8,1),(2,9,1),(2,10,1),(2,11,1),(2,12,1),(2,13,1),(2,14,1),(2,15,1),(2,16,1),(2,17,1),(2,18,1),(2,19,1),(3,1,1),(3,2,1),(3,3,1),(3,4,1),(3,5,1),(3,6,1),(3,7,1),(3,8,1),(3,9,1),(3,10,1),(3,11,1),(3,12,1),(3,13,1),(3,14,1),(3,15,1),(3,16,1),(3,17,1),(3,18,1),(3,19,1);

-- insert customer data
INSERT INTO `customer` VALUES 
(1,'Gabe Laban',3,2,'Gabe Laban','9.610E+15',11,19,623,'2084 Novick Parkway','Albany','NY',12242,'glaban0','JNzTg2Pu','2018-03-18','2018-03-26','2019-03-26','5185583188','glaban0@virginia.edu','2084 Novick Parkway','Albany','NY',12242,1),(2,'Whitney Dmytryk',3,8,'Whitney Dmytryk','8.887E+15',4,26,685,'7 Burning Wood Terrace','Schenectady','NY',12305,'wdmytryk1','gtsGVS1','2018-04-08','2018-03-27','2019-03-27','5182788063','wdmytryk1@g.co','7 Burning Wood Terrace','Schenectady','NY',12305,1),(3,'Delila Fidler',3,17,'Delila Fidler','1.093E+15',7,25,968,'10874 Spohn Crossing','Rochester','NY',14652,'dfidler2','oy4kiaNY48','2018-04-10','2018-03-28','2019-03-28','5851958787','dfidler2@fc2.com','10874 Spohn Crossing','Rochester','NY',14652,1),(4,'Shawn Gerdes',2,8,'Shawn Gerdes','2.573E+15',3,23,396,'84471 Surrey Parkway','Sacramento','CA',94273,'sgerdes3','IVWEdCSiW','2018-03-23','2018-03-29','2019-03-29','9162638396','sgerdes3@webs.com','84471 Surrey Parkway','Sacramento','CA',94273,1),(5,'Prinz Trevithick',1,3,'Prinz Trevithick','4.246E+15',12,28,482,'1 Center Hill','Worcester','MA',1610,'ptrevithick4','HppsQbEdf','2018-04-12','2018-03-30','2019-03-30','5089337390','ptrevithick4@bloglines.com','1 Center Hill','Worcester','MA',1610,0),(6,'Shawn Leaf',2,5,'Shawn Leaf','7.486E+15',12,24,395,'0783 Sycamore Alley','Bakersfield','CA',93399,'sleaf5','93IMQqQmDcr','2018-04-09','2018-03-31','2019-03-31','6618392762','sleaf5@google.com.hk','0783 Sycamore Alley','Bakersfield','CA',93399,1),(7,'Rosie Gribbell',2,3,'Rosie Gribbell','9.024E+15',12,25,468,'0 Golf Course Court','Sacramento','CA',94237,'rgribbell6','wUwM4RCZQjkq','2018-03-23','2018-04-01','2019-04-01','9167329756','rgribbell6@statcounter.com','0 Golf Course Court','Sacramento','CA',94237,1),(8,'Jany Biggin',3,17,'Jany Biggin','1.409E+15',5,18,101,'5292 Fremont Road','New York City','NY',10150,'jbiggin7','klVeL8B','2018-03-27','2018-04-02','2019-04-02','2124898000','jbiggin7@ca.gov','5292 Fremont Road','New York City','NY',10150,1),(9,'Wendie Dumphry',3,16,'Wendie Dumphry','9.292E+15',4,28,666,'5 Reinke Lane','Albany','NY',12255,'wdumphry8','LKG3AY','2018-03-28','2018-04-03','2019-04-03','5184693030','wdumphry8@slideshare.net','5 Reinke Lane','Albany','NY',12255,0),(10,'Roslyn Chmiel',2,19,'Roslyn Chmiel','8.722E+15',8,22,719,'90590 Victoria Circle','Torrance','CA',90510,'rchmiel9','h8Wryd56i','2018-04-13','2018-04-04','2019-04-04','8187786455','rchmiel9@1688.com','90590 Victoria Circle','Torrance','CA',90510,0),(11,'Stevana Juanes',2,5,'Stevana Juanes','2.309E+15',3,27,719,'8115 Bunting Place','Carlsbad','CA',92013,'sjuanesa','XMqkMaKt','2018-03-31','2018-04-05','2019-04-05','7606602154','sjuanesa@wikimedia.org','8115 Bunting Place','Carlsbad','CA',92013,1),(12,'Nissy Stidworthy',2,17,'Nissy Stidworthy','9.781E+15',11,25,894,'658 Memorial Pass','Long Beach','CA',90810,'nstidworthyb','41wvxfm','2018-03-27','2018-04-06','2019-04-06','3103769683','nstidworthyb@dailymotion.com','658 Memorial Pass','Long Beach','CA',90810,0),(13,'Mayne Bance',2,3,'Mayne Bance','9.927E+15',7,25,380,'63 Gale Avenue','Fresno','CA',93750,'mbancec','uQSQLaB','2018-04-02','2018-04-07','2019-04-07','5599970024','mbancec@ow.ly','63 Gale Avenue','Fresno','CA',93750,0),(14,'Pooh Francomb',2,6,'Pooh Francomb','2.683E+15',5,27,619,'585 Farmco Drive','Palmdale','CA',93591,'pfrancombd','UcLEmdSGm','2018-04-13','2018-04-08','2019-04-08','6611198593','pfrancombd@is.gd','585 Farmco Drive','Palmdale','CA',93591,1),(15,'Moyra Riehm',1,18,'Moyra Riehm','1.693E+15',10,18,198,'8 Moland Junction','Worcester','MA',1610,'mriehme','M8ICDxcyT','2018-04-07','2018-04-09','2019-04-09','5088494284','mriehme@goodreads.com','8 Moland Junction','Worcester','MA',1610,1),(16,'Elsey Nelthropp',2,19,'Elsey Nelthropp','3.588E+15',9,26,172,'06 Red Cloud Pass','Santa Ana','CA',92705,'enelthroppf','61VPpqA10U','2018-03-31','2018-04-10','2019-04-10','2133354969','enelthroppf@jalbum.net','06 Red Cloud Pass','Santa Ana','CA',92705,0),(17,'Ned O\'Nowlan',2,1,'Ned O\'Nowlan','5.372E+15',11,21,269,'370 Stoughton Center','San Diego','CA',92110,'nonowlang','mE3L0f9G036K','2018-04-15','2018-04-11','2019-04-11','9496006738','nonowlang@sourceforge.net','370 Stoughton Center','San Diego','CA',92110,1),(18,'Marney Gudde',2,5,'Marney Gudde','4.433E+15',7,25,674,'6787 Buena Vista Way','Palmdale','CA',93591,'mguddeh','HRNmzTo13','2018-03-26','2018-04-12','2019-04-12','6611114962','mguddeh@spotify.com','6787 Buena Vista Way','Palmdale','CA',93591,0),(19,'Aland De Minico',2,13,'Aland De Minico','2.918E+15',8,23,119,'1 Miller Point','Bakersfield','CA',93311,'adei','lFpzXEOh66we','2018-04-02','2018-04-13','2019-04-13','8057047841','adei@adobe.com','1 Miller Point','Bakersfield','CA',93311,0),(20,'Heinrick Marlen',3,12,'Heinrick Marlen','9.749E+15',7,27,864,'00401 Granby Drive','Rochester','NY',14604,'hmarlenj','FDo6Qw297oVc','2018-03-24','2018-04-14','2019-04-14','5859548077','hmarlenj@deviantart.com','00401 Granby Drive','Rochester','NY',14604,1),(21,'Trista Harlow',2,3,'Trista Harlow','6.028E+15',12,27,117,'26322 Mayer Circle','Oakland','CA',94611,'tharlowk','lG7lF0qjL6rp','2018-03-30','2018-04-15','2019-04-15','5108213030','tharlowk@jugem.jp','26322 Mayer Circle','Oakland','CA',94611,0),(22,'Jeralee Wannes',3,1,'Jeralee Wannes','7.370E+15',3,28,101,'141 Pawling Place','New York City','NY',10105,'jwannesl','3KuNmj','2018-04-14','2018-04-16','2019-04-16','2126088943','jwannesl@yahoo.co.jp','141 Pawling Place','New York City','NY',10105,0),(23,'Renata Horley',2,17,'Renata Horley','8.380E+15',3,22,883,'6138 Cambridge Street','Sacramento','CA',95852,'rhorleym','yVFeI5','2018-04-15','2018-04-17','2019-04-17','9161961465','rhorleym@istockphoto.com','6138 Cambridge Street','Sacramento','CA',95852,0),(24,'Lisha McGlaughn',1,3,'Lisha McGlaughn','3.410E+15',6,19,754,'7 Manitowish Alley','Boston','MA',2109,'lmcglaughnn','GY5UMui','2018-04-12','2018-04-18','2019-04-18','6171355010','lmcglaughnn@who.int','7 Manitowish Alley','Boston','MA',2109,1),(25,'Zelma Emlin',2,2,'Zelma Emlin','9.378E+15',4,20,886,'22047 Karstens Road','Long Beach','CA',90810,'zemlino','FsdrFVT','2018-04-06','2018-04-19','2019-04-19','3109832575','zemlino@china.com.cn','22047 Karstens Road','Long Beach','CA',90810,1),(26,'Inesita Scanderet',3,6,'Inesita Scanderet','2.717E+15',11,23,622,'53 Commercial Pass','New York City','NY',10131,'iscanderetp','CUA8tt30UKJ','2018-03-29','2018-04-20','2019-04-20','2126264866','iscanderetp@weebly.com','53 Commercial Pass','New York City','NY',10131,0),(27,'Edeline Schenkel',2,8,'Edeline Schenkel','4.389E+15',7,24,873,'5 Delaware Crossing','Fresno','CA',93794,'eschenkelq','wvN4BZzFO','2018-04-06','2018-04-21','2019-04-21','5594125124','eschenkelq@acquirethisname.com','5 Delaware Crossing','Fresno','CA',93794,1),(28,'Alford Bencher',1,3,'Alford Bencher','2.535E+15',4,28,638,'92 Anniversary Terrace','Waltham','MA',2453,'abencherr','CN1HiqmsV','2018-04-14','2018-04-22','2019-04-22','9788283908','abencherr@devhub.com','92 Anniversary Terrace','Waltham','MA',2453,1),(29,'Sandi Hurles',2,8,'Sandi Hurles','9.159E+15',5,20,385,'92 Blaine Way','Fresno','CA',93750,'shurless','RX3k9BOUsd','2018-04-13','2018-04-23','2019-04-23','5595186814','shurless@ox.ac.uk','92 Blaine Way','Fresno','CA',93750,0),(30,'Jewell Rubinsky',3,1,'Jewell Rubinsky','4.724E+15',12,23,422,'8350 Blue Bill Park Lane','Bronx','NY',10459,'jrubinskyt','OxxvaFgCJU','2018-04-11','2018-04-24','2019-04-24','9174246370','jrubinskyt@cafepress.com','8350 Blue Bill Park Lane','Bronx','NY',10459,1),(31,'Ollie Caunter',2,16,'Ollie Caunter','9.523E+15',8,22,873,'1943 Texas Hill','San Francisco','CA',94137,'ocaunteru','H1FX6oO','2018-04-05','2018-04-25','2019-04-25','4151171269','ocaunteru@w3.org','1943 Texas Hill','San Francisco','CA',94137,1),(32,'Jessalin Jocelyn',3,7,'Jessalin Jocelyn','1.173E+15',3,22,245,'362 Eggendart Road','Rochester','NY',14609,'jjocelynv','ccNn2lDB','2018-03-27','2018-04-26','2019-04-26','5854478025','jjocelynv@ftc.gov','362 Eggendart Road','Rochester','NY',14609,1),(33,'Eddy Dorrington',2,5,'Eddy Dorrington','7.130E+15',1,21,683,'448 Hanson Hill','Oceanside','CA',92056,'edorringtonw','iASsvqG','2018-04-12','2018-04-27','2019-04-27','7607474923','edorringtonw@de.vu','448 Hanson Hill','Oceanside','CA',92056,1),(34,'Katherina Duester',2,4,'Katherina Duester','4.496E+15',10,19,808,'102 Schiller Junction','Long Beach','CA',90840,'kduesterx','VXJ9ANJU','2018-04-09','2018-04-28','2019-04-28','5629035042','kduesterx@ebay.co.uk','102 Schiller Junction','Long Beach','CA',90840,1),(35,'Luise Beaudry',2,5,'Luise Beaudry','7.179E+15',8,22,958,'140 Kingsford Pass','South Lake Tahoe','CA',96154,'lbeaudryy','tNNGobz','2018-04-03','2018-04-29','2019-04-29','5309894086','lbeaudryy@wisc.edu','140 Kingsford Pass','South Lake Tahoe','CA',96154,1),(36,'Somerset Camps',2,13,'Somerset Camps','1.736E+15',4,18,847,'8135 Shopko Alley','Los Angeles','CA',90076,'scampsz','Mw9CArmyP6','2018-04-08','2018-04-30','2019-04-30','3236696155','scampsz@engadget.com','8135 Shopko Alley','Los Angeles','CA',90076,1),(37,'Leigha Deerr',2,9,'Leigha Deerr','2.401E+15',3,27,994,'16485 Dixon Road','Oakland','CA',94605,'ldeerr10','8xe05EyOtx','2018-04-13','2018-05-01','2019-05-01','5108228420','ldeerr10@51.la','16485 Dixon Road','Oakland','CA',94605,1),(38,'Wendye Routledge',3,3,'Wendye Routledge','9.572E+15',3,21,786,'14 Clarendon Lane','New York City','NY',10004,'wroutledge11','6eIS5a','2018-03-28','2018-05-02','2019-05-02','6469044949','wroutledge11@ca.gov','14 Clarendon Lane','New York City','NY',10004,0),(39,'Dita De Metz',2,4,'Dita De Metz','8.277E+15',7,28,711,'4 Macpherson Place','Oakland','CA',94622,'dde12','J6zNax7lC','2018-03-30','2018-05-03','2019-05-03','5104821830','dde12@msn.com','4 Macpherson Place','Oakland','CA',94622,0),(40,'Dewey Blower',2,3,'Dewey Blower','2.572E+15',3,22,161,'63 Longview Court','San Diego','CA',92127,'dblower13','5cZMkv','2018-04-10','2018-05-04','2019-05-04','7609709898','dblower13@apache.org','63 Longview Court','San Diego','CA',92127,1),(41,'Karlan Campey',1,3,'Karlan Campey','8.837E+15',9,26,163,'29073 Kingsford Point','Springfield','MA',1129,'kcampey14','OGhUuRda','2018-04-15','2018-05-05','2019-05-05','4138372534','kcampey14@shinystat.com','29073 Kingsford Point','Springfield','MA',1129,1),(42,'Leandra Spiers',1,8,'Leandra Spiers','2.532E+15',5,18,793,'0 Johnson Park','New Bedford','MA',2745,'lspiers15','b75cN9cV','2018-04-05','2018-05-06','2019-05-06','5087244634','lspiers15@nbcnews.com','0 Johnson Park','New Bedford','MA',2745,1),(43,'Hermon Whitely',2,8,'Hermon Whitely','1.829E+15',7,25,732,'76 Jenna Park','San Francisco','CA',94116,'hwhitely16','MTdgCkoE37Gp','2018-03-25','2018-05-07','2019-05-07','4154443529','hwhitely16@google.cn','76 Jenna Park','San Francisco','CA',94116,0),(44,'Kira McGilvray',2,4,'Kira McGilvray','4.951E+15',1,28,404,'1034 Westerfield Center','Los Angeles','CA',90030,'kmcgilvray17','QIavr8VxpkM','2018-04-01','2018-05-08','2019-05-08','2135832339','kmcgilvray17@printfriendly.com','1034 Westerfield Center','Los Angeles','CA',90030,0),(45,'Alva McKane',2,8,'Alva McKane','5.420E+15',8,25,344,'63 Reindahl Road','Irvine','CA',92710,'amckane18','kb7vbiL2h57X','2018-04-06','2018-05-09','2019-05-09','7144522077','amckane18@prlog.org','63 Reindahl Road','Irvine','CA',92710,0),(46,'Reinwald Novak',3,4,'Reinwald Novak','8.622E+15',4,26,398,'5 Anhalt Avenue','New York City','NY',10270,'rnovak19','WvZfjRn','2018-04-06','2018-05-10','2019-05-10','7189557188','rnovak19@washingtonpost.com','5 Anhalt Avenue','New York City','NY',10270,1),(47,'Dale Treacher',2,8,'Dale Treacher','7.890E+15',5,27,418,'148 Meadow Ridge Place','Sacramento','CA',94245,'dtreacher1a','AqMaN9','2018-03-30','2018-05-11','2019-05-11','9161634566','dtreacher1a@mashable.com','148 Meadow Ridge Place','Sacramento','CA',94245,0),(48,'Quinn Abramovici',2,18,'Quinn Abramovici','2.571E+15',9,19,780,'83 Springview Terrace','San Bernardino','CA',92415,'qabramovici1b','oKVXzjl','2018-04-15','2018-05-12','2019-05-12','7608554153','qabramovici1b@cbc.ca','83 Springview Terrace','San Bernardino','CA',92415,0),(49,'Dunn Stickings',1,11,'Dunn Stickings','5.112E+15',12,18,359,'3486 Spaight Court','Boston','MA',2119,'dstickings1c','ZRgiSubSWv','2018-03-29','2018-05-13','2019-05-13','9782480189','dstickings1c@cyberchimps.com','3486 Spaight Court','Boston','MA',2119,1),(50,'Jennie Moyles',3,13,'Jennie Moyles','4.908E+15',1,18,250,'8 Monica Street','Hicksville','NY',11854,'jmoyles1d','iFqZLK2CFh','2018-04-14','2018-05-14','2019-05-14','5161156697','jmoyles1d@furl.net','8 Monica Street','Hicksville','NY',11854,1),(51,'Ainsley Hookes',3,15,'Ainsley Hookes','3.138E+15',3,21,350,'5478 Dawn Center','Albany','NY',12227,'ahookes1e','NW4GuhOUrAn','2018-03-30','2018-05-15','2019-05-15','5187033135','ahookes1e@addthis.com','5478 Dawn Center','Albany','NY',12227,1),(52,'Scotti McGrey',2,13,'Scotti McGrey','4.612E+15',2,21,533,'2309 Clove Center','Los Angeles','CA',90065,'smcgrey1f','Mu3voB7L7Z','2018-04-02','2018-05-16','2019-05-16','8189809560','smcgrey1f@skype.com','2309 Clove Center','Los Angeles','CA',90065,0),(53,'Hanson Meakes',2,14,'Hanson Meakes','3.170E+15',7,25,627,'5 Nelson Terrace','Stockton','CA',95298,'hmeakes1g','qFilRz','2018-04-13','2018-05-17','2019-05-17','2092400493','hmeakes1g@addthis.com','5 Nelson Terrace','Stockton','CA',95298,0),(54,'Demott Papierz',3,3,'Demott Papierz','1.362E+15',1,22,362,'0851 Meadow Vale Court','Yonkers','NY',10705,'dpapierz1h','iKnQrMC','2018-03-30','2018-05-18','2019-05-18','9146007764','dpapierz1h@amazon.com','0851 Meadow Vale Court','Yonkers','NY',10705,1),(55,'Eleanora Varey',2,13,'Eleanora Varey','5.930E+15',6,25,157,'7840 3rd Avenue','San Luis Obispo','CA',93407,'evarey1i','vIsWElstxom','2018-04-02','2018-05-19','2019-05-19','8052980363','evarey1i@ucsd.edu','7840 3rd Avenue','San Luis Obispo','CA',93407,0),(56,'Aubert Edgcombe',2,1,'Aubert Edgcombe','1.833E+15',8,21,577,'2 Graceland Lane','Sacramento','CA',95838,'aedgcombe1j','iXaIS2LXF','2018-03-24','2018-05-20','2019-05-20','9169399448','aedgcombe1j@apple.com','2 Graceland Lane','Sacramento','CA',95838,1),(57,'Glad Lavielle',2,5,'Glad Lavielle','9.976E+15',12,21,733,'36477 Crownhardt Terrace','San Diego','CA',92176,'glavielle1k','qnpwJNToM5F','2018-04-01','2018-05-21','2019-05-21','6196157386','glavielle1k@google.com.br','36477 Crownhardt Terrace','San Diego','CA',92176,0),(58,'Meredithe Braga',2,1,'Meredithe Braga','5.730E+15',9,20,845,'7321 Steensland Point','San Francisco','CA',94110,'mbraga1l','80EDTE4c','2018-03-27','2018-05-22','2019-05-22','4158304166','mbraga1l@taobao.com','7321 Steensland Point','San Francisco','CA',94110,0),(59,'Tami Rabley',2,16,'Tami Rabley','2.904E+15',12,28,379,'25 Fair Oaks Alley','Los Angeles','CA',90020,'trabley1m','h2QAaNxgSj','2018-03-26','2018-05-23','2019-05-23','5627387937','trabley1m@mashable.com','25 Fair Oaks Alley','Los Angeles','CA',90020,1),(60,'Kym Presman',3,18,'Kym Presman','4.077E+15',9,18,359,'9 Bunker Hill Center','Jamaica','NY',11499,'kpresman1n','HlbAvozDim','2018-04-03','2018-05-24','2019-05-24','2127014008','kpresman1n@google.com.hk','9 Bunker Hill Center','Jamaica','NY',11499,1),(61,'Stanfield Harragin',2,14,'Stanfield Harragin','2.037E+15',3,21,482,'9105 Waubesa Way','Northridge','CA',91328,'sharragin1o','2hqqcLYGor6N','2018-04-11','2018-05-25','2019-05-25','8181954520','sharragin1o@google.de','9105 Waubesa Way','Northridge','CA',91328,0),(62,'Ward Bertelmot',3,12,'Ward Bertelmot','6.222E+15',1,21,328,'4287 Cardinal Place','Albany','NY',12237,'wbertelmot1p','wT5Hqh4g','2018-03-28','2018-05-26','2019-05-26','5182037878','wbertelmot1p@liveinternet.ru','4287 Cardinal Place','Albany','NY',12237,0),(63,'Clary Goodliffe',2,16,'Clary Goodliffe','7.755E+15',3,26,471,'9 Carey Court','San Francisco','CA',94164,'cgoodliffe1q','KRUbAen','2018-04-07','2018-05-27','2019-05-27','4157653788','cgoodliffe1q@eepurl.com','9 Carey Court','San Francisco','CA',94164,0),(64,'Lenci Bumford',2,14,'Lenci Bumford','4.943E+15',6,21,851,'0767 Mendota Avenue','Inglewood','CA',90310,'lbumford1r','6pXCVWW6n67d','2018-04-15','2018-05-28','2019-05-28','3102471794','lbumford1r@ucoz.ru','0767 Mendota Avenue','Inglewood','CA',90310,1),(65,'Pascal Tarbet',2,9,'Pascal Tarbet','3.557E+15',10,18,524,'123 Susan Park','San Luis Obispo','CA',93407,'ptarbet1s','HYUXG9','2018-03-24','2018-05-29','2019-05-29','8056555742','ptarbet1s@businesswire.com','123 Susan Park','San Luis Obispo','CA',93407,1),(66,'Demetre Giacopello',3,17,'Demetre Giacopello','9.028E+15',5,23,288,'3 Hazelcrest Junction','Albany','NY',12242,'dgiacopello1t','qPjoQghprwY','2018-03-31','2018-05-30','2019-05-30','5185249252','dgiacopello1t@netvibes.com','3 Hazelcrest Junction','Albany','NY',12242,1),(67,'Read Permain',1,8,'Read Permain','8.344E+15',11,22,527,'816 Duke Point','Newton','MA',2162,'rpermain1u','LLZGpo78Vh2','2018-04-02','2018-05-31','2019-05-31','5085502462','rpermain1u@illinois.edu','816 Duke Point','Newton','MA',2162,1),(68,'Nadya Orknay',2,11,'Nadya Orknay','8.666E+15',5,24,985,'76 Anniversary Way','Sacramento','CA',94245,'norknay1v','hZ2slD9P3','2018-03-27','2018-06-01','2019-06-01','9169120875','norknay1v@hp.com','76 Anniversary Way','Sacramento','CA',94245,1),(69,'Abdel Sarch',2,6,'Abdel Sarch','9.325E+15',6,18,654,'48733 Thompson Pass','Sacramento','CA',94250,'asarch1w','oKVbhV9h','2018-04-15','2018-06-02','2019-06-02','9162206988','asarch1w@statcounter.com','48733 Thompson Pass','Sacramento','CA',94250,0),(70,'Clair Burdell',3,15,'Clair Burdell','8.996E+15',1,23,569,'1897 Northridge Junction','Bronx','NY',10469,'cburdell1x','zIQKS6ngZ6ik','2018-03-29','2018-06-03','2019-06-03','9175804604','cburdell1x@bluehost.com','1897 Northridge Junction','Bronx','NY',10469,0),(71,'Alena Radband',2,5,'Alena Radband','9.627E+15',7,26,120,'69 Mallory Park','San Diego','CA',92160,'aradband1y','5Wu0vGHBZUX','2018-04-14','2018-06-04','2019-06-04','6193849419','aradband1y@photobucket.com','69 Mallory Park','San Diego','CA',92160,1),(72,'Emeline Allbut',3,6,'Emeline Allbut','9.666E+15',11,27,646,'302 Grayhawk Hill','Jamaica','NY',11407,'eallbut1z','cyAjqGeQi','2018-04-02','2018-06-05','2019-06-05','5163523361','eallbut1z@washington.edu','302 Grayhawk Hill','Jamaica','NY',11407,0),(73,'Glyn Schusterl',2,9,'Glyn Schusterl','4.893E+15',7,21,931,'37928 Pleasure Junction','Inglewood','CA',90305,'gschusterl20','yVGRgz','2018-04-11','2018-06-06','2019-06-06','3232926920','gschusterl20@earthlink.net','37928 Pleasure Junction','Inglewood','CA',90305,0),(74,'Hadleigh Scamwell',2,2,'Hadleigh Scamwell','1.136E+15',10,22,356,'68 Charing Cross Way','Los Angeles','CA',90076,'hscamwell21','LQNXymc','2018-03-25','2018-06-07','2019-06-07','3235890261','hscamwell21@hp.com','68 Charing Cross Way','Los Angeles','CA',90076,0),(75,'Jori Triplow',2,9,'Jori Triplow','8.026E+15',10,23,899,'86664 South Avenue','Los Angeles','CA',90060,'jtriplow22','5COHQ1O','2018-04-05','2018-06-08','2019-06-08','3233098724','jtriplow22@google.com','86664 South Avenue','Los Angeles','CA',90060,0),(76,'Gabriello Pike',3,13,'Gabriello Pike','1.784E+15',10,28,826,'360 Browning Street','Brooklyn','NY',11220,'gpike23','42JpPdU4D7Z','2018-03-30','2018-06-09','2019-06-09','7188386801','gpike23@feedburner.com','360 Browning Street','Brooklyn','NY',11220,0),(77,'Rolf Collet',2,16,'Rolf Collet','6.467E+15',10,24,388,'1761 Talmadge Way','San Bernardino','CA',92405,'rcollet24','HPUhM8ijgF','2018-03-31','2018-06-10','2019-06-10','9093186557','rcollet24@free.fr','1761 Talmadge Way','San Bernardino','CA',92405,0),(78,'Frederic Kausche',3,9,'Frederic Kausche','8.317E+15',2,18,122,'801 Division Park','New York City','NY',10090,'fkausche25','L1RuKu6o','2018-03-24','2018-06-11','2019-06-11','2123074028','fkausche25@biglobe.ne.jp','801 Division Park','New York City','NY',10090,1),(79,'Thibaud Grzegorzewski',2,14,'Thibaud Grzegorzewski','6.599E+15',11,21,924,'566 Graceland Park','Pasadena','CA',91109,'tgrzegorzewski26','hdhkNIjxjU','2018-03-29','2018-06-12','2019-06-12','8182133321','tgrzegorzewski26@paypal.com','566 Graceland Park','Pasadena','CA',91109,0),(80,'Jessalyn Girodias',3,5,'Jessalyn Girodias','3.607E+15',11,26,647,'28887 Crest Line Way','Brooklyn','NY',11205,'jgirodias27','MqZFK2hTCFeA','2018-04-09','2018-06-13','2019-06-13','7185739798','jgirodias27@scientificamerican.com','28887 Crest Line Way','Brooklyn','NY',11205,0),(81,'Aaron Ridgeway',2,3,'Aaron Ridgeway','4.057E+15',11,24,517,'154 Katie Drive','San Francisco','CA',94142,'aridgeway28','S7voJEnYmWR','2018-03-27','2018-06-14','2019-06-14','4159176482','aridgeway28@timesonline.co.uk','154 Katie Drive','San Francisco','CA',94142,1),(82,'Bernadene Lampe',2,11,'Bernadene Lampe','2.049E+15',12,22,853,'5125 Lunder Park','Van Nuys','CA',91406,'blampe29','m5cFPnezXuUc','2018-04-11','2018-06-15','2019-06-15','3231806377','blampe29@goo.gl','5125 Lunder Park','Van Nuys','CA',91406,0),(83,'Wiatt Conyer',2,3,'Wiatt Conyer','5.940E+15',7,28,789,'18187 Walton Lane','Los Angeles','CA',90030,'wconyer2a','8aHYouk1pV4','2018-03-31','2018-06-16','2019-06-16','2134389874','wconyer2a@yale.edu','18187 Walton Lane','Los Angeles','CA',90030,0),(84,'Kimmy Byass',3,7,'Kimmy Byass','7.090E+15',1,24,959,'3 Cambridge Avenue','New York City','NY',10249,'kbyass2b','qo9YykBru5cr','2018-04-15','2018-06-17','2019-06-17','2121508260','kbyass2b@whitehouse.gov','3 Cambridge Avenue','New York City','NY',10249,0),(85,'Florentia Hudspeth',2,4,'Florentia Hudspeth','7.761E+15',7,22,211,'454 East Circle','Long Beach','CA',90831,'fhudspeth2c','hGIkICtgE','2018-04-10','2018-06-18','2019-06-18','5621977072','fhudspeth2c@ucla.edu','454 East Circle','Long Beach','CA',90831,1),(86,'Erwin Mussolini',2,7,'Erwin Mussolini','5.802E+15',5,19,457,'07 Randy Way','Long Beach','CA',90805,'emussolini2d','cakMFKT0sT','2018-04-04','2018-06-19','2019-06-19','5625269658','emussolini2d@nbcnews.com','07 Randy Way','Long Beach','CA',90805,1),(87,'Hillard Keeffe',3,9,'Hillard Keeffe','6.864E+15',5,20,854,'436 Hallows Road','Elmira','NY',14905,'hkeeffe2e','cujVvbyOTQQ','2018-04-07','2018-06-20','2019-06-20','6078463210','hkeeffe2e@ask.com','436 Hallows Road','Elmira','NY',14905,0),(88,'Tomi Garham',2,18,'Tomi Garham','3.288E+15',3,27,653,'0182 Granby Park','Corona','CA',92883,'tgarham2f','RDSYQBtqvXQt','2018-03-26','2018-06-21','2019-06-21','6265348910','tgarham2f@fotki.com','0182 Granby Park','Corona','CA',92883,1),(89,'Milty Emmanueli',3,17,'Milty Emmanueli','5.195E+15',6,22,643,'2358 Grayhawk Center','Staten Island','NY',10305,'memmanueli2g','Zk1k2dpJ','2018-03-30','2018-06-22','2019-06-22','7182496555','memmanueli2g@tinypic.com','2358 Grayhawk Center','Staten Island','NY',10305,1),(90,'Irita Toffalo',3,13,'Irita Toffalo','6.696E+15',8,27,802,'2 Sachtjen Road','New York City','NY',10039,'itoffalo2h','ueaoJ2AQ','2018-04-12','2018-06-23','2019-06-23','6462156541','itoffalo2h@furl.net','2 Sachtjen Road','New York City','NY',10039,1),(91,'Lana Quinnette',2,18,'Lana Quinnette','5.111E+15',3,27,435,'91990 Lakewood Gardens Place','San Diego','CA',92160,'lquinnette2i','PPhP5C','2018-04-01','2018-06-24','2019-06-24','6199018022','lquinnette2i@arizona.edu','91990 Lakewood Gardens Place','San Diego','CA',92160,1),(92,'Alis Millin',2,6,'Alis Millin','8.172E+15',9,26,218,'46 Brown Lane','Corona','CA',92878,'amillin2j','7gctBuXQC8sO','2018-03-23','2018-06-25','2019-06-25','9516239611','amillin2j@hubpages.com','46 Brown Lane','Corona','CA',92878,1),(93,'Noellyn Borris',2,5,'Noellyn Borris','2.158E+15',1,18,395,'99 Mosinee Junction','Orange','CA',92668,'nborris2k','iVczZGmyhigk','2018-03-30','2018-06-26','2019-06-26','7602089770','nborris2k@usa.gov','99 Mosinee Junction','Orange','CA',92668,0),(94,'Venus De Bruijn',3,15,'Venus De Bruijn','2.364E+15',2,25,272,'7500 Cardinal Drive','Jamaica','NY',11480,'vde2l','OnNJYqjHHhof','2018-04-12','2018-06-27','2019-06-27','7181086688','vde2l@hp.com','7500 Cardinal Drive','Jamaica','NY',11480,0),(95,'Kial Gosland',2,2,'Kial Gosland','7.456E+15',3,21,232,'0 Harbort Trail','Redwood City','CA',94064,'kgosland2m','crUIgHy','2018-04-02','2018-06-28','2019-06-28','6501756916','kgosland2m@1688.com','0 Harbort Trail','Redwood City','CA',94064,0),(96,'Lauri Capponer',3,18,'Lauri Capponer','1.283E+15',2,27,373,'97 North Junction','Brooklyn','NY',11236,'lcapponer2n','DFFf1OnmI2n','2018-04-03','2018-06-29','2019-06-29','7181949156','lcapponer2n@slate.com','97 North Junction','Brooklyn','NY',11236,1),(97,'Benoite Arger',3,12,'Benoite Arger','2.133E+15',5,20,887,'8492 Mallory Road','New York City','NY',10039,'barger2o','xipDk5lEQJ','2018-04-12','2018-06-30','2019-06-30','9176235213','barger2o@businessweek.com','8492 Mallory Road','New York City','NY',10039,1),(98,'Elke Coalburn',2,13,'Elke Coalburn','5.276E+15',6,21,334,'813 Garrison Street','Sacramento','CA',94230,'ecoalburn2p','yJiJIB','2018-03-30','2018-07-01','2019-07-01','9167245302','ecoalburn2p@biblegateway.com','813 Garrison Street','Sacramento','CA',94230,0),(99,'Aron Jarratt',3,13,'Aron Jarratt','1.615E+15',3,19,226,'41 Meadow Ridge Center','New York City','NY',10014,'ajarratt2q','3hI6pK','2018-03-26','2018-07-02','2019-07-02','3475226687','ajarratt2q@bandcamp.com','41 Meadow Ridge Center','New York City','NY',10014,1),(100,'Farah Brevetor',3,4,'Farah Brevetor','8.751E+15',1,20,286,'38644 Vidon Drive','Bronx','NY',10469,'fbrevetor2r','Mq1KHNOu','2018-03-28','2018-07-03','2019-07-03','7186851850','fbrevetor2r@washington.edu','38644 Vidon Drive','Bronx','NY',10469,1),(101,'John Smith',1,10,'John Smith','1234123412341234',11,19,623,'12 Huntington Ave','Boston','MA',12115,'smithj','password','2018-03-17','2018-03-25','2019-03-25','6173887823','smith.jo@husky.neu.edu','12 Huntington Ave','Boston','MA',12115,1);

-- insert empolyee data
INSERT INTO `employee` VALUES 
(1,'Mimi Jellis',1,55000,'Tech','mjellis1','a6HLXInyZN','2017-12-05','739 871 0611','mjellis1@cccc.com','1 Lunder Circle','Boston','MA',2115),
(2,'Janene Monteith',2,62000,'Tech','jmonteith5','9yF76Ymc','2017-12-05','117 978 2185','jmonteith5@cccc.com','792 Rusk Junction','San Francisco','CA',94110),
(3,'Iolande Rickasse',3,55000,'Tech','irickasse9','RuBFrTViy','2017-12-05','113 366 6905','irickasse9@cccc.com','6908 Clemons Street','New York','NY',10005),
(4,'Lela Klaaasen',2,70000,'Manager','lklaaasen3','FwH7XaPK','2017-10-01','477 236 2099','lklaaasen3@cccc.com','57292 Mallory Terrace','Boston','MA',2116),
(5,'Stearne Labbett',2,52000,'Sales','slabbett4','hgWRgMZXBQh','2018-01-01','230 556 2826','slabbett4@cccc.com','07010 Anhalt Crossing','San Francisco','CA',94110),
(6,'Alano Dri',1,45000,'Sales','adri0','42E4gG','2018-01-01','312 349 4534','adri0@cccc.com','8357 Jana Crossing','Boston','MA',2120),
(7,'Minta Toope',2,65000,'Accounting','mtoope6','MsL1wE','2017-10-10','221 506 2128','mtoope6@cccc.com','7 Swallow Road','San Francisco','CA',94103),
(8,'Ariadne Dessant',3,75000,'Manager','adessant7','a9mItLK','2017-10-01','662 369 8755','adessant7@cccc.com','37273 Canary Pass','San Francisco','CA',94102),
(9,'Brynn Lawly',3,45000,'Sales','blawly8','B2rIvpLi90R','2018-01-01','330 350 7281','blawly8@cccc.com','59 Dixon Drive','New York','NY',10001),
(10,'Mayor Balam',1,60000,'Accounting','mbalam2','uijTeSsiJWE','2017-10-10','505 367 5946','mbalam2@cccc.com','11843 Dennis Circle','Boston','MA',2120),
(11,'Callida Nicely',3,60000,'Accounting','cnicelya','pq5x1muJG','2017-10-10','253 194 3544','cnicelya@cccc.com','037 Village Plaza','New York','NY',10009),
(12,'Connor Arnold',1,70000,'Manager','carnold','mypass','2017-10-01','508 560 1670','arnold.co@husky.neu.edu','10 Leon Street','Boston','MA',02115),
(13,'Tim Jones',1,55000,'Tech','tjones1','abc123','2017-12-09','734 871 0321','tjones1@cccc.com','7 Cleveland Circle','Boston','MA',2115);

-- insert appointment data
INSERT INTO `appointment` VALUES 
(1,2,4,'2018-04-08',0,'client has dog'),(2,2,6,'2018-05-02',0,'none'),(3,2,7,'2018-05-03',79,'none'),(4,2,10,'2018-04-28',79,'none'),(5,2,11,'2018-04-26',0,'new install'),(6,2,12,'2018-04-15',79,'none'),(7,1,5,'2018-04-08',0,'none'),(8,1,15,'2018-05-02',0,'new install'),(9,1,24,'2018-05-03',59,'bring extra wire'),(10,13,41,'2018-04-28',59,'none'),(11,13,42,'2018-04-26',0,'none'),(12,13,49,'2018-04-15',59,'none'),(13,3,1,'2018-04-08',0,'new install'),(14,3,2,'2018-05-02',0,'none'),(15,3,3,'2018-05-03',69,'none'),(16,3,8,'2018-04-28',69,'internet issues'),(17,3,9,'2018-04-26',0,'none'),(18,3,30,'2018-04-15',69,'none');


-- LOGIN FUNCTIONS
-- Get employee data
 DROP FUNCTION IF EXISTS  employee_login_check;
 DELIMITER $$
 CREATE FUNCTION employee_login_check(username varchar(255), pass varchar(255))
	RETURNS INT
 BEGIN
 DECLARE id INT;
 SELECT e_id
 INTO id
 FROM employee
 WHERE username = e_username AND pass = e_password;
 RETURN (id);
 END $$ 
 DELIMITER ;
  
   DROP FUNCTION IF EXISTS  customer_login_check;
 DELIMITER $$
 CREATE FUNCTION customer_login_check(username varchar(255), pass varchar(255))
	RETURNS INT
 BEGIN
 DECLARE id INT;
 SELECT c_id
 INTO id
 FROM customer
 WHERE username = c_username AND pass = c_password;
 RETURN (id);
 END $$ 
 DELIMITER ;


-- EMPLOYEE VIEW FUNCTIONS
  DROP PROCEDURE IF EXISTS  get_e_data;
DELIMITER $$
CREATE PROCEDURE get_e_data(id INT)
BEGIN
 SELECT *
 FROM employee
 WHERE id=e_id;
END $$ 
 DELIMITER ; 
 
-- CHANGE PASSWORD FUNCTIONS
DROP PROCEDURE IF EXISTS  get_c_pwd;
DELIMITER $$
CREATE PROCEDURE get_c_pwd(id INT)
BEGIN
 SELECT c_password
 FROM customer
 WHERE id=c_id;
END $$ 
 DELIMITER ;
 
DROP PROCEDURE IF EXISTS  get_e_pwd;
DELIMITER $$
CREATE PROCEDURE get_e_pwd(id INT)
BEGIN
 SELECT e_password
 FROM employee
 WHERE id=e_id;
END $$ 
 DELIMITER ;
 
 
-- CUSTOMER VIEW FUNCTIONS
-- get edit customer fields
DROP PROCEDURE IF EXISTS  get_cust_info;
DELIMITER $$
CREATE PROCEDURE get_cust_info(id INT)
BEGIN
 SELECT *
 FROM customer
 JOIN region ON customer.r_id = region.r_id
 JOIN package ON customer.p_id = package.p_id
 JOIN package_service ON customer.p_id = package_service.p_id
 JOIN service ON package_service.s_id = service.s_id
 WHERE id=customer.c_id;
END $$ 
 DELIMITER ;
 
-- get edit customer appts, if any
DROP PROCEDURE IF EXISTS  get_cust_apt;
DELIMITER $$
CREATE PROCEDURE get_cust_apt(id INT)
BEGIN
 SELECT *
 FROM appointment
 JOIN employee ON appointment.e_id=employee.e_id
 WHERE id=c_id;
END $$ 
 DELIMITER ;

-- get edit customer fields
DROP PROCEDURE IF EXISTS  get_edit_cust;
DELIMITER $$
CREATE PROCEDURE get_edit_cust(id INT)
BEGIN
 SELECT c_name, c_username, c_phone, c_email, c_billing_street, c_billing_city, c_billing_state, c_billing_zip, c_newsletter
 FROM customer
 WHERE id=c_id;
END $$ 
 DELIMITER ;
 
 -- get edit customer billing fields
DROP PROCEDURE IF EXISTS  get_edit_custBilling;
DELIMITER $$
CREATE PROCEDURE get_edit_custBilling(id INT)
BEGIN
 SELECT c_cc_name, c_cc_number, c_cc_expiration_month, c_cc_expiration_year, c_cc_cvv, c_billing_street, c_billing_city, c_billing_state, c_billing_zip
 FROM customer
 WHERE id=c_id;
END $$ 
 DELIMITER ;
 
 DROP FUNCTION IF EXISTS  r_id_to_name;
 DELIMITER $$
 CREATE FUNCTION r_id_to_name(id int)
	RETURNS varchar(255)
 BEGIN
 DECLARE b_name varchar(255);
 SELECT r_name
 INTO b_name
 FROM region
 WHERE id=r_id;
 RETURN (b_name);
 END $$ 
 DELIMITER ;
 
  DROP PROCEDURE IF EXISTS update_employee_basic;
 DELIMITER $$
 CREATE PROCEDURE update_employee_basic
 (IN id int,
 IN new_username varchar(255), 
 IN new_password varchar(255), 
 IN new_email varchar(255),
 IN new_street_address varchar(255),
 IN new_city varchar(255),
 IN new_state char(2),
  IN new_zip int,
 IN new_phone varchar(255))
 BEGIN 
    UPDATE employee
    SET e_username = new_username,
	e_password = new_password,
    e_phone = new_phone,
    e_email = new_email,
    e_street_address = new_street_address,
    e_city = new_city,
    e_state = new_state,
    e_zip = new_zip
    WHERE e_id = id;
 END$$
 DELIMITER ;
 
 DROP PROCEDURE IF EXISTS update_employee_manager;
 DELIMITER $$
 CREATE PROCEDURE update_employee_manager
 (IN id int,
 IN new_branch int, 
 IN new_position varchar(255), 
 IN new_salary int,
 IN new_username varchar(255),
 IN new_password varchar(255))
 BEGIN 
    UPDATE employee
    SET r_id = new_branch,
    e_salary = new_salary,
    e_role = new_position,
    e_username = new_username,
    e_password = new_password
    WHERE e_id = id;
 END$$
 DELIMITER ;
 
DROP TRIGGER IF EXISTS employee_insert_date;
DELIMITER $$
CREATE TRIGGER employee_insert_date BEFORE INSERT ON employee
FOR EACH ROW
BEGIN
	SET NEW.e_start_date = NOW();
END $$ ;
DELIMITER ;

 DROP PROCEDURE IF EXISTS  regional_packages;
 DELIMITER $$
 CREATE PROCEDURE regional_packages(IN id int)
 BEGIN
 SELECT p_name
 FROM package
 NATURAL JOIN package_available 
 WHERE r_id = id and available;
 END $$ 
 DELIMITER ;
 
 DROP PROCEDURE IF EXISTS  regional_appointments;
 DELIMITER $$
 CREATE PROCEDURE regional_appointments(IN id int)
 BEGIN
 SELECT a_time, c_name, c_street_address, c_city, c_state, c_zip, e_name 
FROM appointment 
 NATURAL JOIN customer c 
 NATURAL JOIN employee e 
 WHERE e.r_id = id
ORDER BY a_time ASC;
 END $$ 
 DELIMITER ;
 
 DROP PROCEDURE IF EXISTS  regional_appointments_name;
 DELIMITER $$
 CREATE PROCEDURE regional_appointments_name(IN id int, IN employee_name varchar(255))
 BEGIN
 SELECT a_time, c_name, c_street_address, c_city, c_state, c_zip, e_name 
 FROM appointment 
 NATURAL JOIN customer c 
 NATURAL JOIN employee e 
 WHERE e.r_id = id and e_name = employee_name
 ORDER BY a_time ASC;
 END $$ 
 DELIMITER ;
 
 DROP PROCEDURE IF EXISTS  regional_customers;
 DELIMITER $$
 CREATE PROCEDURE regional_customers(IN id int)
 BEGIN
 SELECT c_name
 FROM customer 
 WHERE r_id = id
 ORDER BY c_name ASC;
 END $$ 
 DELIMITER ;

