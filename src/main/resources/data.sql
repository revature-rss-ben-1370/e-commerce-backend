
DELETE FROM product WHERE id <= 5;
INSERT INTO product (id, quantity, price,sale_rate, description, image, name,is_sale) VALUES (
    1,
    10,
    20.00,
    15,
    'A nice pair of headphones',
    'https://rss-p3-202203.s3.amazonaws.com/resources/images/headphone.png',
    'Headphones',
    false
) ,
(
    2,
    5,
    45.00,
    30,
    'A nice TeeShirt',
    'https://d3o2e4jr3mxnm3.cloudfront.net/Mens-Jake-Guitar-Vintage-Crusher-Tee_68382_1_lg.png',
    'TeeShirt',
    false
) ,
(
    3,
    20,
    2.50,
    10,
    'A reusable shopping bag',
    'https://images.ctfassets.net/5gvckmvm9289/3BlDoZxSSjqAvv1jBJP7TH/65f9a95484117730ace42abf64e89572/Noissue-x-Creatsy-Tote-Bag-Mockup-Bundle-_4_-2.png',
    'Shopping Bag',
    false
) ,
(
    4,
    20,
    10.00,
    20,
    'A fancy cap for a fancy person',
    'https://d3o2e4jr3mxnm3.cloudfront.net/Rocket-Vintage-Chill-Cap_66374_1_lg.png',
    'Baseball Cap',
    false
) ,
(
    5,
    3,
    80.00,
    10,
    'A nice coat',
    'https://www.pngarts.com/files/3/Women-Jacket-PNG-High-Quality-Image.png',
    'Coat',
    true
) ;
--Copy Pasting the salt doesn't work
DELETE FROM users WHERE id = 1;
INSERT INTO users (id, email, password, first_name, last_name, salt) VALUES (
    1,
    'testuser@gmail.com',
    '*eÓôéHÏÑ_£XÔ', --    'password',
    'Test',
    'User',
    'NotSoRandomSalt?'
) ;

DELETE FROM reset_request WHERE id = 1;
INSERT into reset_request (id,time_stamp,user_id) VALUES(
    1,
    12345678,
    1
) ;
