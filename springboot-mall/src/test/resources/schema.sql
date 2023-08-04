CREATE TABLE IF NOT EXISTS product(
    product_id SERIAL NOT NULL,
    product_name  VARCHAR(128) NOT NULL,
    category VARCHAR(32) NOT NULL,
    image_url VARCHAR(256) NOT NULL,
    price INTEGER NOT NULL,
    stock INTEGER NOT NULL,
    description VARCHAR(1024),
    created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP NOT NULL,
    PRIMARY KEY(product_id)
)
