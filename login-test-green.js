import { sleep } from 'k6';
import http from 'k6/http';

export const options = {
    iterations: 5,
    vus: 5,
};

export default function () {
    let body = {email: 'testing@test.test', password: 'TestTest1!'}

    http.post('http://a0b905afdcd8d45fab0d6125451e1e3c-315284420.us-east-1.elb.amazonaws.com/e-commerce/auth/login',
    JSON.stringify(body));
    sleep(0.1)
    http.post('http://a0b905afdcd8d45fab0d6125451e1e3c-315284420.us-east-1.elb.amazonaws.com/e-commerce/auth/logout',
    JSON.stringify(body))
}