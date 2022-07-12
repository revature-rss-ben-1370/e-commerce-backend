import { sleep } from 'k6';
import http from 'k6/http';

export const options = {
    duration: '2m',
    vus: 20,
};

export default function () {


    http.get('http://a8164654749db438791fab617e96f1c9-157527310.us-east-1.elb.amazonaws.com/e-commerce/api/product');
    sleep(0.01);
    http.get('http://a8164654749db438791fab617e96f1c9-157527310.us-east-1.elb.amazonaws.com/e-commerce/api/product');
    sleep(0.01);
}