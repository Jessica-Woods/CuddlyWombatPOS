INSERT INTO MENU (ID, ITEM_NAME, ITEM_COST)
VALUES
    (1, 'Latte', 5.00),
    (2, 'Tall Black', 2.50),
    (3, 'Chai', 3.00),
    (4, 'Hamburger', 15.00),
    (5, 'Fish & Chips', 27.00),
    (6, 'Steak', 33.33);

INSERT INTO RESERVATION (ID, NAME, PHONE_NUMBER, NUMBER_OF_PEOPLE, DATE, TIME)
VALUES (1, 'Anne', '0499799797', 3, NOW(), NOW());

INSERT INTO EMPLOYEE (ID, NAME)
VALUES
    (1, 'Anne'),
    (2, 'Bill'),
    (3, 'Cathy'),
    (4, 'Dean');