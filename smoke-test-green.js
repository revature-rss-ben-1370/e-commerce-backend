import { sleep } from 'k6';
import http from 'k6/http';

export const options = {
    duration: '2m',
    vus: 20,
};

export default function () {


    http.get('http://a0b905afdcd8d45fab0d6125451e1e3c-315284420.us-east-1.elb.amazonaws.com/e-commerce/api/product');
    sleep(0.01);
    http.get('http://a0b905afdcd8d45fab0d6125451e1e3c-315284420.us-east-1.elb.amazonaws.com/e-commerce/api/product');
    sleep(0.01);
}