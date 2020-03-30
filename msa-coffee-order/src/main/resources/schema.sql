DROP TABLE IF EXISTS TB_ORDER;

CREATE TABLE TB_ORDER (
    `ID` NUMBER AUTO_INCREMENT NOT NULL,
    `ORDER_NO` NUMBER NOT NULL,
    `COFFEE_NAME` VARCHAR NOT NULL,
    `COFFEE_COUNT` VARCHAR NOT NULL,
    `MEMBER_NAME` VARCHAR NOT NULL,
    PRIMARY KEY (`ID`)
);

ALTER TABLE TB_ORDER ADD CONSTRAINT ORDER_UNIQUE UNIQUE (ORDER_NO);