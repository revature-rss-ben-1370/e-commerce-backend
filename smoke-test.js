import { sleep } from 'k6';
import http from 'k6/http';

export const options = {
    duration: '1m',
    vus: 5,
};

export default function () {
    http.get('http://a24b3e0b0313345e49eb2e69fe33a185-35174312.us-east-1.elb.amazonaws.com/e-commerce/api/product');
    sleep(0.1);
}