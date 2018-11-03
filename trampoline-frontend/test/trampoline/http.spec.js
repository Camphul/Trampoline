import {expect} from 'chai';
import http from '../../src/trampoline/http';

describe('trampoline-http-client', () => {
   it('http should return axios client', ()=> {
       return expect(http).to.equal(require('axios'));
   });
});