
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
START TRANSACTION;
    SELECT * FROM `music-festivals`.festivalsTicketsPricesConcurrency;
    SELECT SLEEP(5);
    SELECT * FROM `music-festivals`.festivalsTicketsPricesConcurrency;
    SELECT SLEEP(5);
    SELECT * FROM `music-festivals`.festivalsTicketsPricesConcurrency;
    SELECT SLEEP(5);
    SELECT * FROM `music-festivals`.festivalsTicketsPricesConcurrency;
ROLLBACK;