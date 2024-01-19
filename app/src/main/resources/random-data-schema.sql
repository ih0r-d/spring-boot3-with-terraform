-- {
--   "id": 1105,
--   "uid": "d80db268-1ac6-4773-9545-3dd9e1c0a9b3",
--   "brand": "Whirlpool",
--   "equipment": "Drawer dishwasher"
-- }
create table if not exists appliances
(
    id        BIGSERIAL PRIMARY KEY,
    uid       VARCHAR(50)  NOT NULL,
    brand     VARCHAR(100) NOT NULL,
    equipment VARCHAR(250) NOT NULL
);