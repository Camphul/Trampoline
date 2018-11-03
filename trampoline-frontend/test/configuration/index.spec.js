import {expect} from "chai";
import config from "../../src/configuration";

describe('trampoline-configuration-set-get', () => {
    it('Configuration should set and get correctly', ()=> {
        config.set('a', 2)
        return expect(config.get('a')).to.equal(2);
    });
});