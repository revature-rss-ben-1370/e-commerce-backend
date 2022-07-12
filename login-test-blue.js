import { sleep } from 'k6';
import http from 'k6/http';

export const options = {
    iterations: 5,
    vus: 5,
};

export default function () {
    let body = {email: 'testing@test.test', password: 'TestTest1!'}

    http.post('http://a8164654749db438791fab617e96f1c9-157527310.us-east-1.elb.amazonaws.com/e-commerce/auth/login',
    JSON.stringify(body));
    sleep(0.1)
    http.post('http://a8164654749db438791fab617e96f1c9-157527310.us-east-1.elb.amazonaws.com/e-commerce/auth/logout',
    JSON.stringify(body))
}