START TRANSACTION;
    DELETE FROM `music-festivals`.festivalsTicketsPricesConcurrency
    WHERE festivalId = 1 and ticketTypeId = 2;

    SELECT SLEEP(5);

    INSERT INTO `music-festivals`.festivalsTicketsPricesConcurrency (festivalId, ticketTypeId, price)
    VALUES (2, 3, 400);

    SELECT SLEEP(5)  ;

    UPDATE `music-festivals`.festivalsTicketsPricesConcurrency
    SET price = price + 100 WHERE festivalId = 1 and ticketTypeId = 1;
COMMIT;

