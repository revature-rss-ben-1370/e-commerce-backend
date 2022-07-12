import { sleep } from 'k6';
import http from 'k6/http';

export const options = {
    iterations: 1,
    vus: 20,
};

export default function () {
    let body = {email: 'testing@test.test', password: 'TestTest1!'}

    http.post('http://a24b3e0b0313345e49eb2e69fe33a185-35174312.us-east-1.elb.amazonaws.com/e-commerce/auth/login',
    JSON.stringify(body));
    sleep(0.1)
    http.post('http://a24b3e0b0313345e49eb2e69fe33a185-35174312.us-east-1.elb.amazonaws.com/e-commerce/auth/logout',
    JSON.stringify(body))
}