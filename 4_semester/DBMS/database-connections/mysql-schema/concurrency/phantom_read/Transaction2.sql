START TRANSACTION;
    DELETE FROM `music-festivals`.festivalsTicketsPricesConcurrency
    WHERE festivalId = 1 and ticketTypeId = 2;
COMMIT;

START TRANSACTION;
    INSERT INTO `music-festivals`.festivalsTicketsPricesConcurrency (festivalId, ticketTypeId, price)
    VALUES (2, 3, 400);
COMMIT;

START TRANSACTION;
    UPDATE `music-festivals`.festivalsTicketsPricesConcurrency
    SET price = price + 100 WHERE festivalId = 1 and ticketTypeId = 1;
COMMIT;





