DROP TABLE IF EXISTS Coin;
CREATE TABLE Coin (
  currency varchar(255) NOT NULL,
  dollar varchar(255) NOT NULL,
  ctname varchar(255) DEFAULT NULL,
  exrate DECIMAL(19, 4),
  lasttime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (currency)
);