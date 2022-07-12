import { sleep } from 'k6';
import http from 'k6/http';

export const options = {
    duration: '5m',
    vus: 20,
};

export default function () {
    http.get('http://a24b3e0b0313345e49eb2e69fe33a185-35174312.us-east-1.elb.amazonaws.com/e-commerce/api/product');
    sleep(0.1);
    http.get('http://a24b3e0b0313345e49eb2e69fe33a185-35174312.us-east-1.elb.amazonaws.com/e-commerce/api/product');
    sleep(0.1);
    http.get('http://a24b3e0b0313345e49eb2e69fe33a185-35174312.us-east-1.elb.amazonaws.com/e-commerce/api/product');
    sleep(0.1);
    http.get('http://a24b3e0b0313345e49eb2e69fe33a185-35174312.us-east-1.elb.amazonaws.com/e-commerce/api/product');
    sleep(0.1);
    http.get('http://a24b3e0b0313345e49eb2e69fe33a185-35174312.us-east-1.elb.amazonaws.com/e-commerce/api/product');
    sleep(0.1);
}